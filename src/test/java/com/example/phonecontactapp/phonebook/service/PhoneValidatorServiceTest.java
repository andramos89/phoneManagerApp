package com.example.phonecontactapp.phonebook.service;

import com.example.phonecontactapp.phonebook.models.NumVerifyResponseTO;
import com.example.phonecontactapp.phonebook.models.exceptions.InvalidPhoneNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhoneValidatorServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private PhoneValidatorService phoneValidatorService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testIsValidPhoneNumber_validNumber() throws InvalidPhoneNumberException {
		String phoneNumber = "1234567890";
		String countryCode = "US";
		NumVerifyResponseTO mockResponse = new NumVerifyResponseTO();
		mockResponse.setValid(true);

		when(restTemplate.getForObject(anyString(), eq(NumVerifyResponseTO.class))).thenReturn(mockResponse);

		boolean result = phoneValidatorService.isValidPhoneNumber(phoneNumber, countryCode);

		assertTrue(result);
		verify(restTemplate).getForObject(anyString(), eq(NumVerifyResponseTO.class));
	}

	@Test
	void testIsValidPhoneNumber_invalidNumber() throws InvalidPhoneNumberException {
		String phoneNumber = "11111111555";
		String countryCode = "US";
		NumVerifyResponseTO mockResponse = new NumVerifyResponseTO();
		mockResponse.setValid(false);

		when(restTemplate.getForObject(anyString(), eq(NumVerifyResponseTO.class))).thenReturn(mockResponse);

		assertThrows(InvalidPhoneNumberException.class, () -> phoneValidatorService.isValidPhoneNumber(phoneNumber, countryCode));

		verify(restTemplate).getForObject(anyString(), eq(NumVerifyResponseTO.class));
	}

	@Test
	void testValidatePhoneNumber_validResponse() throws InvalidPhoneNumberException {
		String phoneNumber = "1234567890";
		String countryCode = "US";
		NumVerifyResponseTO mockResponse = new NumVerifyResponseTO();
		mockResponse.setValid(true);

		when(restTemplate.getForObject(anyString(), eq(NumVerifyResponseTO.class))).thenReturn(mockResponse);

		NumVerifyResponseTO result = phoneValidatorService.validatePhoneNumber(phoneNumber, countryCode);

		assertNotNull(result);
		assertTrue(result.isValid());
		verify(restTemplate).getForObject(anyString(), eq(NumVerifyResponseTO.class));
	}

	@Test
	void testValidatePhoneNumber_invalidResponse() {
		String phoneNumber = "1234567890";
		String countryCode = "US";
		NumVerifyResponseTO mockResponse = new NumVerifyResponseTO();
		mockResponse.setValid(false);

		when(restTemplate.getForObject(anyString(), eq(NumVerifyResponseTO.class))).thenReturn(mockResponse);

		InvalidPhoneNumberException exception = assertThrows(InvalidPhoneNumberException.class, () -> {
			phoneValidatorService.validatePhoneNumber(phoneNumber, countryCode);
		});

		assertEquals("Invalid phone number: 1234567890", exception.getMessage());
		verify(restTemplate).getForObject(anyString(), eq(NumVerifyResponseTO.class));
	}

	@Test
	void testValidatePhoneNumber_nullPhoneNumber() {
		String phoneNumber = null;
		String countryCode = "US";

		InvalidPhoneNumberException exception = assertThrows(InvalidPhoneNumberException.class, () -> {
			phoneValidatorService.validatePhoneNumber(phoneNumber, countryCode);
		});

		assertEquals("Phone number is null or empty: null", exception.getMessage());
		verify(restTemplate, never()).getForObject(anyString(), eq(NumVerifyResponseTO.class));
	}
}
