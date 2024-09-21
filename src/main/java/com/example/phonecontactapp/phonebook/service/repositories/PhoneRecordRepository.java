package com.example.phonecontactapp.phonebook.service.repositories;

import com.example.phonecontactapp.phonebook.models.PhoneRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhoneRecordRepository extends JpaRepository<PhoneRecord, UUID>, JpaSpecificationExecutor<PhoneRecord> {
	Optional<PhoneRecord> findByPhoneNumberIgnoreCase(String phoneNumber);

	PhoneRecord findByContactId(UUID contactId);

	PhoneRecord findByNameIgnoreCase(String name);
}
