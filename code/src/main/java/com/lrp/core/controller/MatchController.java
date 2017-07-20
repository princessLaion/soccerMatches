package com.lrp.core.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lrp.core.domain.ParentMatch;
import com.lrp.core.exception.ErrorInfo;
import com.lrp.core.exception.IDNotExistsException;
import com.lrp.core.exception.InvalidSearchCriteriaException;
import com.lrp.core.service.MatchesService;

@Controller
public class MatchController {

	@Autowired
	private MatchesService matchesService;
	private final Logger LOGGER = LoggerFactory.getLogger(MatchController.class);
	
	/**
	 * 01 User Story - Persist and retrieve match information/difficulty
	 * @param soccerMatch
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/api/v1/add", method = RequestMethod.POST,
	           produces = MediaType.APPLICATION_JSON_VALUE,
	           consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ParentMatch  addMatches(@RequestBody ParentMatch soccerMatch) throws ParseException{
		LOGGER.info("Invoking addMatches . . .");		
		return matchesService.addMatches(soccerMatch);
	}
	
	/**
	 * 02 User Story - search for soccer matches
	 * @param home
	 * @param away
	 * @param Match_Difficult
	 * @return
	 * @throws ParseException
	 * @throws InvalidSearchCriteriaException 
	 */
	@RequestMapping (value = "/api/v1/search", method = RequestMethod.GET)
	public @ResponseBody ParentMatch searchMatches(@RequestParam(defaultValue = "") String home,
			@RequestParam(defaultValue = "") String away, 
			@RequestParam (defaultValue = "") String Match_Difficult,
			@RequestParam (defaultValue = "") String start_date,
			@RequestParam (defaultValue = "") String end_date
			) throws ParseException, InvalidSearchCriteriaException {
		LOGGER.info("Invoking searchMatches . . . Match_Difficult: " + Match_Difficult + ",home:" + home);
		return matchesService.retrieveMatches(home, away, Match_Difficult, start_date, end_date);
	}	
		
	/**
	 * 03 User Story -  Update existing data
	 * 				If ID not exist, do not save to the database
	 * @param soccerMatch
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/api/v1/update", method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE,
	        consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ParentMatch updateMatches(@RequestBody ParentMatch soccerMatch) throws Exception{
		LOGGER.info("Invoking updateMatches . . .");
		return matchesService.updateMatches(soccerMatch);
	}
		
	/**
	 * TO validate if webservice is up
	 * @return
	 */
	@RequestMapping (value = "/api/v1/ping", method = RequestMethod.GET)
	public @ResponseBody String helloWorld() {
		LOGGER.info("Invoking ping . . .");
		return "Hello World";
	}

	public void setMatchesService(MatchesService matchesService) {
		this.matchesService = matchesService;
	}

	public MatchesService getMatchesService() {
		return matchesService;
	}

	//--------------- EXCEPTION HANDLING ---------------
	@ExceptionHandler(IDNotExistsException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo handleIDNotExistsException(HttpServletRequest req, IDNotExistsException ex) {
		LOGGER.info("Exception encountered. Invoking handleIDNotExistsException . . .");		
		return new ErrorInfo(ex.getName(), ex.getMessage());
	}
	
	@ExceptionHandler(ParseException.class)
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ErrorInfo handleParseException(HttpServletRequest req, ParseException ex) {
		LOGGER.info("Exception encountered. Invoking handleParseException . . .");

		return new ErrorInfo("PARSE_EXCEPTION", ex.getMessage());
	}
		
	@ExceptionHandler(InvalidSearchCriteriaException.class)
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ErrorInfo handleInvalidSearchCriteriaException(HttpServletRequest req, InvalidSearchCriteriaException ex) {
		LOGGER.info("Exception encountered. Invoking handleInvalidSearchCriteriaException . . .");

		return new ErrorInfo(ex.getName(), ex.getMessage());
	}
	
}
