/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.domainlib;

import com.google.gson.Gson;

/**
 * new
 * @author hexvaara
 */
public class TokenConverter
{
    Gson gson = new Gson();
    
    public TokenConverter()
    {
        
    }
    
    public DateTimeToken toDateTime(Token token)
    {
        try {
            return gson.fromJson(token.toString(), DateTimeToken.class);
        } catch (Exception e)
        {
            return null;
        }
    }
}
