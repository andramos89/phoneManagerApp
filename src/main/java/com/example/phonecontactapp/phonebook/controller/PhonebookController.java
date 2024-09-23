package com.example.phonecontactapp.phonebook.controller;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import com.example.phonecontactapp.phonebook.service.PhonebookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/phone")
@Tag(name = "Phonebook Controller", description = "API for managing phone records")
public class PhonebookController {

	@Autowired
	PhonebookService phonebookService;

	@GetMapping("/{id}")
	@Operation(summary = "Find phone record by contact id", description = "Retrieve a phone record using the contact id")
	public ResponseEntity<PhoneRecord> findByContactId(@PathVariable("id") UUID contactId) {
		return ResponseEntity.ok(phonebookService.findByContactId(contactId));
	}

	@PostMapping({"","/"})
	@Operation(summary = "Create (or update) phone record", description = "Create a new phone record")
	public ResponseEntity<PhoneRecord> add(@RequestBody PhoneRecord phoneRecord) {
		return ResponseEntity.ok(phonebookService.createPhoneRecord(phoneRecord));
	}

	@PutMapping({"","/"})
	@Operation(summary = "Update phone record", description = "Update a phone record")
	public ResponseEntity<PhoneRecord> update(@RequestBody PhoneRecord phoneRecord) {
		return ResponseEntity.ok(phonebookService.createPhoneRecord(phoneRecord));
	}

	@GetMapping({"","/"})
	@Operation(summary = "Find phone records with filters", description = "Find all records that match the filters")
	public ResponseEntity<List<PhoneRecord>> filter(@RequestParam(required = false) UUID contactId, @RequestParam(required = false) String name, @RequestParam(required = false) String countryCode, @RequestParam(required = false) String phoneNumber ) {
		return ResponseEntity.ok(phonebookService.findAllFiltered(contactId, name, countryCode, phoneNumber));
	}

}
