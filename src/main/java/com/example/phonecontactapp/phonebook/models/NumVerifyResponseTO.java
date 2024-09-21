package com.example.phonecontactapp.phonebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NumVerifyResponseTO {

	private boolean valid;

	private String number;

	@JsonProperty("local_format")
	private String localFormat;

	@JsonProperty("international_format")
	private String internationalFormat;

	@JsonProperty("country_prefix")
	private String countryPrefix;

	@JsonProperty("country_code")
	private String countryCode;

	@JsonProperty("country_name")
	private String countryName;

	private String location;

	private String carrier;

	@JsonProperty("line_type")
	private String lineType;


}
