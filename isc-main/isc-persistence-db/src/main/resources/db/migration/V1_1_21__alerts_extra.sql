ALTER TABLE isc.alerts ADD COLUMN a_school_id VARCHAR(40);
ALTER TABLE isc.alerts ADD COLUMN a_district_id VARCHAR(40);
ALTER TABLE isc.alerts ADD COLUMN a_code VARCHAR(100) NOT NULL;
ALTER TABLE isc.alerts ADD COLUMN a_description VARCHAR(1024);
