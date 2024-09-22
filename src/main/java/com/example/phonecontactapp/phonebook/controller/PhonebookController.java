package com.example.phonecontactapp.phonebook.controller;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import com.example.phonecontactapp.phonebook.service.PhonebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/phone")
public class PhonebookController {

	@Autowired
	PhonebookService phonebookService;

	@GetMapping("/findByContactId")
	public PhoneRecord findByContactId(UUID contactId) {
		return phonebookService.findByContactId(contactId);
	}

	@GetMapping("/findByPhoneNumber")
	public PhoneRecord findByPhoneNumber(String phoneNumber, String countryCode) {
		return phonebookService.findByPhoneNumber(phoneNumber, countryCode);
	}

	@GetMapping("/findByName")
	public List<PhoneRecord> findByName(String name) {
		return phonebookService.findByName(name);
	}

	@GetMapping("/all")
	public List<PhoneRecord> findAll() {
		//Maybe paginate this?
		return phonebookService.findAll();
	}

	@PostMapping("/new")
	public PhoneRecord add(@RequestBody PhoneRecord phoneRecord) {
		return phonebookService.createPhoneRecord(phoneRecord);
	}


	@GetMapping("")
	public List<PhoneRecord> filter(@RequestParam(required = false) UUID contactId, @RequestParam(required = false) String name, @RequestParam(required = false) String countryCode, @RequestParam(required = false) String phoneNumber ) {
		return phonebookService.findAllFiltered(contactId, name, countryCode, phoneNumber);
	}



}
