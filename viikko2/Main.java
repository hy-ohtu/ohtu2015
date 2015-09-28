/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakkaus.ohtuv;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Markku
 */
public class Main {
    private static Object FileUtils;

    public static void main(String[] args) {
        Path currentDirectory;
        Path ohtuvPath;
        Path root;
        Path parent;
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat pvm = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat aika = new SimpleDateFormat("HH.mm");
        String aikaleima=pvm.format(date)+"."+aika.format(date);
        

        // INIT
        currentDirectory = Paths.get(System.getProperty("user.dir"));
//        System.out.println(currentDirectory.toString());
//        if(!Files.exists(Paths.get(currentDirectory.toString()+"/.ohtuv"))) {
//            File dir=new File(".ohtuv");
//            dir.mkdir();
//        }
        String filename="tiedosto.txt";

        Path path = Paths.get(currentDirectory.toString());
        root = path.getRoot();
        System.out.println("Root: " + root.toString());
        while (!path.toString().equals(root.toString()) && !Files.exists(Paths.get(path.toString() + "/.ohtuv")) ) {
            System.out.println("silmukassa");
            path = path.getParent();
            System.out.println(path.toString());

        }
        if (Files.exists(Paths.get(path.toString() + "/.ohtuv"))) {
            System.out.println("Löytyy polusta: " + path.toString());
            System.out.println("Kopioidaan kohteeseen: " + path.toString()+"/.ohtuv/" + aikaleima + "." + filename);
            try {
            Files.copy(Paths.get(filename), Paths.get(path.toString()+"/.ohtuv/"+ aikaleima + "." + filename));
            
            }
            catch(Exception e) {
                System.out.println(e.toString());
            }
            
        } else {
            System.out.println("Ei löydy!");
        }
    }
}


