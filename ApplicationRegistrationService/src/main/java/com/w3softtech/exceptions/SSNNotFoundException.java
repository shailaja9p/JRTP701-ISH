package com.w3softtech.exceptions;

public class SSNNotFoundException extends RuntimeException {

	public SSNNotFoundException() {
		super();
	}

	public SSNNotFoundException(String msg) {
		super(msg);
	}
}
