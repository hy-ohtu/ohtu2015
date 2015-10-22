/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.domainlib;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;

/**
 * new, importeissa saattaa olla häikkää
 * @author hexvaara
 */
public class UrlLoader
{
    public UrlLoader()
    {
        
    }
    
    public UrlCollection load(String configurationsUrl) throws IOException
    {
        Gson gson = new Gson();
        HttpResponse response = Request.Get(configurationsUrl).execute().returnResponse();
        String responseAsJson = IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8"));
        return gson.fromJson(responseAsJson, UrlCollection.class);
    }
}
