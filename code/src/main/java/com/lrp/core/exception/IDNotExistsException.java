package com.lrp.core.exception;

/**
 * lpenarubia
 */

public class IDNotExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	private String name;

    public IDNotExistsException(String name) {
        super();
        this.name = name;
    }

    public IDNotExistsException(String name, String message) {
        super(message);
        this.name = name;
    }

    public IDNotExistsException(Throwable cause) {
        super(cause);
    }

    public IDNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}