/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import testex.DateFormatter;
import testex.JokeException;

/**
 *
 * @author williambech
 */
public class DateFormatterTest {
    
    DateFormatter dateFormatter;
    Date date;
    
    private String timeZone;
    
    @Before
    public void InitialSetUp() {
        dateFormatter = new DateFormatter();
    }
    
    @Test
    public void localTimeZone() throws Exception {
        date = new Date();
        timeZone = "Europe/Copenhagen";
        String dateFormat = "dd MMM yyyy hh:mm aa";
        DateFormat simpleFormat = new SimpleDateFormat(dateFormat);
        
        String comparer = simpleFormat.format(date);
        String result = dateFormatter.getFormattedDate(timeZone, date);
        assertEquals(comparer, result);
    }
    
    @Test(expected=JokeException.class)
    public void invalidInputException() throws JokeException {
        date = new Date();
        timeZone = "blahblahblah";
        dateFormatter.getFormattedDate(timeZone, date);
    }    
}
