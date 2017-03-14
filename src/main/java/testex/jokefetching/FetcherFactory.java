/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testex.jokefetching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import testex.JokeException;

/**
 *
 * @author williambech
 */
public class FetcherFactory implements IFetcherFactory {

    private final List<String> availableTypes = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");
    private EduJoke eduJoke = new EduJoke();
    private ChuckNorris chuckNorris = new ChuckNorris();
    private Moma moma = new Moma();
    private Tambal tambal = new Tambal();

    @Override
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    @Override
    public List<IJokeFetcher> getJokeFetchers(String jokeFetch) {
        List<IJokeFetcher> jokes = new ArrayList<IJokeFetcher>();
        String[] tokens = jokeFetch.split(",");

        try {
            for (String token : tokens) {
                if(!getAvailableTypes().contains(token)) {
                    throw new JokeException("hej");
                }
                
                switch (token) {
                    case "eduprog":
                        jokes.add((IJokeFetcher) Class.forName("testex.jokefetching.EduJoke").newInstance());
                        break;
                    case "chucknorris":
                        jokes.add((IJokeFetcher) Class.forName("testex.jokefetching.ChuckNorris").newInstance());
                        break;
                    case "moma":
                        jokes.add((IJokeFetcher) Class.forName("testex.jokefetching.Moma").newInstance());
                        break;
                    case "tambal":
                        jokes.add((IJokeFetcher) Class.forName("testex.jokefetching.Tambal").newInstance());
                        break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(FetcherFactory.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return jokes;
    }

}
