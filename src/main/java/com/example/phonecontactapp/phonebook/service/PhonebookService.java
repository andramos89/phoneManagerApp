package com.example.phonecontactapp.phonebook.service;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import com.example.phonecontactapp.phonebook.models.exceptions.InvalidNameException;
import com.example.phonecontactapp.phonebook.models.exceptions.InvalidPhoneNumberException;
import com.example.phonecontactapp.phonebook.service.repositories.PhoneRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PhonebookService {

	@Autowired
	PhoneValidatorService phoneValidatorService;

	@Autowired
	PhoneRecordRepository phoneRecordRepository;

	public PhoneRecord findByContactId(UUID contactId) {
		return phoneRecordRepository.findByContactId(contactId);
	}

	public PhoneRecord findByPhoneNumber(String phoneNumber, String countryCode) throws InvalidPhoneNumberException {
		if (!phoneValidatorService.isValidPhoneNumber(phoneNumber, countryCode)) {
			return phoneRecordRepository.findByPhoneNumberIgnoreCase(phoneNumber).orElse(new PhoneRecord());
		}

		return new PhoneRecord();
	}

	public PhoneRecord findByName(String name) {
		if (name == null || name.isEmpty()) {
			throw new InvalidNameException("Name cannot be empty");
		}
		return phoneRecordRepository.findByNameIgnoreCase(name);
	}

	public List<PhoneRecord> findAll() {
		return phoneRecordRepository.findAll();
	}

	public PhoneRecord createPhoneRecord(PhoneRecord phoneRecord) throws InvalidPhoneNumberException {

		phoneValidatorService.isValidPhoneNumber(phoneRecord.getPhoneNumber(), phoneRecord.getCountryCode());
		if (phoneRecord.getName() == null || phoneRecord.getName().isBlank()) {
			throw new InvalidNameException("Name cannot be empty");
		}
		return phoneRecordRepository.save(phoneRecord);

	}
}
