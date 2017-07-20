package com.lrp.core.service;

import java.text.ParseException;

import com.lrp.core.domain.ParentMatch;
import com.lrp.core.exception.IDNotExistsException;
import com.lrp.core.exception.InvalidSearchCriteriaException;

public interface MatchesService {

	public ParentMatch addMatches(ParentMatch parentMatch) throws ParseException;

	public ParentMatch updateMatches(ParentMatch soccerMatch) throws IDNotExistsException, ParseException;

	public ParentMatch retrieveMatches(String home, String away, String matchDifficult, String startDate, String endDate) throws ParseException, InvalidSearchCriteriaException  ;
}
