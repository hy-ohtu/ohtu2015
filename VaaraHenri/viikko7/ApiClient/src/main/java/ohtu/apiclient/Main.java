/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.apiclient;

/**
 * new
 * @author hexvaara
 */
import ohtu.domainlib.Token;
import ohtu.domainlib.Product;
import ohtu.domainlib.Person;
import com.google.gson.Gson;
import java.nio.charset.Charset;
import java.util.Scanner;
import ohtu.domainlib.UrlCollection;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;

public class Main {

    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        
        String configurationsUrl = System.getenv("CONF_API");
        
        HttpResponse hrConf = Request.Get(configurationsUrl)
                .execute().returnResponse();
        
        String responseAsJson = IOUtils
                .toString(hrConf.getEntity().getContent()
                        , Charset.forName("UTF-8"));
        
        
        UrlCollection urlCollection = gson.fromJson(responseAsJson, UrlCollection.class);
        
        String personsUrl = urlCollection.persons();
        String sessionUrl = urlCollection.session();
        String productsUrl = urlCollection.products();
        
        Scanner scanner = new Scanner(System.in);
        Token token = new Token("invalid");
        
        while (true) {            
            System.out.print("> ");
            String komentorivi = scanner.nextLine();
            String[] komento = komentorivi.split(" ");
            
            ////////////////////////////////////////////////////////////////////
            
            if (komento[0].equals("products"))
            {
                // a get request with a header set
                HttpResponse httpResponse = Request.Get(productsUrl)
                        .addHeader("Authorization", token.toString())
                        .execute().returnResponse();
                
                int code = httpResponse.getStatusLine().getStatusCode();
                
                
                // read the response body as a json string
                String responseBodyAsJson = IOUtils
                        .toString(httpResponse.getEntity()
                        .getContent(), 
                        Charset.forName("UTF-8"));            
                System.out.println("response body was "+responseBodyAsJson);
                
                // turn the response body to an object of the right type
                if (code == 200) {
                    Product[] products = gson.fromJson(responseBodyAsJson, Product[].class);
                    for (Product product : products) {
                        System.out.println(product.name());
                    }
                } else {
                    Error error = gson.fromJson(responseBodyAsJson, Error.class);
                    System.out.println(error);
                }
            }
            
            if (komento[0].equals("new"))
            {
                Product product = new Product(komento[1], komento[2], komento[3], komento[4]);
                
                String asJson = gson.toJson(product);
                HttpEntity httpEntity = new ByteArrayEntity(asJson.getBytes("UTF-8"));
                
                HttpResponse httpResponse = Request
                        .Post(productsUrl)
                        .addHeader("Authorization", token.toString())
                        .body(httpEntity)
                        .execute()
                        .returnResponse();
                
                int code = httpResponse.getStatusLine().getStatusCode();
                
                System.out.println("code: "+code);
            }
            
            
            ////////////////////////////////////////////////////////////////
            
            if (komento[0].equals("login") ) {
                // form an object to help the json generation
                Person person = new Person(komento[1], null, komento[2], null);
                String asJson = gson.toJson(person);
                System.out.println("making a http post with body: "+asJson);

                HttpResponse httpResponse = Request.Post(sessionUrl).bodyString(asJson, ContentType.APPLICATION_JSON).execute().returnResponse();
                
                int code = httpResponse.getStatusLine().getStatusCode();

                String responseBodyAsJson = IOUtils.toString(httpResponse.getEntity().getContent(), Charset.forName("UTF-8"));            
                System.out.println("response body was "+responseBodyAsJson);

                if (code == 200) {
                    // login succeded, create and save the Token-object        
                    token = gson.fromJson(responseBodyAsJson, Token.class);
                    System.out.println("success! got token " + token.toString());
                    //System.out.println("success! got token: "+responseBodyAsJson);
                } else {
                    Error error = gson.fromJson(responseBodyAsJson, Error.class);
                    System.out.println(error);
                }
            } if (komento[0].equals("register")){
                Person person = new Person(komento[1], komento[2], komento[3], komento[4]);
                
                String asJson = gson.toJson(person);
                HttpEntity httpEntity = new ByteArrayEntity(asJson.getBytes("UTF-8"));
                
                HttpResponse httpResponse = Request.Post(personsUrl).body(httpEntity).execute().returnResponse();
                
                int code = httpResponse.getStatusLine().getStatusCode();
                
                System.out.println("code: "+code);
                
                // implement registration here
            } if (komento[0].equals("persons")){
                
                // a get request with a header set
                HttpResponse httpResponse = Request.Get(personsUrl)
                        .addHeader("Authorization", token.toString())
                        .execute().returnResponse();
                
                int code = httpResponse.getStatusLine().getStatusCode();
                
                
                // read the response body as a json string
                String responseBodyAsJson = IOUtils.toString(httpResponse.getEntity().getContent(), Charset.forName("UTF-8"));            
                System.out.println("response body was "+responseBodyAsJson);
                
                // turn the response body to an object of the right type
                if (code == 200) {
                    Person[] persons = gson.fromJson(responseBodyAsJson, Person[].class);
                    for (Person person : persons) {
                        System.out.println(person);
                    }
                } else {
                    Error error = gson.fromJson(responseBodyAsJson, Error.class);
                    System.out.println(error);
                }                
            }
        }
    }
}
