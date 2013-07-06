package com.example.swp_ucd_2013_eule.net;

public class APIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4591556071021991260L;

	public APIException() {
		super("Fehler bei der Datenübertragung!");
	}

	public APIException(String msg) {
		super(msg);
	}

}
