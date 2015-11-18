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
public class Error
{
    String error;
    
    @Override
    public String toString()
    {
        return "Error: "+error;
    }
    
    public static Error withCause(String cause)
    {
        Error e = new Error();
        e.error = cause;
        return e;
    }
}
