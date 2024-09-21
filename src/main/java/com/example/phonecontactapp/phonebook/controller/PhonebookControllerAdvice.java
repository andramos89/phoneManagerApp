package com.example.phonecontactapp.phonebook.controller;

import com.example.phonecontactapp.phonebook.models.exceptions.InvalidNameException;
import com.example.phonecontactapp.phonebook.models.exceptions.InvalidPhoneNumberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class PhonebookControllerAdvice {

	@ExceptionHandler(InvalidPhoneNumberException.class)
	public ResponseEntity<?> handleInvalidBookingException(InvalidPhoneNumberException ex, WebRequest request) {
		if (ex.getMessage() == null || ex.getDetail() == null) {
			return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (ex.getDetail().contains("Invalid phone number")) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		if (ex.getDetail().contains("Phone number is null or empty")) {
			return new ResponseEntity<>(ex.getDetail(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidNameException.class)
	public ResponseEntity<?> handleInvalidBookingException(InvalidNameException ex, WebRequest request) {
		if (ex.getMessage() == null ) {
			return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
