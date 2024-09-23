package com.example.phonecontactapp.phonebook.controller;

import com.example.phonecontactapp.PhoneContactAppApplication;
import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import com.example.phonecontactapp.phonebook.service.PhonebookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PhoneContactAppApplication.class)
@AutoConfigureMockMvc
class PhonebookControllerTest {

	private static final String ROOT_URL = "/api/v1/phone/";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PhonebookService phonebookService;

	@InjectMocks
	private PhonebookController phonebookController;

	@Test
	void testFindByContactId() throws Exception {
		UUID contactId = UUID.randomUUID();
		PhoneRecord mockRecord = new PhoneRecord();
		mockRecord.setContactId(contactId);

		when(phonebookService.findByContactId(contactId)).thenReturn(mockRecord);

		mockMvc.perform(get(ROOT_URL + contactId)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.contactId").value(contactId.toString()));

		verify(phonebookService).findByContactId(contactId);
	}

	@Test
	void testFindByCountryCode() throws Exception {
		String countryCode = "US";
		UUID contactId = UUID.randomUUID();

		PhoneRecord phoneRecord = new PhoneRecord();
		phoneRecord.setCountryCode(countryCode);
		phoneRecord.setContactId(contactId);
		phoneRecord.setPhoneNumber("1234567890");
		phoneRecord.setName("John Doe");

		List<PhoneRecord> mockRecords = List.of(phoneRecord);

		when(phonebookService.findAllFiltered(null, null, countryCode, null)).thenReturn(mockRecords);

		mockMvc.perform(get(ROOT_URL)
				.param("countryCode", countryCode))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(1))
			.andExpect(jsonPath("$[0].phoneNumber").value(phoneRecord.getPhoneNumber()))
			.andExpect(jsonPath("$[0].contactId").value(contactId.toString()))
			.andExpect(jsonPath("$[0].countryCode").value(countryCode))
			.andExpect(jsonPath("$[0].name").value(phoneRecord.getName()));

		verify(phonebookService).findAllFiltered(null, null, countryCode, null);
	}
	@Test
	void testFindByPhoneNumber() throws Exception {
		String phoneNumber = "1234567890";
		String countryCode = "US";
		PhoneRecord mockRecord = new PhoneRecord();
		mockRecord.setPhoneNumber(phoneNumber);
		List<PhoneRecord> mockRecords = List.of(mockRecord);

		when(phonebookService.findAllFiltered(null, null, countryCode, phoneNumber)).thenReturn(mockRecords);

		mockMvc.perform(get(ROOT_URL)
				.param("countryCode", countryCode )
				.param("phoneNumber", phoneNumber))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].phoneNumber").value(phoneNumber));

		verify(phonebookService).findAllFiltered(null, null,  countryCode, phoneNumber);
	}

	@Test
	void testFindByName() throws Exception {
		String name = "John Doe";
		PhoneRecord mockRecord1 = new PhoneRecord();
		PhoneRecord mockRecord2 = new PhoneRecord();
		List<PhoneRecord> mockRecords = Arrays.asList(mockRecord1, mockRecord2);

		when(phonebookService.findAllFiltered(null, name, null, null)).thenReturn(mockRecords);

		mockMvc.perform(get(ROOT_URL)
				.param("name", name))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(mockRecords.size()));

		verify(phonebookService).findAllFiltered(null, name, null, null);
	}

	@Test
	void testFindAllFiltered() throws Exception {
		PhoneRecord mockRecord1 = new PhoneRecord();
		PhoneRecord mockRecord2 = new PhoneRecord();
		List<PhoneRecord> mockRecords = Arrays.asList(mockRecord1, mockRecord2);

		when(phonebookService.findAllFiltered(null, null, null, null)).thenReturn(mockRecords);

		mockMvc.perform(get(ROOT_URL))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(mockRecords.size()));

		verify(phonebookService).findAllFiltered(null, null, null, null);
	}

	@Test
	void testAdd() throws Exception {
		PhoneRecord mockRecord = new PhoneRecord();
		mockRecord.setPhoneNumber("1234567890");

		when(phonebookService.createPhoneRecord(any(PhoneRecord.class))).thenReturn(mockRecord);

		mockMvc.perform(post(ROOT_URL)
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

		mockMvc.perform(get(ROOT_URL)
				.param("contactId", contactId.toString())
				.param("name", name)
				.param("countryCode", countryCode)
				.param("phoneNumber", phoneNumber))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(mockRecords.size()));

		verify(phonebookService).findAllFiltered(contactId, name, countryCode, phoneNumber);
	}
}
