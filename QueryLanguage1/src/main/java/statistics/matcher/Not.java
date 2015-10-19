/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.matcher;

import statistics.Player;

/**
 *
 * @author hylje
 */
public class Not implements Matcher {
    Matcher matcher;
    
    public Not (Matcher matcher) {
        this.matcher = matcher;
    }
    
    @Override
    public boolean matches(Player p) {
        return !this.matcher.matches(p);
    }
    
}
