
-- schools
INSERT INTO isc.school_districts (sd_id, sd_guid, sd_name, sd_contact_email, sd_status, sd_created, sd_updated)
VALUES (0, '00000000-0000-0000-0000-000000000000', 'NONE', '', 'DEACTIVATED', current_timestamp, current_timestamp);

INSERT INTO isc.school_districts (sd_id, sd_guid, sd_name, sd_contact_email, sd_address, sd_city, sd_state, sd_zip_code, sd_country, sd_status, sd_created, sd_updated)
VALUES (NEXTVAL('isc.sd_id_seq'), '3beb8500-1a62-4921-8054-9c5f23287ce1', 'Unified School District', '551', 'South', 'BH', 'CA', '90212', 'US', 'ACTIVATED', current_timestamp, current_timestamp);

INSERT INTO isc.schools (s_id, s_guid, s_name, s_sd_id, s_contact_email, s_status, s_created, s_updated)
VALUES (0, '00000000-0000-0000-0000-000000000000', 'NONE', 0, '', 'DEACTIVATED', current_timestamp, current_timestamp);

INSERT INTO isc.schools (s_id, s_guid, s_name, s_sd_id, s_contact_email, s_address, s_city, s_state, s_zip_code, s_country, s_status, s_created, s_updated)
VALUES (NEXTVAL('isc.s_id_seq'), 'eec6e5c0-2767-487e-b4b6-8e081aa4e44e', 'Elementary School', 1, '222', '605', 'BH', 'CA', '90210', 'US', 'ACTIVATED', current_timestamp, current_timestamp);

-- default region
INSERT INTO isc.regions(r_id, r_guid, r_s_id, r_name, r_type, r_status, r_created, r_updated)
VALUES (0, '00000000-0000-0000-0000-000000000000', 0, 'NONE', 'UNKNOWN', 'DEACTIVATED', current_timestamp, current_timestamp);
