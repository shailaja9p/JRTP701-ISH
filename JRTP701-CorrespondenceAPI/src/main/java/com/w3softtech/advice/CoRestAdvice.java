package com.w3softtech.advice;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CoRestAdvice {

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException fne){
		return new ResponseEntity<String>(fne.getMessage(),HttpStatus.OK);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllExceptions(Exception e){
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.OK);
	}
}
