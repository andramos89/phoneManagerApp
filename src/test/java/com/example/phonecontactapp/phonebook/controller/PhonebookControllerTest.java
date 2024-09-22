package com.example.phonecontactapp.phonebook.controller;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import com.example.phonecontactapp.phonebook.service.PhonebookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PhonebookControllerTest {

	private MockMvc mockMvc;

	@Mock
	private PhonebookService phonebookService;

	@InjectMocks
	private PhonebookController phonebookController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(phonebookController).build();
	}

	@Test
	void testFindByContactId() throws Exception {
		UUID contactId = UUID.randomUUID();
		PhoneRecord mockRecord = new PhoneRecord();
		mockRecord.setContactId(contactId);

		when(phonebookService.findByContactId(contactId)).thenReturn(mockRecord);

		mockMvc.perform(get("/phone/findByContactId")
				.param("contactId", contactId.toString()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.contactId").value(contactId.toString()));

		verify(phonebookService, times(1)).findByContactId(contactId);
	}

	@Test
	void testFindByPhoneNumber() throws Exception {
		String phoneNumber = "1234567890";
		String countryCode = "US";
		PhoneRecord mockRecord = new PhoneRecord();
		mockRecord.setPhoneNumber(phoneNumber);

		when(phonebookService.findByPhoneNumber(phoneNumber, countryCode)).thenReturn(mockRecord);

		mockMvc.perform(get("/phone/findByPhoneNumber")
				.param("phoneNumber", phoneNumber)
				.param("countryCode", countryCode))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.phoneNumber").value(phoneNumber));

		verify(phonebookService, times(1)).findByPhoneNumber(phoneNumber, countryCode);
	}

	@Test
	void testFindByName() throws Exception {
		String name = "John Doe";
		PhoneRecord mockRecord1 = new PhoneRecord();
		PhoneRecord mockRecord2 = new PhoneRecord();
		List<PhoneRecord> mockRecords = Arrays.asList(mockRecord1, mockRecord2);

		when(phonebookService.findByName(name)).thenReturn(mockRecords);

		mockMvc.perform(get("/phone/findByName")
				.param("name", name))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(mockRecords.size()));

		verify(phonebookService, times(1)).findByName(name);
	}

	@Test
	void testFindAll() throws Exception {
		PhoneRecord mockRecord1 = new PhoneRecord();
		PhoneRecord mockRecord2 = new PhoneRecord();
		List<PhoneRecord> mockRecords = Arrays.asList(mockRecord1, mockRecord2);

		when(phonebookService.findAll()).thenReturn(mockRecords);

		mockMvc.perform(get("/phone/all"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(mockRecords.size()));

		verify(phonebookService, times(1)).findAll();
	}

	@Test
	void testAdd() throws Exception {
		PhoneRecord mockRecord = new PhoneRecord();
		mockRecord.setPhoneNumber("1234567890");

		when(phonebookService.createPhoneRecord(any(PhoneRecord.class))).thenReturn(mockRecord);

		mockMvc.perform(post("/phone/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(mockRecord)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.phoneNumber").value("1234567890"));

		verify(phonebookService, times(1)).createPhoneRecord(any(PhoneRecord.class));
	}

	@Test
	void testFilter() throws Exception {
		UUID contactId = UUID.randomUUID();
		String name = "John Doe";
		String countryCode = "US";
		String phoneNumber = "1234567890";

		PhoneRecord mockRecord1 = new PhoneRecord();
		PhoneRecord mockRecord2 = new PhoneRecord();
		List<PhoneRecord> mockRecords = Arrays.asList(mockRecord1, mockRecord2);

		when(phonebookService.findAllFiltered(contactId, name, countryCode, phoneNumber)).thenReturn(mockRecords);

		mockMvc.perform(get("/phone")
				.param("contactId", contactId.toString())
				.param("name", name)
				.param("countryCode", countryCode)
				.param("phoneNumber", phoneNumber))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(mockRecords.size()));

		verify(phonebookService, times(1)).findAllFiltered(contactId, name, countryCode, phoneNumber);
	}
}
