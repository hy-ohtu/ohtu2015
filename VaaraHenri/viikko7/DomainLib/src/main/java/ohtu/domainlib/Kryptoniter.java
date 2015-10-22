/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.domainlib;

import org.jasypt.util.text.StrongTextEncryptor;

/**
 * new
 * @author hexvaara
 */
public class Kryptoniter
{
    StrongTextEncryptor kryptoniter;
    
    
    public Kryptoniter(String encryption_key)
    {
        kryptoniter = new StrongTextEncryptor();
        kryptoniter.setPassword(encryption_key);
    }
    
    public Token encryptedToken(Token token)
    {
        return new Token(kryptoniter.encrypt(token.toString()));
    }
    public Token decryptedToken(Token token)
    {
        return new Token(kryptoniter.decrypt(token.toString()));
    }
}