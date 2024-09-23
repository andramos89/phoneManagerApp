package com.example.phonecontactapp.phonebook.service;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import com.example.phonecontactapp.phonebook.models.exceptions.InvalidNameException;
import com.example.phonecontactapp.phonebook.models.exceptions.InvalidPhoneNumberException;
import com.example.phonecontactapp.phonebook.models.exceptions.PhoneContactNotFoundException;
import com.example.phonecontactapp.phonebook.service.repositories.PhoneRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PhonebookService {

	@Autowired
	PhoneValidatorService phoneValidatorService;

	@Autowired
	PhoneRecordRepository phoneRecordRepository;

	public PhoneRecord findByContactId(UUID contactId) {
		PhoneRecord phoneRecord = phoneRecordRepository.findByContactId(contactId);
		if (phoneRecord == null) {
			throw new PhoneContactNotFoundException("Contact with id " + contactId + " not found");
		}
		return phoneRecord;
	}

	public PhoneRecord findByPhoneNumber(String phoneNumber, String countryCode) throws InvalidPhoneNumberException, PhoneContactNotFoundException {
		if (!phoneValidatorService.isValidPhoneNumber(phoneNumber, countryCode)) {
			throw new InvalidPhoneNumberException(phoneNumber, countryCode);
		}
		PhoneRecord phoneRecord = phoneRecordRepository.findByPhoneNumberAndCountryCodeIgnoreCase(phoneNumber, countryCode);
		if (phoneRecord == null) {
			throw new PhoneContactNotFoundException("Phone Contact Not Found");
		}
		return phoneRecord;
	}

	public List<PhoneRecord> findByCountryCode(String countryCode) throws InvalidPhoneNumberException, PhoneContactNotFoundException {

		return phoneRecordRepository.findByCountryCodeIgnoreCase(countryCode);

	}

	public List<PhoneRecord> findByName(String name) {
		if (name == null || name.isEmpty()) {
			throw new InvalidNameException("Name cannot be empty");
		}
		List<PhoneRecord> phoneRecord = phoneRecordRepository.findByNameIgnoreCase(name);
		if (phoneRecord == null) {
			throw new PhoneContactNotFoundException("Phone Contacts Not Found");
		}
		return phoneRecord;
	}

	public List<PhoneRecord> findAll() {
		return phoneRecordRepository.findAll();
	}

	public PhoneRecord createPhoneRecord(PhoneRecord phoneRecord) throws InvalidPhoneNumberException {

		phoneValidatorService.isValidPhoneNumber(phoneRecord.getPhoneNumber(), phoneRecord.getCountryCode());
		if (phoneRecord.getName() == null || phoneRecord.getName().isBlank()) {
			throw new InvalidNameException("Name cannot be empty");
		}
		PhoneRecord stored = phoneRecordRepository.findByPhoneNumberAndCountryCodeIgnoreCase(phoneRecord.getPhoneNumber(), phoneRecord.getCountryCode());
		if (stored != null) {
			//update instead of insert
			phoneRecord.setContactId(stored.getContactId());
		}

		return phoneRecordRepository.save(phoneRecord);

	}

	public List<PhoneRecord> findAllFiltered(UUID contactId, String name, String countryCode, String phoneNumber) {

		ArrayList<PhoneRecord> phoneRecords = new ArrayList<>();
		//yes, we could have done this with a simpler strategy.
		if (contactId != null ){
			phoneRecords.add(findByContactId(contactId));
		} else if (name != null && !name.isEmpty() && countryCode != null && !countryCode.isEmpty() && phoneNumber != null && !phoneNumber.isEmpty()){
			phoneRecords.addAll(phoneRecordRepository.findByNameAndCountryCodeIgnoreCaseAndPhoneNumber(name, countryCode, phoneNumber));
		} else if (name != null && !name.isEmpty() && countryCode != null && !countryCode.isEmpty()){
			phoneRecords.addAll(phoneRecordRepository.findByNameAndCountryCodeIgnoreCase(name, countryCode));
		} else if (name != null ){
			phoneRecords.addAll(findByName(name));
		}else if (phoneNumber != null && !phoneNumber.isBlank() && countryCode != null && !countryCode.isBlank()){
			phoneRecords.add(findByPhoneNumber(phoneNumber, countryCode));
		}else if(phoneNumber != null && !phoneNumber.isBlank() ){
			phoneRecords.addAll(phoneRecordRepository.findByPhoneNumber(phoneNumber));
		} else if (countryCode != null) {
			phoneRecords.addAll(phoneRecordRepository.findByCountryCodeIgnoreCase(countryCode));
		}else {
			phoneRecords.addAll(findAll());
		}

		return phoneRecords;
	}
}
