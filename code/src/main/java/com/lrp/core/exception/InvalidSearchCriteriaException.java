package com.lrp.core.exception;

/**
 * lpenarubia
 */

public class InvalidSearchCriteriaException extends Exception {

	private static final long serialVersionUID = 1L;
	private String name;

    public InvalidSearchCriteriaException(String name) {
        super();
        this.name = name;
    }

    public InvalidSearchCriteriaException(String name, String message) {
        super(message);
        this.name = name;
    }

    public InvalidSearchCriteriaException(Throwable cause) {
        super(cause);
    }

    public InvalidSearchCriteriaException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}