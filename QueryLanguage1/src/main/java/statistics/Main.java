package statistics;

import statistics.matcher.*;

public class Main {
    public static void main(String[] args) {
        Statistics stats = new Statistics(new PlayerReaderImpl("http://nhlstats-2013-14.herokuapp.com/players.txt"));
          
        QueryBuilder b = new QueryBuilder();
        
        Matcher m = b.hasAtLeast(10, "goals")
                .hasAtLeast(10, "assists")
                .playsIn("PHI")
                .build();
        
        for (Player player : stats.matches(m)) {
            System.out.println( player );
        }
        
        System.out.println("---");
        
        Matcher m2
                = b.oneOf(b.hasFewerThan(3, "goals"),
                          b.hasFewerThan(3, "assists"))
                .not(b.playsIn("PHI"))
                .build();

        for (Player player : stats.matches(m2)) {
            System.out.println( player );
        }
               
    }
}
