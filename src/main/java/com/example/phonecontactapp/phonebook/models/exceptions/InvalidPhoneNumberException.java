package com.example.phonecontactapp.phonebook.models.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidPhoneNumberException extends RuntimeException {

	private final String phoneNumber;
	private final String detail;

	public InvalidPhoneNumberException(String phoneNumber, String detail) {
		super(detail + ": " + phoneNumber);
		this.phoneNumber = phoneNumber;
		this.detail = detail;

	}
}
