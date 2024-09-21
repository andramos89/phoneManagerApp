package com.example.phonecontactapp.phonebook.service;

import com.example.phonecontactapp.phonebook.models.NumVerifyResponseTO;
import com.example.phonecontactapp.phonebook.models.exceptions.InvalidPhoneNumberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class PhoneValidatorService {

	private static final String API_URL = "https://apilayer.net/api/validate";
	@Value("${numverifi.api.key}")
	private static String API_KEY;

	private final RestTemplate restTemplate;

	public PhoneValidatorService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public boolean isValidPhoneNumber(String phoneNumber) {
		return validatePhoneNumber(phoneNumber).isValid();
	}

	private NumVerifyResponseTO validatePhoneNumber(String phoneNumber) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL)
			.queryParam("access_key", API_KEY)
			.queryParam("number", phoneNumber);

		// Make the API request and map the response to NumVerifyResponseTO
		NumVerifyResponseTO response = restTemplate.getForObject(builder.toUriString(), NumVerifyResponseTO.class);
		try {

			// Check if the API response contains valid data
			if (response != null && response.isValid()) {
				return response;
			} else {

				// Handle case where phone number is invalid
				throw new InvalidPhoneNumberException(phoneNumber, "Invalid phone number");
			}

		} catch (HttpClientErrorException e) {
			handleHttpClientError(e);
		} catch (Exception e) {
			// Log any other unexpected errors
			log.error("An unexpected error occurred while validating phone number: {}", phoneNumber, e);
			throw new InvalidPhoneNumberException(phoneNumber, "An expected error occurred while validating phone number");
		}

		return response;
	}

	private void handleHttpClientError(HttpClientErrorException e) {
		int errorStatusCode = e.getStatusCode().value();
		String responseBody = e.getResponseBodyAsString();

			/*
			Response errors to pick up here:
			403	"missing_access_key"	User did not supply an Access Key.
			403	"invalid_access_key"	User entered an invalid Access Key.
			404	"404_not_found"	User requested a resource which does not exist.
			404	"invalid_api_function"	User requested a non-existent API function.
			*/

		switch (errorStatusCode) {
			case 403:
				if (responseBody.contains("missing_access_key")) {
					log.error("Error 403: Missing access key. Please provide a valid API key.");
				} else if (responseBody.contains("invalid_access_key")) {
					log.error("Error 403: Invalid access key. The provided API key is incorrect.");
				}
				break;

			case 404:
				if (responseBody.contains("404_not_found")) {
					log.error("Error 404: Resource not found. The requested resource does not exist.");
				} else if (responseBody.contains("invalid_api_function")) {
					log.error("Error 404: Invalid API function. The requested function does not exist.");
				}
				break;

			default:
				log.error("Unhandled HTTP error: {} - Response: {}", errorStatusCode, responseBody);
				break;
		}
	}



}
