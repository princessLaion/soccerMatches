package com.lrp.core.dto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.lrp.core.domain.Match;
import com.lrp.core.domain.Matches;
import com.lrp.core.domain.ParentMatch;
import com.lrp.core.util.Util;

public class ParentMatchDTO {

	private MatchesDTO matches;

	public MatchesDTO getMatches() {
		return matches;
	}

	public void setMatches(MatchesDTO matches) {
		this.matches = matches;
	}
	
	public static ParentMatchDTO convertToDTO(ParentMatch parentMatch) throws ParseException {
		
		ParentMatchDTO parentMatchDTO = new ParentMatchDTO();
		MatchesDTO matchesDTO = new MatchesDTO();
		List<MatchDTO> matchDTOList = new ArrayList<MatchDTO>();
		
		for (Match match : parentMatch.getMatches().getMatch()) {
			MatchDTO matchDTO = new MatchDTO();
			matchDTO.setId(match.getId());
			matchDTO.setLeague(match.getLeague());
			matchDTO.setHome(match.getHome());
			matchDTO.setAway(match.getAway());
			matchDTO.setDate(Util.formatFromInputDateToDBDate(match.getDate()));
			matchDTO.setHome_score(match.getHome_score());
			matchDTO.setAway_score(match.getAway_score());
			matchDTO.setMatch_difficult(Util.getMatchDifficulty(match));
			
			matchDTOList.add(matchDTO);
			
		}		
		
		matchesDTO.setMatch(matchDTOList);
		parentMatchDTO.setMatches(matchesDTO);
		
		return parentMatchDTO;
	}
	

}
