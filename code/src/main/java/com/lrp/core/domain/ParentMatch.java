package com.lrp.core.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.lrp.core.dto.MatchDTO;
import com.lrp.core.dto.ParentMatchDTO;
import com.lrp.core.util.Util;

public class ParentMatch {

	private Matches matches;

	public Matches getMatches() {
		return matches;
	}

	public void setMatches(Matches matches) {
		this.matches = matches;
	}
	
	/**
	 * Convert Object and set format to date field
	 * @param parentMatchDto
	 * @return
	 * @throws ParseException
	 */
	public static ParentMatch convertToDomain(ParentMatchDTO parentMatchDto) throws ParseException {
		
		ParentMatch parentMatch = new ParentMatch();
		Matches matches = new Matches();
		List<Match> matchList = new ArrayList<Match>();
		
		
		for (MatchDTO matchDTO : parentMatchDto.getMatches().getMatch()) {
			Match match = new Match();
			match.setId(matchDTO.getId());
			match.setLeague(matchDTO.getLeague());
			match.setHome(matchDTO.getHome());
			match.setAway(matchDTO.getAway());
			match.setDate(Util.formatFromDBDateToInputDate(matchDTO.getDate()));
			match.setHome_score(matchDTO.getHome_score());
			match.setAway_score(matchDTO.getAway_score());
			match.setMatch_difficult(matchDTO.getMatch_difficult());
			matchList.add(match);			
		}
		
		matches.setMatch(matchList);
		parentMatch.setMatches(matches);
		
		return parentMatch;
	}
}
