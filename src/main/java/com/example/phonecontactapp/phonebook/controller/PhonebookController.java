package com.example.phonecontactapp.phonebook.controller;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import com.example.phonecontactapp.phonebook.service.PhonebookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/phone/v1/")
@Tag(name = "Phonebook Controller", description = "API for managing phone records")
public class PhonebookController {

	@Autowired
	PhonebookService phonebookService;

	@GetMapping("/byContactId")
	@Operation(summary = "Find phone record by contact id", description = "Retrieve a phone record using the contact id")
	public PhoneRecord findByContactId(UUID contactId) {
		return phonebookService.findByContactId(contactId);
	}

	@GetMapping("/byPhoneNumber")
	@Operation(summary = "Find phone record by phone number and country code", description = "Retrieve a phone record using the phone number and country code")
	public PhoneRecord findByPhoneNumber(String phoneNumber, String countryCode) {
		return phonebookService.findByPhoneNumber(phoneNumber, countryCode);
	}

	@GetMapping("/byName")
	@Operation(summary = "Find phone record by contact name", description = "Retrieve a phone record using the contact name")
	public List<PhoneRecord> findByName(String name) {
		return phonebookService.findByName(name);
	}

	@PostMapping("")
	@Operation(summary = "Create (or update) phone record", description = "Create a new phone record")
	public PhoneRecord add(@RequestBody PhoneRecord phoneRecord) {
		return phonebookService.createPhoneRecord(phoneRecord);
	}

	@PutMapping("")
	@Operation(summary = "Update phone record", description = "Update a phone record")
	public PhoneRecord update(@RequestBody PhoneRecord phoneRecord) {
		return phonebookService.createPhoneRecord(phoneRecord);
	}

	@GetMapping("")
	@Operation(summary = "Find phone records with filters", description = "Find all records that match the filters")
	public List<PhoneRecord> filter(@RequestParam(required = false) UUID contactId, @RequestParam(required = false) String name, @RequestParam(required = false) String countryCode, @RequestParam(required = false) String phoneNumber ) {
		return phonebookService.findAllFiltered(contactId, name, countryCode, phoneNumber);
	}

}
