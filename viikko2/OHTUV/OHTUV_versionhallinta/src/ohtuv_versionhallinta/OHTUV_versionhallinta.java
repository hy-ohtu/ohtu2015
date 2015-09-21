/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtuv_versionhallinta;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asjuvone
 */
public class OHTUV_versionhallinta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        if (args.length < 1) {
            help();
        }
        else if (args[0].equals("init")) {
            init();
        } else if (args[0].equals("save")) {
            save(args);
        } else if (args[0].equals("restore")) {
            restore(args);
        } else {
            help();
        }
    }

    private static void help() {
        System.out.println("Komennot:");
        System.out.println("  init- luo .ohtuv -kansion");
        System.out.println("  save filu.txt ottaa kopion tiedostosta filu.txt");
        System.out.println("  restore 6.9.2015 filu.txt palauttaa tiedoston filu.txt annetun pvm mukaiseksi");
    }

    private static void save(String[] args) {
        if (args.length < 2) {
            System.out.println("Määrittele myös tiedoston nimi");
            return;
        }
        if (args.length > 2) {
            System.out.println("Tiedostoja joiden nimessä on välilyönti ei tueta!");
            return;
        }
        String fileName = args[1];
        File original = new File(fileName);
        if (!original.exists()) {
            System.out.println("Tiedostoa ei löytynyt!");
            return;
        }
        String repoPath = repoPath(System.getProperty("user.dir"));
        if (repoPath == null) {
            System.out.println("OHTUV ei inisialisiotu vielä! kokeile ohtuv init");
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");
        String copyName = repoPath + dateFormat.format(new Date()) + fileName;
        File destination = new File(copyName);
        if (destination.exists()) {
            System.out.println("Tallennus epäonnistui, sillä kohdetiedosto oli olemassa");
            return;
        }
        try {
            Files.copy(original.toPath(), destination.toPath());
            System.out.println("Tallennettu!");
        } catch (IOException ex) {
            System.out.println("Tallennus epäonnistui jostain syystä");
        }
    }

    private static void init() {
        File f = new File(System.getProperty("user.dir") + "/.ohtuv");
        System.out.println("Yritetään luoda " + f.toString());
        if (f.exists() && f.isDirectory()) {
            System.out.println("Kansio .ohtuv on jo olemassa!");
            return;
        }
        try {
            if (!f.mkdirs()) throw new SecurityException("Kansion luonti fail");
            System.out.println("Initialisoitiin kansio .ohtuv onnistuneesti");
        } catch(SecurityException se) {
            System.out.println("Kansion .ohtuv luonti ei onnistunut! + " + se.toString());
        }
    }

    private static void restore(String[] args) {
        //jos useita kopioita, listaa kopiot ja pyydä täsmennys
        File folder = new File(System.getProperty("user.dir") + "/.ohtuv");
        File[] list = folder.listFiles();
        ArrayList<File> matches = new ArrayList<>();
        if (args.length < 3) {
            System.out.println("TÄsmennä hakua!");
            return;
        }
        String hakuPvm = args[1];
        String hakuFileName = args[2];
        for (File file : list) {
            if (!file.isFile()) continue;
            if (!file.getName().endsWith(hakuFileName)) continue;
            if (file.getName().startsWith(hakuPvm)) matches.add(file);
        }
        if (matches.isEmpty()) {
            System.out.println("Ei löydetty!");
            return;
        }
        if (matches.size() > 1) {
            System.out.println("Löydettiin useita tiedostoja. Täsmennä minkä tiedoston haluat komennolla restore <aikaleima> <tiedostonimi>");
            for (File file : matches) {
                System.out.println("  " + file.getName());
            }
        }
        else {
            // Files.copy(original.toPath(), destination.toPath());
            System.out.println("Palautettiin tiedosto " + matches.get(0).getName());
        }
    }

    private static String repoPath(String p) {
        if (new File(p + "/.ohtuv").exists()) {
            return p + "/.ohtuv/";
        }
        for (int i=p.length()-1; i>0; i--) {
            if (p.charAt(i) == '/') return repoPath(p.substring(0,i));
        }
        return null;
    }
    
}
