package com.lrp.core.service.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.lrp.core.dao.MatchesDao;
import com.lrp.core.domain.Match;
import com.lrp.core.domain.ParentMatch;
import com.lrp.core.dto.MatchDTO;
import com.lrp.core.dto.MatchesDTO;
import com.lrp.core.dto.ParentMatchDTO;
import com.lrp.core.exception.IDNotExistsException;
import com.lrp.core.exception.InvalidSearchCriteriaException;
import com.lrp.core.service.MatchesService;
import com.lrp.core.util.Util;

@Service
@Configurable
public class MatchesServiceImpl implements MatchesService{

	@Autowired
	private MatchesDao matchesDao;
	
	@Transactional
	public ParentMatch addMatches(ParentMatch parentMatch) throws ParseException {
		
		ParentMatchDTO parentMatchDto = ParentMatchDTO.convertToDTO(parentMatch);
		matchesDao.addMatches(parentMatchDto);		
		return ParentMatch.convertToDomain(parentMatchDto);		
	}
	
	/**
	 * All IDs from user input should exists on DB for Update webservice call
	 * @param parentMatch
	 * @return true if ALL records exists on DB
	 * @throws IDNotExistsException if not existing ID found from the Request
	 */
	private boolean isRequestIdsExists(ParentMatch parentMatch) throws IDNotExistsException {
	
		Map<String, MatchDTO> dbSoccerMatchDtoMap = matchesDao.retrieveMatchesByIds(parentMatch);
		
		//Validate if all the records from user input exists. If true, proceed with update
		for(Match userInputSoccerMatch : parentMatch.getMatches().getMatch()) {
			
			if(!dbSoccerMatchDtoMap.containsKey(userInputSoccerMatch.getId()) ) {
				throw new IDNotExistsException("ID_Not_found", "ID " + userInputSoccerMatch.getId() + " not found. Data was not persisted on DB. Please add the record first.");
			}
		}
		
		return true;
	}
	
	@Transactional
	public ParentMatch updateMatches(ParentMatch parentMatch) throws IDNotExistsException, ParseException {
		/**
		 * validate if existing
		 * true -> update records
		 * false -> throw exception and do not save on DB
		 */
		ParentMatchDTO parentMatchDto = null;
		if(isRequestIdsExists(parentMatch)) {
			//Update records
			parentMatchDto = ParentMatchDTO.convertToDTO(parentMatch);
			matchesDao.updateMatches(parentMatchDto);
		}
		
		return ParentMatch.convertToDomain(parentMatchDto);
	}
	
	/**
	 * @param startDate
	 * @param endDate
	 * @return true if both the startDate and endDate was provided for search criteria
	 */
	private boolean isDateParamValid(String startDate, String endDate) {
		boolean isValid = true;
		if (Util.isNotNull(startDate) && !Util.isNotNull(endDate)) {
			isValid = false;
		} else if (!Util.isNotNull(startDate) && Util.isNotNull(endDate)) {
			isValid = false;
		}
		return isValid;
	}
	
	@Transactional
	public ParentMatch retrieveMatches(String home, String away, String matchDifficult, String startDate, String endDate) throws ParseException, InvalidSearchCriteriaException {
		
		if(!isDateParamValid(startDate, endDate)) {
			throw new InvalidSearchCriteriaException("INVALID_SEARCH_CRITERIA", "Please provide value for both start_date and end_date");
		}
		List<MatchDTO> matchList = matchesDao.retrieveMatches(home, away, matchDifficult, startDate, endDate);
		ParentMatchDTO parentMatchDto = null;
		ParentMatch parentMatch = null;
		
		if(!matchList.isEmpty() ) {
			parentMatchDto = new ParentMatchDTO();
			MatchesDTO matchesDto = new MatchesDTO();
			matchesDto.setMatch(matchList);
			parentMatchDto.setMatches(matchesDto);
			
			parentMatch = ParentMatch.convertToDomain(parentMatchDto);

		} 		
		return parentMatch;
	}
	
	
	public void setMatchesDao(MatchesDao matchesDao) {
		this.matchesDao = matchesDao;
	}

	public MatchesDao getMatchesDao() {
		return matchesDao;
	}
	
	
}
