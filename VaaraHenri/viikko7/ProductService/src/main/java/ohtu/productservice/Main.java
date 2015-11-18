/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.productservice;

/**
 * new
 * @author hexvaara
 */
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import ohtu.domainlib.DateTimeToken;
import ohtu.domainlib.JsonTransformer;
import ohtu.domainlib.Error;
import ohtu.domainlib.Product;
import ohtu.domainlib.UrlCollection;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.jasypt.util.text.StrongTextEncryptor;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.SparkBase.port;

public class Main
{
    public static void main(String args[]) throws IOException
    {
        port(4568);
        Gson gson = new Gson();
        
        String configurationsUrl = System.getenv("CONF_API");;
        
        HttpResponse hrConf = Request.Get(configurationsUrl)
                .execute().returnResponse();
        
        String responseAsJson = IOUtils
                .toString(hrConf.getEntity().getContent()
                        , Charset.forName("UTF-8"));
        
        
        UrlCollection urlCollection = gson.fromJson(responseAsJson, UrlCollection.class);
        
        String tokenUrl = urlCollection.token();
        String mongoLab = urlCollection.mongourl();
        
        
        MongoClientURI uri = new MongoClientURI(mongoLab);
        
        
        Morphia morphia = new Morphia();
        
        MongoClient mongo = new MongoClient(uri);
        
        morphia.mapPackage("ohtu.domainlib");
        
        Datastore datastore = morphia.createDatastore(mongo, "kanta11");
        
        get("/ping", (request, response) -> {
            String name = ManagementFactory.getRuntimeMXBean().getName();
            String dir = System.getProperty("user.dir");

            return "{ \"name\": \""+name+"\", \"dir\": \""+dir+"\" }";
        });
        
        before("/products", (request, response) ->
        {
            StrongTextEncryptor kryptoniter = new StrongTextEncryptor();
            kryptoniter.setPassword(System.getenv("OHTU_KRYPTO"));
            String encrypted_token = request.headers("Authorization");
            String decrypted_token = kryptoniter.decrypt(encrypted_token);
                
            System.out.println("decrypted token: "+decrypted_token);
                
            //trycatch tähän.
            DateTimeToken dtt_auth = gson.fromJson(decrypted_token, DateTimeToken.class);
                
            DateTimeToken dtt_now = DateTimeToken.generate(0);
                
                
            System.out.println("auth: "+dtt_auth.toString());
            System.out.println("now: "+dtt_now.toString());
            System.out.println("auth is after now: "+dtt_auth.isAfter(dtt_now));
                
            if (!dtt_auth.isAfter(dtt_now))
            {
                halt(401, gson.toJson(Error.withCause("missing, invalid or expired token")));
            }
        
//            preFilter(request);
//            HttpResponse hr = Request.Get(tokenUrl)
//                    .addHeader("Authorization", request.headers("Authorization"))
//                    .execute().returnResponse();
//            
//            
//            if (EntityUtils.toString(hr.getEntity()).equals("0"))
//            {
//                halt(401, gson.toJson(Error.withCause("invalid token")));
//            }
            
            //System.out.println("hr.getentity adasd"+EntityUtils.toString(hr.getEntity()));
        });
        
        
        get("/products", (request, response) -> 
        {
            preFilter(request);
            
            return datastore.find(Product.class).asList();
        }, new JsonTransformer());
        
        post("/products", (request, response) ->
        {
            preFilter(request);
            
            Product product = gson.fromJson(request.body(), Product.class);
            
            
            
            if (product == null)
            {
                halt(401, gson.toJson(Error.withCause("invalid credenials")));
            }
            
            if (datastore.createQuery(Product.class)
                    .field("name")
                    .equal(product.name())
                    .get() != null)
            {
                halt(400, gson.toJson(Error.withCause("name must be unique")));
            }
            
            datastore.save(product);
            
            
            return product;
        }, new JsonTransformer());
        
        
    }
    
    public static void preFilter(spark.Request request)
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
