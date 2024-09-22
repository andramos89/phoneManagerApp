package com.example.phonecontactapp.phonebook.models.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneContactNotFoundException extends RuntimeException {
	public PhoneContactNotFoundException(String message) {
		super(message);
	}
}
