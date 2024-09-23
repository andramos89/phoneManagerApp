package com.example.phonecontactapp.phonebook.service;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import com.example.phonecontactapp.phonebook.models.exceptions.InvalidNameException;
import com.example.phonecontactapp.phonebook.models.exceptions.InvalidPhoneNumberException;
import com.example.phonecontactapp.phonebook.models.exceptions.PhoneContactNotFoundException;
import com.example.phonecontactapp.phonebook.service.repositories.PhoneRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhonebookServiceTest {

	@Mock
	private PhoneValidatorService phoneValidatorService;

	@Mock
	private PhoneRecordRepository phoneRecordRepository;

	@InjectMocks
	private PhonebookService phonebookService;

	private PhoneRecord mockPhoneRecord;

	@BeforeEach
	void setUp() {
		mockPhoneRecord = new PhoneRecord();
		mockPhoneRecord.setContactId(UUID.randomUUID());
		mockPhoneRecord.setPhoneNumber("1234567890");
		mockPhoneRecord.setCountryCode("US");
		mockPhoneRecord.setName("John Doe");
	}

	@Test
	void testFindByContactIdSuccess() {
		when(phoneRecordRepository.findByContactId(mockPhoneRecord.getContactId())).thenReturn(mockPhoneRecord);

		PhoneRecord result = phonebookService.findByContactId(mockPhoneRecord.getContactId());

		assertNotNull(result);
		assertEquals(mockPhoneRecord.getContactId(), result.getContactId());
		verify(phoneRecordRepository, times(1)).findByContactId(mockPhoneRecord.getContactId());
	}

	@Test
	void testFindByContactIdNotFound() {
		when(phoneRecordRepository.findByContactId(any(UUID.class))).thenReturn(null);

		UUID contactId = UUID.randomUUID();

		assertThrows(PhoneContactNotFoundException.class, () -> phonebookService.findByContactId(contactId));
		verify(phoneRecordRepository).findByContactId(contactId);
	}

	@Test
	void testFindByPhoneNumberValid() throws InvalidPhoneNumberException {
		when(phoneValidatorService.isValidPhoneNumber(eq(mockPhoneRecord.getPhoneNumber()), eq(mockPhoneRecord.getCountryCode()))).thenReturn(true);
		when(phoneRecordRepository.findByPhoneNumberAndCountryCodeIgnoreCase(eq(mockPhoneRecord.getPhoneNumber()), eq(mockPhoneRecord.getCountryCode()))).thenReturn(mockPhoneRecord);

		PhoneRecord result = phonebookService.findByPhoneNumber(mockPhoneRecord.getPhoneNumber(), mockPhoneRecord.getCountryCode());

		assertNotNull(result);
		assertEquals(mockPhoneRecord.getPhoneNumber(), result.getPhoneNumber());
		verify(phoneValidatorService).isValidPhoneNumber(eq(mockPhoneRecord.getPhoneNumber()), eq(mockPhoneRecord.getCountryCode()));
		verify(phoneRecordRepository).findByPhoneNumberAndCountryCodeIgnoreCase(eq(mockPhoneRecord.getPhoneNumber()), eq(mockPhoneRecord.getCountryCode()));
	}

	@Test
	void testFindByPhoneNumberInvalid() throws InvalidPhoneNumberException {
		when(phoneValidatorService.isValidPhoneNumber(anyString(), anyString())).thenReturn(false);

		assertThrows(InvalidPhoneNumberException.class, () -> phonebookService.findByPhoneNumber("invalidPhone", "US"));

		verify(phoneValidatorService).isValidPhoneNumber(anyString(), anyString());
		verify(phoneRecordRepository, times(0)).findByPhoneNumberIgnoreCase(anyString());
	}


	@Test
	void testFindByNameSuccess() {
		List<PhoneRecord> phoneRecords = new ArrayList<>();
		phoneRecords.add(mockPhoneRecord);

		when(phoneRecordRepository.findByNameIgnoreCase(anyString())).thenReturn(phoneRecords);

		List<PhoneRecord> result = phonebookService.findByName(mockPhoneRecord.getName());

		assertFalse(result.isEmpty());
		assertEquals(mockPhoneRecord.getName(), result.get(0).getName());
		verify(phoneRecordRepository).findByNameIgnoreCase(anyString());
	}

	@Test
	void testFindByNameThrowsInvalidNameException() {
		assertThrows(InvalidNameException.class, () -> phonebookService.findByName(""));

		verify(phoneRecordRepository, never()).findByNameIgnoreCase(anyString());
	}

	@Test
	void testCreatePhoneRecordSuccess() throws InvalidPhoneNumberException {
		when(phoneValidatorService.isValidPhoneNumber(anyString(), anyString())).thenReturn(true);
		when(phoneRecordRepository.save(any(PhoneRecord.class))).thenReturn(mockPhoneRecord);

		PhoneRecord result = phonebookService.createPhoneRecord(mockPhoneRecord);

		assertNotNull(result);
		assertEquals(mockPhoneRecord.getPhoneNumber(), result.getPhoneNumber());
		verify(phoneValidatorService).isValidPhoneNumber(anyString(), anyString());
		verify(phoneRecordRepository).save(any(PhoneRecord.class));
	}

	@Test
	void testCreatePhoneRecordInvalidPhoneNumber() throws InvalidPhoneNumberException {
		when(phoneValidatorService.isValidPhoneNumber(anyString(), anyString())).thenThrow(new InvalidPhoneNumberException("1234567890", "Invalid phone number"));

		assertThrows(InvalidPhoneNumberException.class, () -> phonebookService.createPhoneRecord(mockPhoneRecord));

		verify(phoneValidatorService).isValidPhoneNumber(anyString(), anyString());
		verify(phoneRecordRepository, never()).save(any(PhoneRecord.class));
	}

	@Test
	void testFindAllFilteredByContactId() {
		when(phoneRecordRepository.findByContactId(any(UUID.class))).thenReturn(mockPhoneRecord);

		List<PhoneRecord> result = phonebookService.findAllFiltered(mockPhoneRecord.getContactId(), null, null, null);

		assertEquals(1, result.size());
		assertEquals(mockPhoneRecord.getContactId(), result.get(0).getContactId());
		verify(phoneRecordRepository).findByContactId(any(UUID.class));
	}

	@Test
	void testFindAll() {
		List<PhoneRecord> phoneRecords = List.of(mockPhoneRecord);
		when(phoneRecordRepository.findAll()).thenReturn(phoneRecords);

		List<PhoneRecord> result = phonebookService.findAll();

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		verify(phoneRecordRepository).findAll();
	}
}
