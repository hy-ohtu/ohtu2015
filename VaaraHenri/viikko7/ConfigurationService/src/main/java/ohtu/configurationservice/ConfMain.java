/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.configurationservice;

import com.google.gson.Gson;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import ohtu.domainlib.DateTimeToken;
import ohtu.domainlib.UrlCollection;
import spark.Request;
import static spark.Spark.get;
import static spark.SparkBase.port;

/**
 * new
 * @author hexvaara
 */
public class ConfMain
{
    public static void main(String[] args)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 60);
        //System.out.println(calendar.getTime());
        
        
        DateTimeToken datetimetoken = DateTimeToken.generate(60);
        
        System.out.println(datetimetoken.toString());
        
        
        port(4569);
        
        Gson gson = new Gson();
        
        UrlCollection uc;
        
        String session = "http://localhost:4567/session";
        String token = "http://localhost:4567/token";
        String persons = "http://Localhost:4567/persons";
        String products = "http://Localhost:4568/products";
        String mongourl = "mongodb://ohtu:ohtu@ds041651.mongolab.com:41651/kanta11";
        
        uc = new UrlCollection(mongourl, token, persons, products, session);
        
        System.out.println(uc.mongourl()+" : "+uc.token()+" : "+uc.persons()+" : "+uc.products()+" : "+uc.session());
        
        String json = gson.toJson(uc);
        
        System.out.println("json: "+json);
        
        get("/configurations", (request, response) ->
        {
            preFilter(request);
            return json;
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