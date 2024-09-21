package com.example.phonecontactapp.phonebook.controller;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import com.example.phonecontactapp.phonebook.service.PhonebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/phone")
public class PhonebookController {

	@Autowired
	PhonebookService phonebookService;

	@RequestMapping("/findByContactId")
	public PhoneRecord findByContactId(String contactId) {
		return phonebookService.findByContactId(contactId);
	}

	@RequestMapping("/findByPhoneNumber")
	public PhoneRecord findByPhoneNumber(String phoneNumber) {
		return phonebookService.findByPhoneNumber(phoneNumber);
	}

	@RequestMapping("/findByName")
	public PhoneRecord findByName(String name) {
		return phonebookService.findByName(name);
	}

}
