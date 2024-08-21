package com.w3softtech.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.w3softtech.exceptions.SSNNotFoundException;

@RestControllerAdvice
public class CitizenAppRegistrationControllerAdvice {

	@ExceptionHandler(SSNNotFoundException.class)
	public ResponseEntity<String> handleSSNNotFoundException(SSNNotFoundException ssnException){
		return new ResponseEntity<String>(ssnException.getMessage(),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllExceptions(Exception exception){
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
