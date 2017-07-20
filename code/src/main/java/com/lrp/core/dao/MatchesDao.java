package com.lrp.core.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.lrp.core.domain.ParentMatch;
import com.lrp.core.dto.MatchDTO;
import com.lrp.core.dto.ParentMatchDTO;

public interface MatchesDao{
	
	/**
	 * Save record on DB
	 * 		compute match difficulty based on home_score - away_score
	 * 		convert date format to dd/mm/yyyy	
	 * @param parentMatch
	 * @throws ParseException
	 */
	public void addMatches(ParentMatchDTO parentMatch) throws ParseException;
	
	/**
	 * Update match data from database
	 * @param soccerMatch
	 */
	public void updateMatches(ParentMatchDTO parentMatch);
	
	/**
	 * Retrieve matches based on search criteria
	 * @param homeParam
	 * @param awayParam
	 * @param matchDifficultParam
	 * @param startDateParam
	 * @param endDateParam
	 * @return
	 */
	public List<MatchDTO> retrieveMatches(String homeParam, String awayParam, 
			String matchDifficultParam, String startDateParam, String endDateParam) throws ParseException;
	
	/**
	 * Retrieve matches based on IDs
	 * @param parentMatch
	 * @return
	 */
	public Map<String, MatchDTO> retrieveMatchesByIds(ParentMatch parentMatch);
}
