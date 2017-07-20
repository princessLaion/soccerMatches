package com.lrp.core.dao.impl;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lrp.core.dao.MatchesDao;
import com.lrp.core.domain.Match;
import com.lrp.core.domain.ParentMatch;
import com.lrp.core.dto.MatchDTO;
import com.lrp.core.dto.ParentMatchDTO;
import com.lrp.core.util.Util;

public class MatchesDaoImpl implements MatchesDao{

	private final Logger LOGGER = LoggerFactory.getLogger(MatchesDaoImpl.class);
	private SessionFactory sessionFactory;
	
	public MatchesDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	} 
		
	@Override
	public void addMatches(ParentMatchDTO parentMatch) throws ParseException{
		LOGGER.info("MatchesDaoImpl - Invoking addMatches");
		Map<String, MatchDTO> matchDtoMap = retrieveMatchesByIds(ParentMatch.convertToDomain(parentMatch));
		for(MatchDTO match : parentMatch.getMatches().getMatch()) {
			if(!matchDtoMap.containsKey(match.getId())) {
				//Only persist if data not exists on DB
				Util.setDefaultValue(match);
				sessionFactory.getCurrentSession().persist(match);
			}				
		}
				
	}
	
	public void updateMatches(ParentMatchDTO parentMatchDto) {
		LOGGER.info("MatchesDaoImpl - Invoking updateMatches");
		for(MatchDTO matchDto : parentMatchDto.getMatches().getMatch()) {
			Util.setDefaultValue(matchDto);
			sessionFactory.getCurrentSession().merge(matchDto);
		}
		
	}
		
	/**
	 * Retrieve existing soccer match based by IDs
	 * @param parentMatch
	 * @return map of matchDTO where the Key is the match ID
	 */
	public Map<String, MatchDTO> retrieveMatchesByIds(ParentMatch parentMatch) {
		StringBuilder sbIds = new StringBuilder();
		for(Match match : parentMatch.getMatches().getMatch()) {
			//concat IDs to be used for search criteria
			sbIds.append(match.getId() + ",");
		}
		
		//Remove the last comma(,) on sbIds
		String iDs = sbIds.substring( 0 , sbIds.length() - 1); 
	
		final Query query = sessionFactory.getCurrentSession().createQuery("from MatchDTO where id in (" + iDs + ")");
		
		List<MatchDTO> result = null;

		result = query.list();
		
		Map<String, MatchDTO> soccerMatchesByIDsMap = new HashMap<String, MatchDTO>();
		if(result != null) {
			for(MatchDTO matchDTO : result) {
				soccerMatchesByIDsMap.put(matchDTO.getId(), matchDTO);
			}
		}
		return soccerMatchesByIDsMap;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	/**
	 * Generate query string for searching the soccer_match table
	 * @param home
	 * @param away
	 * @param matchDifficult
	 * @return concatenated string to be used on Query
	 */
	private String generateQueryString(String home, String away, String matchDifficult, String startDate, String endDate)  {
		StringBuilder sbQuery = new StringBuilder("from MatchDTO ");
		boolean isAddAnd = false;
		if(Util.isNotNull(home) || Util.isNotNull(away) || Util.isNotNull(matchDifficult)
				|| Util.isNotNull(startDate) || Util.isNotNull(endDate)) {
			sbQuery.append( " where ");
		}
		
		if(Util.isNotNull(home)) {
			sbQuery.append(" home = :homeParam ");
			isAddAnd = true;
		} 
		
		if(Util.isNotNull(away)) {
			if(isAddAnd) {
				sbQuery.append(" and ");
			}
			sbQuery.append(" away = :awayParam ");
			isAddAnd = true;
		} 
		
		if(Util.isNotNull(matchDifficult)) {
			if(isAddAnd) {
				sbQuery.append(" and ");
			}
			sbQuery.append(" Match_Difficult = :matchDifficultParam ");
		} 
		if(Util.isNotNull(startDate) && Util.isNotNull(endDate)) {
			if(isAddAnd) {
				sbQuery.append(" and ");
			}
			sbQuery.append(" STR_TO_DATE(date, '%d/%m/%Y') between :startDateParam AND :endDateParam ");
		} 	

		return sbQuery.toString();
	}

		
	@Override
	public List<MatchDTO> retrieveMatches(String homeParam, String awayParam, 
				String matchDifficultParam, String startDateParam, String endDateParam) throws ParseException  {
		List<MatchDTO> result = null;
		
		//Remove special char
		homeParam = Util.removeSpecialChar(homeParam);
		awayParam = Util.removeSpecialChar(awayParam);
		matchDifficultParam = Util.removeSpecialChar(matchDifficultParam);
		startDateParam = Util.removeSpecialChar(startDateParam);
		endDateParam = Util.removeSpecialChar(endDateParam);		
		
		LOGGER.info("=====> Parameters for retrievingMatches: "
				+ "homeParam: " + homeParam + 
				", awayParam: " + awayParam + 
				", matchDifficultParam: " + matchDifficultParam +
				", startDateParam: " + startDateParam +
				", endDateParam: " + endDateParam 
				);		
		
		String queryStr = generateQueryString(homeParam, awayParam, matchDifficultParam, startDateParam, endDateParam);
		LOGGER.debug("Query String: " + queryStr);
		
		//String queryStr = " from MatchDTO where STR_TO_DATE(date,  '%d/%m/%Y') between '2016-09-25' AND '2017-06-24'";
		//final Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		final Query query = sessionFactory.getCurrentSession().createQuery(queryStr);

		//set parameter based on passed value from user input
		if(Util.isNotNull(homeParam)) {
			query.setParameter("homeParam", homeParam);
		}
		if(Util.isNotNull(awayParam)) {
			query.setParameter("awayParam", awayParam);
		}
		if(Util.isNotNull(matchDifficultParam)) {
			query.setParameter("matchDifficultParam", matchDifficultParam);
		}

		if(Util.isNotNull(startDateParam) && Util.isNotNull(endDateParam)) {
			query.setParameter("startDateParam", Util.formatFromDBDateToSQLDate(startDateParam));
			query.setParameter("endDateParam", Util.formatFromDBDateToSQLDate(endDateParam));
		}
		result = query.list();
		LOGGER.info("retrieveMatches list result: " + result);
		
		return (result == null) ? Collections.<MatchDTO> emptyList() : result;
	}
	
}
