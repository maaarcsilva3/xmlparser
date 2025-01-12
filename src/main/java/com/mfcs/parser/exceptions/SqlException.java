package com.mfcs.parser.exceptions;

public class SqlException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SqlException(String message) {
		super("Error encountered while processing SQL query. " + message);
	}
}
