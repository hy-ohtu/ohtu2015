/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hylje
 */
public class QueryBuilder {
    private final List<Matcher> matchers;
    
    public QueryBuilder() {
        this.matchers = new ArrayList<>();
    }
    
    private QueryBuilder(List<Matcher> matchers, Matcher new_matcher) {
        this.matchers = new ArrayList<>(matchers);
        this.matchers.add(new_matcher);
    }
    
    public Matcher build() {
        Matcher[] matcherArr = new Matcher[matchers.size()];
        matcherArr = matchers.toArray(matcherArr);
        return new And(matcherArr);
    }
    
    public QueryBuilder hasAtLeast(int value, String key) {
        return new QueryBuilder(matchers, new HasAtLeast(value, key));
    }
    
    public QueryBuilder hasFewerThan(int value, String key) {
        return new QueryBuilder(matchers, new HasFewerThan(value, key));
    }
    
    public QueryBuilder not(Matcher matcher) {
        return new QueryBuilder(matchers, new Not(matcher));
    }
    
    public QueryBuilder not(QueryBuilder builder) {
        return new QueryBuilder(matchers, new Not(builder.build()));
    }
    
    public QueryBuilder oneOf(Matcher... newMatchers) {
        return new QueryBuilder(matchers, new Or(newMatchers));
    }
    
    public QueryBuilder oneOf(QueryBuilder... builders) {
        Matcher[] newMatchers = new Matcher[builders.length];
        for (int i=0; i<builders.length; i++) {
            newMatchers[i] = builders[i].build();
        }
        return new QueryBuilder(matchers, new Or(newMatchers));
    } 
    
    public QueryBuilder playsIn(String team) {
        return new QueryBuilder(matchers, new PlaysIn(team));
    }
}
