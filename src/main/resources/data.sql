CREATE TABLE IF NOT EXISTS phone_contact (
                                             contact_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                                             phone_number VARCHAR(20) NOT NULL,
                                             country_code VARCHAR(3) NOT NULL,
                                             name VARCHAR(100) NOT NULL
);