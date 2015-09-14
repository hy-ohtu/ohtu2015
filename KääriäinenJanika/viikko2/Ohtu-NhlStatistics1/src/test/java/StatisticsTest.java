/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import ohtuesimerkki.Player;
import ohtuesimerkki.Reader;
import ohtuesimerkki.Statistics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author janikaka
 */
public class StatisticsTest {
    Statistics stats;
    Reader readerStub = new ReaderStub();
    
    public StatisticsTest() {
        stats = new Statistics(readerStub);
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void search() {
        Player pelaaja = new Player("Semenko", "EDM", 4, 12);
        assertEquals(pelaaja.getName(), stats.search("Semenko").getName());
        pelaaja = new Player("Janika", "KMF", 100000, 2012010010);
        assertEquals(null, stats.search("janika"));
    }
    
    @Test
    public void team() {
        ArrayList<Player> lista = new ArrayList();
        lista.add(new Player("Semenko", "EDM", 4, 12));
        lista.add(new Player("Kurri",   "EDM", 37, 53));
        lista.add(new Player("Gretzky", "EDM", 35, 89));
        List<Player> verrattava = stats.team("EDM");
        
        assertEquals(lista.size(), verrattava.size());
        Collections.sort(verrattava);
        Collections.sort(lista);
        Iterator<Player> eka = lista.iterator();
        Iterator<Player> toka = verrattava.iterator();
        while(eka.hasNext()) {
            Player ekaSeuraava = eka.next();
            Player tokaSeuraava = toka.next();
            assertEquals(ekaSeuraava.getAssists(), tokaSeuraava.getAssists());
            assertEquals(ekaSeuraava.getGoals(), tokaSeuraava.getGoals());
            assertEquals(ekaSeuraava.getName(), tokaSeuraava.getName());
            assertEquals(ekaSeuraava.getPoints(), tokaSeuraava.getPoints());
            assertEquals(ekaSeuraava.getTeam(), tokaSeuraava.getTeam());
        }
    }
    
    @Test
    public void topScores() {
        ArrayList<Player> lista = new ArrayList();
        lista.add(new Player("Semenko", "EDM", 4, 12));
        lista.add(new Player("Kurri",   "EDM", 37, 53));
        lista.add(new Player("Gretzky", "EDM", 35, 89));
        lista.add(new Player("Lemieux", "PIT", 45, 54));
        lista.add(new Player("Yzerman", "DET", 42, 56));
        List<Player> verrattava = stats.topScorers(4);
        
        assertEquals(lista.size(), verrattava.size());
        Collections.sort(verrattava);
        Collections.sort(lista);
        Iterator<Player> eka = lista.iterator();
        Iterator<Player> toka = verrattava.iterator();
        while(eka.hasNext()) {
            Player ekaSeuraava = eka.next();
            Player tokaSeuraava = toka.next();
            assertEquals(ekaSeuraava.getAssists(), tokaSeuraava.getAssists());
            assertEquals(ekaSeuraava.getGoals(), tokaSeuraava.getGoals());
            assertEquals(ekaSeuraava.getName(), tokaSeuraava.getName());
            assertEquals(ekaSeuraava.getPoints(), tokaSeuraava.getPoints());
            assertEquals(ekaSeuraava.getTeam(), tokaSeuraava.getTeam());
        }
    }
    
}

class ReaderStub implements Reader {

    @Override
    public List<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));

            return players;
    }
    
}

//class Reader {
//    public List<Player> getPlayers() {
//        return new ArrayList<Player>();
//    }
//}
