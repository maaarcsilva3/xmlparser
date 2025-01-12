package com.mfcs.parser.exceptions;

public class JsonException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonException(String message) {
		super("Error creating JSON File. " + message);
	}
}
