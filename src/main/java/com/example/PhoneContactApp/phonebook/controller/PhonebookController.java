package com.example.PhoneContactApp.phonebook.controller;

import com.example.PhoneContactApp.phonebook.models.PhoneRecord;
import com.example.PhoneContactApp.phonebook.service.PhonebookService;
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
		return null;
	}

	@RequestMapping("/findByPhoneNumber")
	public PhoneRecord findByPhoneNumber(String phoneNumber) {
		return null;
	}

	@RequestMapping("/findByName")
	public PhoneRecord findByName(String name) {
		return null;
	}

}
