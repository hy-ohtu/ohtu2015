/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.domainlib;

/**
 * new
 * @author hexvaara
 */
public class UrlCollection
{
    String mongourl, token, persons, products, session;
    
    public UrlCollection()
    {
              
    }
    public UrlCollection(
            String mongourl,
            String token,
            String persons,
            String products,
            String session)
    {
        this.mongourl = mongourl;
        this.token = token;
        this.persons = persons;
        this.products = products;
        this.session = session;
    }
    
    public String session()
    {
        return session;
    }
    public String token()
    {
        return token;
    }
    public String persons()
    {
        return persons;
    }
    public String products()
    {
        return products;
    }
    public String mongourl()
    {
        return mongourl;
    }
}
