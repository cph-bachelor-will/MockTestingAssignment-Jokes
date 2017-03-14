/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import testex.IDateFormatter;
import testex.Joke;
import testex.JokeFetcher;
import testex.Jokes;
import testex.jokefetching.ChuckNorris;
import testex.jokefetching.EduJoke;
import testex.jokefetching.IFetcherFactory;
import testex.jokefetching.IJokeFetcher;
import testex.jokefetching.Moma;
import testex.jokefetching.Tambal;

/**
 *
 * @author Ben
 */
@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {
    
    @Mock
    private IDateFormatter dfMock;
    @Mock
    private IFetcherFactory ffMock;
    @Mock
    Moma moma;
    @Mock
    ChuckNorris chuck;
    @Mock
    EduJoke edu;
    @Mock
    Tambal tambal;
    
    Joke testEdu = new Joke("educ", "EducationalJokes.org");
    Joke testChuck = new Joke("el Chucko", "ChuckNorrisJokes.org");
    Joke testMoma = new Joke("mama", "JoMamaJokes.org");
    Joke testTambal = new Joke("Tambala", "TambalaJokes.org");
    
    final String timeZone = "Europe/Copenhagen";
  
    private JokeFetcher jFetcher;
    
    @Before
    public void setup() {
        List<IJokeFetcher> fetchers = Arrays.asList(edu, chuck, moma, tambal);
        when(ffMock.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal")).thenReturn(fetchers);
        List<String> types = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");

        when(ffMock.getAvailableTypes()).thenReturn(types);
        jFetcher = new JokeFetcher(dfMock, ffMock);
        
        given(edu.getJoke()).willReturn(testEdu);
        given(chuck.getJoke()).willReturn(testChuck);
        given(moma.getJoke()).willReturn(testMoma);
        given(tambal.getJoke()).willReturn(testTambal);
    }
    /**
     * Test of getAvailableTypes method, of class JokeFetcher.
     */
    @Test
    public void testGetAvailableTypesFour() {
        assertThat(jFetcher.getAvailableTypes().size(), is(4));
        assertThat(jFetcher.getAvailableTypes(), hasItems("EduJoke", "ChuckNorris", "Moma", "Tambal"));
    }
    
    @Test
    public void testCheckIfValidToken() {
        String validTokens = "EduJoke,ChuckNorris,Moma,Tambal";
        assertThat(jFetcher.isStringValid(validTokens), is(true));
        String invalidTokens = "ben,chuckmorris,mammia,tambo";
        assertThat(jFetcher.isStringValid(invalidTokens), is(false));
    }

    @Test
    public void testGetJokes() throws Exception {
         given(dfMock.getFormattedDate(eq(timeZone), anyObject())).willReturn("14 Mar 2017 12:13 AM");
         assertThat(jFetcher.getJokes("eduprog,chucknorris,moma,tambal", timeZone)
                 .getTimeZoneString(), is("14 Mar 2017 12:13 AM"));
    }
    
     @Test
    public void eduJoke() throws Exception {
        String expectedJoke = "educ";
        String expectedReference = "EducationalJokes.org";
        Jokes jokes = jFetcher.getJokes("EduJoke,ChuckNorris,Moma,Tambal", timeZone);
        assertThat(jokes.getJokes().get(0).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(0).getReference(), is(expectedReference));
    }
    
    @Test
    public void chuckNorrisJoke() throws Exception {
        String expectedJoke = "el Chucko";
        String expectedReference = "ChuckNorrisJokes.org";
        Jokes jokes = jFetcher.getJokes("EduJoke,ChuckNorris,Moma,Tambal", timeZone);
        assertThat(jokes.getJokes().get(1).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(1).getReference(), is(expectedReference));
    }
    
    @Test
    public void yoMamaJoke() throws Exception {
        String expectedJoke = "mama";
        String expectedReference = "JoMamaJokes.org";
        Jokes jokes = jFetcher.getJokes("EduJoke,ChuckNorris,Moma,Tambal", timeZone);
        assertThat(jokes.getJokes().get(2).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(2).getReference(), is(expectedReference));
    }
    
     @Test
    public void tambalaJoke() throws Exception {
        String expectedJoke = "Tambala";
        String expectedReference = "TambalaJokes.org";
        Jokes jokes = jFetcher.getJokes("EduJoke,ChuckNorris,Moma,Tambal", timeZone);
        assertThat(jokes.getJokes().get(3).getJoke(), is(expectedJoke));
        assertThat(jokes.getJokes().get(3).getReference(), is(expectedReference));
    }
   
    
}