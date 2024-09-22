package com.example.phonecontactapp.phonebook.service.repositories;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhoneRecordRepository extends JpaRepository<PhoneRecord, UUID>, JpaSpecificationExecutor<PhoneRecord> {
	Optional<PhoneRecord> findByPhoneNumberIgnoreCase(String phoneNumber);

	PhoneRecord findByContactId(UUID contactId);

	List<PhoneRecord> findByNameIgnoreCase(String name);

	PhoneRecord findByPhoneNumberAndCountryCodeIgnoreCase(String phoneNumber, String countryCode);

	List<PhoneRecord> findByPhoneNumber(String phoneNumber);

	List<PhoneRecord> findByCountryCodeIgnoreCase(String countryCode);

	List<PhoneRecord> findByNameAndCountryCodeIgnoreCaseAndPhoneNumber(String name, String countryCode, String phoneNumber);

	List<PhoneRecord> findByNameAndCountryCodeIgnoreCase(String name, String countryCode);
}
