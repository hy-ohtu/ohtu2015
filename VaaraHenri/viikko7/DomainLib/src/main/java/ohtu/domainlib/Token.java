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
public class Token {
    String token;

    public Token() {
        
    }

    public Token(String token) {
        
        this.token = token;
    }

    @Override
    public String toString() {
        
        return token;
    }
    
    
    public static Token generate()
    {
        DateTimeToken ddt = DateTimeToken.generate(120);
        return new Token(ddt.toString());
    }
}