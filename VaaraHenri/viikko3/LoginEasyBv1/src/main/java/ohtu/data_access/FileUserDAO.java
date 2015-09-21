/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.domain.User;

/**
 *
 * @author hexvaara
 */
public class FileUserDAO implements UserDao
{

    HashMap<String, String> pws;
    
    public FileUserDAO() throws FileNotFoundException
    {
        Scanner sc = new Scanner(new File("user-password.txt"));
        
        pws = new HashMap<String, String>();
        
        
        while(sc.hasNext())
        {
            String username = sc.next();
            String password = sc.next();
        
            pws.put(username, password);
        }
        
        
        System.out.println(pws.get("asd"));
        System.out.println(pws.get("qwerty"));
        System.out.println(pws.get("jaakko"));
        System.out.println(pws.get("joni"));
    }
    
    
    
    
    @Override
    public List<User> listAll()
    {
        Set<String> keys = pws.keySet();
        
        Object[] usernames = keys.toArray();
        
        List<User> users = new ArrayList<User>();
        
        for (int i = 0; i < usernames.length; i++)
          {
            users.add(new User((String) usernames[i], pws.get((String) usernames[i])));
          }
        
        return users;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User findByName(String name)
    {
        if (pws.containsKey(name))
        {
            return new User(name, pws.get(name));
        }
        return null;
    }

    @Override
    public void add(User user)
    {
        try
          {
            FileWriter fw = new FileWriter("user-password.txt", true);
            
            pws.put(user.getUsername(), user.getPassword());
            
            fw.write("\n"+user.getUsername()+" "+user.getPassword());
            fw.close();
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          } catch (IOException ex)
          {
            Logger.getLogger(FileUserDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
}
