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
public class Product
{
    private String name, producer, price, inStock;
    
    public Product()
    {
        
    }
    
    public Product(String name, 
            String producer, 
            String price, 
            String inStock)
    {
        this.name = name;
        this.producer = producer;
        this.price = price;
        this.inStock = inStock;
    }
    
    public boolean valid()
    {
        return name!=null &&
                producer!=null &&
                price!=null &&
                inStock!=null;
    }
    
    public String name() {return name;}
    public String producer() {return producer;}
    public String price() {return price;}
    public String inStock() {return inStock;}
}
