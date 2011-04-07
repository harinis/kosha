package com.thoughtworks.kosha.skype;

public class SkypeException extends RuntimeException {
    private static final long serialVersionUID = 7806840075550543882L;

    public SkypeException(String message, Throwable exception) {
	super(message, exception);
    }
}
