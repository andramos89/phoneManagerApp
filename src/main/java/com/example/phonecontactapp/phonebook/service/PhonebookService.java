package com.example.phonecontactapp.phonebook.service;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhonebookService {

	@Autowired
	PhoneValidatorService phoneValidatorService;
	public PhoneRecord findByContactId(String contactId) {
		return null;
	}

	public PhoneRecord findByPhoneNumber(String phoneNumber) {
		if(phoneValidatorService.isValidPhoneNumber(phoneNumber));
		return new PhoneRecord();
	}

	public PhoneRecord findByName(String name) {
		return null;
	}

}
