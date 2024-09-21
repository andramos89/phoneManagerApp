package com.example.phonecontactapp.phonebook.models.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidNameException extends RuntimeException {

	public InvalidNameException(String message) {
		super(message);
	}
}
