package statistics;

import statistics.matcher.*;

public class Main {
    public static void main(String[] args) {
        Statistics stats = new Statistics(new PlayerReaderImpl("http://nhlstats-2013-14.herokuapp.com/players.txt"));
          
        Matcher m = new And( new HasAtLeast(10, "goals"),
                             new HasAtLeast(10, "assists"),
                             new PlaysIn("PHI")
        );
        
        for (Player player : stats.matches(m)) {
            System.out.println( player );
        }
        
        System.out.println("---");
        
        Matcher m2 = new And( new Or(new HasFewerThan(3, "goals"),
                                     new HasFewerThan(3, "assists")),
                              new Not(new PlaysIn("PHI"))
        );
        
        for (Player player : stats.matches(m2)) {
            System.out.println( player );
        }
    }
}
