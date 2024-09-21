package com.example.phonecontactapp.phonebook.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phone_contact")
public class PhoneRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "contact_id", nullable = false)
	@JdbcTypeCode(SqlTypes.UUID)
	private UUID contactId;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "country_code", nullable = false)
	private String countryCode;
	
	@Column(name = "name", nullable = false)
	private String name;

}
