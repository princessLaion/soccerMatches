package com.lrp.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.lrp.core.domain.Match;
import com.lrp.core.dto.MatchDTO;

public class Util {
	private final static String INPUT_DATE_FORMAT = "dd MMMM yyyy";
	private final static String DB_DATE_FORMAT = "dd/MM/yyyy";
	private final static String SQL_DATE_FORMAT = "yyyy/MM/dd";

	public final static String MATCH_DIFFICULTY_TOUGH_GAME = "Tough_game";
	public final static String MATCH_DIFFICULTY_MAJOR_WIN = "Major_win";
	public final static String MATCH_DIFFICULTY_AVERAGE_GAME = "Average_game";
	
	/**
	 Given the absolute value (x) of (home_score – away_score):
		(x = 0) Match_Difficult = Tough_game;
		(1 <= x <= 2) Match_Difficult = Average_game;
		(x > 2) Match_Difficult = Major_win.
	 * @return match difficulty based on home_score - away_score
	 */
	public static String getMatchDifficulty(Match match){
		match = setDefaultValue(match);
		int home_score = Integer.parseInt(match.getHome_score());
		int away_score = Integer.parseInt(match.getAway_score());		
		
		return getMatchDifficulty(home_score, away_score);
	}
	
	/**
	 * Set default value (0) to home_score and away_score if no value was passed
	 * @param match
	 * @return
	 */
	public static Match setDefaultValue(Match match){
		if(!isNotNull(match.getHome_score())) {
			match.setHome_score("0");
		}
		if(!isNotNull(match.getAway_score())) {
			match.setAway_score("0");
		}
		
		return match;
	}
	
	/**
	 * Set default value (0) to home_score and away_score if no value was passed
	 * @param matchDto
	 * @return
	 */
	public static MatchDTO setDefaultValue(MatchDTO matchDto){
		if(!isNotNull(matchDto.getHome_score())) {
			matchDto.setHome_score("0");
		}
		if(!isNotNull(matchDto.getAway_score())) {
			matchDto.setAway_score("0");
		}
		
		return matchDto;
	}
	
	public static String getMatchDifficulty(MatchDTO match){
		match = setDefaultValue(match);
		int home_score = Integer.parseInt(match.getHome_score());
		int away_score = Integer.parseInt(match.getAway_score());	
		
		return getMatchDifficulty(home_score, away_score);
	}	
	
	/**
	 * 
	 * x = (home_score – away_score)
			(x = 0) Match_Difficult = Tough_game;
			(1 <= x <= 2) Match_Difficult = Average_game;
			(x > 2) Match_Difficult = Major_win.
	 * @param home_score
	 * @param away_score
	 * @return match difficulty based on (home_score – away_score)
	 */
	public static String getMatchDifficulty(int home_score, int away_score){
		String matchDifficulty = null;
	
		int difficulty = Math.abs(home_score - away_score);
		
		if( difficulty == 0 ) {
			matchDifficulty = MATCH_DIFFICULTY_TOUGH_GAME;
		} else if( difficulty > 2 ) {
			matchDifficulty = MATCH_DIFFICULTY_MAJOR_WIN;
		} else {
			matchDifficulty = MATCH_DIFFICULTY_AVERAGE_GAME;
		}		
		
		return matchDifficulty;
	}
	
	/**
	 * Convert input date(dd M yyyy) format to new format (dd/MM/yyyy)
	 * Example: 
	 * 		24 September 2016 convert to: 24/09/2016
	 * @throws ParseException 
	 */
	public static String formatFromInputDateToDBDate( String dateInput ) throws ParseException {
		
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(INPUT_DATE_FORMAT);
		SimpleDateFormat newDateFormat = new SimpleDateFormat(DB_DATE_FORMAT);
		String newDate = null;
		try {
			newDate = newDateFormat.format(inputDateFormat.parse(dateInput));
		} catch (ParseException e) {
		    throw new ParseException(e.getMessage(), 1);
		}
		
		return newDate;
	}
		
	/**
	 * format from DB ("dd/MM/yyyy") to input date format ("dd MMMM yyyy")
	 * @param dateInput
	 * @return
	 * @throws ParseException
	 */
	public static String formatFromDBDateToInputDate( String dateInput ) throws ParseException {
		
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(DB_DATE_FORMAT);
		SimpleDateFormat newDateFormat = new SimpleDateFormat(INPUT_DATE_FORMAT);
		String newDate = null;
		try {
			newDate = newDateFormat.format(inputDateFormat.parse(dateInput));
		} catch (ParseException e) {
		    throw new ParseException(e.getMessage(), 1);
		}
		
		return newDate;
	}
	
	/**
	 * format from user input ("dd/MM/yyyy") to sql input date format ("dd MMMM yyyy")
	 * @param dateValue
	 * @return
	 * @throws ParseException 
	 */
	public static String formatFromDBDateToSQLDate(String dateInput) throws ParseException {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(DB_DATE_FORMAT);
		SimpleDateFormat newDateFormat = new SimpleDateFormat(SQL_DATE_FORMAT);
		String newDate = null;
		try {
			newDate = newDateFormat.format(inputDateFormat.parse(dateInput));
		} catch (ParseException e) {
		    throw new ParseException(e.getMessage(), 1);
		}
		
		return newDate;
	}

	/**
	 * 
	 * @param value
	 * @return true if String have value
	 */
	public static boolean isNotNull(String value) {
		if (value != null && value.trim().length() != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Remove single quote (') and/or double quote (") in the beginning and end of the string
	 * @return
	 */
	public static String removeSpecialChar(String value) {
		return value.replaceAll("^['\"]|['\"]$", "");
	}
	
}
