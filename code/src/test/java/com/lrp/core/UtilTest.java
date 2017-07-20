package com.lrp.core;

import java.text.ParseException;

import com.lrp.core.domain.Match;
import com.lrp.core.util.Util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Util.
 */
public class UtilTest 
    extends TestCase
{
	private Match match1;
	private Match match2;
	private Match match3;
	private Match match4;
	
	private Match match5;//no value provided for home score
	private Match match6;//no value provided for away score
	protected void setUp() throws Exception {
		match1 = new Match ("5001", "Premier League", "Arsenal", "Aston Villa", "24 September 2016", "2", "2");//tough
		match2 = new Match ("5002", "Premier League", "Arsenal", "Chelsea", "24 September 2016", "3", "0");//major
		match3 = new Match ("5003", "Premier League", "Arsenal", "Everton", "24 September 2016", "1", "3");//average
		match4 = new Match ("5003", "Premier League", "Arsenal", "Everton", "24 September 2016", "1", "5");//major
		
		match5 = new Match ("5003", "Premier League", "Arsenal", "Everton", "24 September 2016", null, "4");//major
		match6 = new Match ("5003", "Premier League", "Arsenal", "Everton", "24 September 2016", "4", null);//major
	}
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UtilTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( UtilTest.class );
    }

    /**
     * Start of test
     */
    public void testMatchDifficultyBasedOnHomeAndAwayScore()
    {
        assertEquals(Util.MATCH_DIFFICULTY_TOUGH_GAME, Util.getMatchDifficulty(match1));
        assertEquals(Util.MATCH_DIFFICULTY_MAJOR_WIN, Util.getMatchDifficulty(match2));
        assertEquals(Util.MATCH_DIFFICULTY_AVERAGE_GAME, Util.getMatchDifficulty(match3));
        assertEquals(Util.MATCH_DIFFICULTY_MAJOR_WIN, Util.getMatchDifficulty(match4));
    }
    
    public void testMatchDifficultyIfNoValueWasProvidedForHomeAndAwayScore()
    {
    	assertEquals(Util.MATCH_DIFFICULTY_MAJOR_WIN, Util.getMatchDifficulty(match5));
    	assertEquals(Util.MATCH_DIFFICULTY_MAJOR_WIN, Util.getMatchDifficulty(match6));
    }
    
    public void testFormatFromInputDateToDBDate() throws ParseException {
    	assertEquals("24/09/2016", Util.formatFromInputDateToDBDate("24 September 2016"));
    }
    
    public void testExceptionWhenParsingWrongDateFormat() {
    	boolean invalidFormat = false;
    	try{
    		Util.formatFromInputDateToDBDate("24 September AA");
    	} catch (ParseException e) {
    		invalidFormat = true;
    	}
    	
    	assertTrue(invalidFormat);
    }
    
    public void testFormatFromDBDateToSQLDate() throws ParseException{
    	assertEquals("2016/09/24", Util.formatFromDBDateToSQLDate("24/09/2016"));
    }
    
    public void testFormatFromDBDateToInputDate() throws ParseException{
    	assertEquals("24 September 2016", Util.formatFromDBDateToInputDate("24/09/2016"));
    }
    
    public void testIsNotNull() {
    	assertNotNull(Util.isNotNull("WithValue"));
    	assertTrue(Util.isNotNull("Hello World"));
    }
    
    public void testShouldReturnFalseForIsNotNull() {
    	assertFalse(Util.isNotNull(" "));
    	assertFalse(Util.isNotNull(null));
    	assertFalse(Util.isNotNull(""));
    }
    
    public void testRemoveSingleOrDoubleQuoteAtStartAndEndOfString(){
    	assertEquals("Hello World", Util.removeSpecialChar("\"Hello World\""));
    	assertEquals("Hello World", Util.removeSpecialChar("'Hello World'"));
    	assertEquals("Hello' World", Util.removeSpecialChar("'Hello' World'"));
    }
    
}
