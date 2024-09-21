package com.example.PhoneContactApp.phonebook.models;

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
public class PhoneRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "contact_id", nullable = false)
	@JdbcTypeCode(SqlTypes.UUID)
	private UUID contactId;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	
	@Column(name = "name", nullable = false)
	private String name;

}
