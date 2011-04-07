package com.thoughtworks.kosha.data;

public class KoshaException extends RuntimeException {

    private static final long serialVersionUID = -5679346154941633810L;

    public KoshaException(String message, Throwable exception) {
	super(message, exception);
    }

}
