/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.personservice;

import ohtu.domainlib.Person;
import ohtu.domainlib.Error;
import ohtu.domainlib.DateTimeToken;
import ohtu.domainlib.Kryptoniter;
import ohtu.domainlib.Token;
import ohtu.domainlib.TokenConverter;
import ohtu.domainlib.JsonTransformer;
import ohtu.domainlib.UrlCollection;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import org.apache.http.HttpResponse;
import org.apache.commons.io.IOUtils;
import spark.Request;
import static spark.Spark.*;

//new

public class App {
    
    
    
    public static void main(String[] args) throws IOException {
        
        Kryptoniter kryptoniter = new Kryptoniter(System.getenv("OHTU_KRYPTO"));
        TokenConverter tokenconverter = new TokenConverter();
        Gson gson = new Gson();
        //UrlLoader urlLoader = new UrlLoader();
        //UrlCollection urlCollection = urlLoader.load("CONF_API");
        
        //UrlCollection urlCollection = new UrlLoader(System.getenv()).load("CONF_API");
        
        String configurationsUrl = System.getenv("CONF_API");
        
        HttpResponse hrConf = org.apache.http.client.fluent.Request.Get(configurationsUrl)
                .execute().returnResponse();
        
        String responseAsJson = IOUtils
                .toString(hrConf.getEntity().getContent()
                        , Charset.forName("UTF-8"));
        
        UrlCollection urlCollection = gson.fromJson(responseAsJson, UrlCollection.class);
        
        
        // vaihda seuraavaan joku vapaa tietokanta
        String mongoLab = urlCollection.mongourl();
        MongoClientURI uri = new MongoClientURI(mongoLab);
        Morphia morphia = new Morphia();
        MongoClient mongo = new MongoClient(uri);
        // jos käytät lokaalia mongoa, luo client seuraavasti
        //MongoClient mongo = new MongoClient();

        morphia.mapPackage("ohtu.domainlib");
        // vaihda seuraavaan sama kun kannan nimi kuin mongourlissa
        Datastore datastore = morphia.createDatastore(mongo, "kanta11");

        

        get("/ping", (request, response) -> {
            preFilter(request);
            String name = ManagementFactory.getRuntimeMXBean().getName();
            String dir = System.getProperty("user.dir");

            return "{ \"name\": \""+name+"\", \"dir\": \""+dir+"\" }";
        });

        
        
        before("/persons", (request, response) -> {
            preFilter(request);
            
            if (request.requestMethod().equals("GET"))
            {
                DateTimeToken dtt_now = DateTimeToken.generate(0);
                DateTimeToken dtt_auth = tokenconverter
                    .toDateTime
                    (
                        kryptoniter.decryptedToken
                            (
                                new Token
                                (
                                        request.headers("Authorization")
                                )
                            )
                    );
                
                System.out.println("now :"+dtt_now.toString());
                System.out.println("auth:"+dtt_auth.toString());
                System.out.println("auth is after now :"+dtt_auth.isAfter(dtt_now));
                
                
                if (dtt_auth == null || !dtt_auth.isAfter(dtt_now))
                {
                    halt(401, gson.toJson(Error.withCause("missing, invalid or expired token")));
                }
            }
        });
        
        get("/persons", (request, response) -> {
            preFilter(request);
            return datastore.find(ohtu.domainlib.Person.class).asList();
        }, new JsonTransformer());

        post("/persons", (request, response) -> {
            preFilter(request);
            ohtu.domainlib.Person person = gson.fromJson(request.body(), ohtu.domainlib.Person.class);

            if ( person == null || !person.valid()) {
                halt(400, gson.toJson(Error.withCause("all fields must have a value")));
            }

            if ( datastore.createQuery(ohtu.domainlib.Person.class).field("username").equal(person.username()).get() != null ){
                halt(400, gson.toJson(Error.withCause("username must be unique")));
            }

            datastore.save(person);
            return person;
        }, new JsonTransformer());

        post("/session", (request, response) -> {
            preFilter(request);
            ohtu.domainlib.Person dataInRequest = gson.fromJson(request.body(), ohtu.domainlib.Person.class);

            ohtu.domainlib.Person person = datastore.createQuery(ohtu.domainlib.Person.class).field("username").equal(dataInRequest.username()).get();

            if ( person==null || !person.password().equals(dataInRequest.password()) ) {
                halt(401, gson.toJson(Error.withCause( "invalid credentials")));
            }

            return kryptoniter.encryptedToken(Token.generate());
        }, new JsonTransformer());

        after((request, response) -> {
            response.type("application/json");
        });
    }
    
    public static void preFilter(Request request)
    {
        System.out.println("----------------------------------------------");
        System.out.println(request.requestMethod());
        for (String line : request.headers())
        {
            System.out.println(line+" "+request.headers(line));
        }
        System.out.println(request.body());
    }
}
