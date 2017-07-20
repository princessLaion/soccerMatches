package com.lrp.core.domain;

public class Match {

	
	public Match () {
		
	}
	
	public Match (String id, String league, String home, String away, String date, String home_score, String away_score) {
		this.id = id;
		this.league = league;
		this.home = home;
		this.away = away;
		this.date = date;
		this.home_score = home_score;
		this.away_score = away_score;
	}
	
//	@Id
	private String id;
	private String league;
	
	private String home;
	private String away;
	
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd MMM yyyy") -> working, but neet to reformat
	private String date;
		
	private String home_score;
	private String away_score;
	private String match_difficult;
	
	public String getId() {
		return id;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getLeague() {
		return league;
	}
	public void setLeague(String league) {
		this.league = league;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getAway() {
		return away;
	}

	public void setAway(String away) {
		this.away = away;
	}

	public String getHome_score() {
		return home_score;
	}

	public void setHome_score(String home_score) {
		this.home_score = home_score;
	}

	public String getAway_score() {
		return away_score;
	}

	public void setAway_score(String away_score) {
		this.away_score = away_score;
	}

	public String getMatch_difficult() {
		return match_difficult;
	}

	public void setMatch_difficult(String match_difficult) {
		this.match_difficult = match_difficult;
	}
	
}
