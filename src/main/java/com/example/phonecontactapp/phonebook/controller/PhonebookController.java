package com.example.phonecontactapp.phonebook.controller;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import com.example.phonecontactapp.phonebook.service.PhonebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
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
	public PhoneRecord findByName(String name) {
		return phonebookService.findByName(name);
	}

	@GetMapping("/all")
	public List<PhoneRecord> findAll() {
		return phonebookService.findAll();
	}

	@PostMapping("/new")
	public PhoneRecord add(@RequestBody @Validated PhoneRecord phoneRecord) {
		return phonebookService.createPhoneRecord(phoneRecord);
	}

}
