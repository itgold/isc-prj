-- Create Categories
TRUNCATE TABLE isc.users CASCADE;
TRUNCATE TABLE isc.roles CASCADE;
TRUNCATE TABLE isc.schools CASCADE;
TRUNCATE TABLE isc.school_districts CASCADE;
TRUNCATE TABLE isc.region_region_joins CASCADE;
TRUNCATE TABLE isc.regions CASCADE;

ALTER SEQUENCE isc.u_id_seq RESTART WITH 1;
ALTER SEQUENCE isc.ro_id_seq RESTART WITH 1;
ALTER SEQUENCE isc.urj_id_seq RESTART WITH 1;
ALTER SEQUENCE isc.r_id_seq RESTART WITH 1;
ALTER SEQUENCE isc.s_id_seq RESTART WITH 1;
ALTER SEQUENCE isc.sd_id_seq RESTART WITH 1;

-- users
INSERT INTO isc.roles (ro_id, ro_name, ro_created, ro_updated) VALUES (NEXTVAL('isc.ro_id_seq'), 'ROLE_SYSTEM_ADMINISTRATOR', current_timestamp, current_timestamp);
INSERT INTO isc.roles (ro_id, ro_name, ro_created, ro_updated) VALUES (NEXTVAL('isc.ro_id_seq'), 'ROLE_DISTRICT_ADMINISTRATOR', current_timestamp, current_timestamp);
INSERT INTO isc.roles (ro_id, ro_name, ro_created, ro_updated) VALUES (NEXTVAL('isc.ro_id_seq'), 'ROLE_ANALYST', current_timestamp, current_timestamp);
INSERT INTO isc.roles (ro_id, ro_name, ro_created, ro_updated) VALUES (NEXTVAL('isc.ro_id_seq'), 'ROLE_GUARD', current_timestamp, current_timestamp);
INSERT INTO isc.roles (ro_id, ro_name, ro_created, ro_updated) VALUES (NEXTVAL('isc.ro_id_seq'), 'ROLE_GUEST', current_timestamp, current_timestamp);

INSERT INTO isc.users (u_id, u_email, u_password, u_status, u_status_date, u_activation_date, u_created, u_updated)
VALUES (NEXTVAL('isc.u_id_seq'), 'admin@usd.io', 'pass', 'ACTIVATED', current_timestamp, current_timestamp, current_timestamp, current_timestamp);

INSERT INTO isc.users (u_id, u_email, u_password, u_status, u_status_date, u_activation_date, u_created, u_updated)
VALUES (NEXTVAL('isc.u_id_seq'), 'district-admin@usd.io', 'pass', 'ACTIVATED', current_timestamp, current_timestamp, current_timestamp, current_timestamp);

INSERT INTO isc.users (u_id, u_email, u_password, u_status, u_status_date, u_activation_date, u_created, u_updated)
VALUES (NEXTVAL('isc.u_id_seq'), 'system-analyst@usd.io', 'pass', 'ACTIVATED', current_timestamp, current_timestamp, current_timestamp, current_timestamp);

INSERT INTO isc.users (u_id, u_email, u_password, u_status, u_status_date, u_activation_date, u_created, u_updated)
VALUES (NEXTVAL('isc.u_id_seq'), 'system-guard@usd.io', 'pass', 'ACTIVATED', current_timestamp, current_timestamp, current_timestamp, current_timestamp);

INSERT INTO isc.users (u_id, u_email, u_password, u_status, u_status_date, u_activation_date, u_created, u_updated)
VALUES (NEXTVAL('isc.u_id_seq'), 'system-guest@usd.io', 'pass', 'ACTIVATED', current_timestamp, current_timestamp, current_timestamp, current_timestamp);

INSERT INTO isc.user_role_joins (urj_id, urj_u_id, urj_ro_id, urj_created, urj_updated)
VALUES (NEXTVAL('isc.urj_id_seq'), 1, 1, current_timestamp, current_timestamp);

INSERT INTO isc.user_role_joins (urj_id, urj_u_id, urj_ro_id, urj_created, urj_updated)
VALUES (NEXTVAL('isc.urj_id_seq'), 2, 2, current_timestamp, current_timestamp);

INSERT INTO isc.user_role_joins (urj_id, urj_u_id, urj_ro_id, urj_created, urj_updated)
VALUES (NEXTVAL('isc.urj_id_seq'), 3, 3, current_timestamp, current_timestamp);

INSERT INTO isc.user_role_joins (urj_id, urj_u_id, urj_ro_id, urj_created, urj_updated)
VALUES (NEXTVAL('isc.urj_id_seq'), 4, 4, current_timestamp, current_timestamp);

INSERT INTO isc.user_role_joins (urj_id, urj_u_id, urj_ro_id, urj_created, urj_updated)
VALUES (NEXTVAL('isc.urj_id_seq'), 5, 5, current_timestamp, current_timestamp);

-- default region
INSERT INTO isc.regions(r_id, r_guid, r_name, r_type, r_status, r_created, r_updated)
VALUES (0, '00000000-0000-0000-0000-000000000000', 'ROOT', 'ROOT', 'DEACTIVATED', current_timestamp, current_timestamp);

-- school district
INSERT INTO isc.school_districts (sd_id, sd_guid, sd_name, sd_r_id, sd_contact_email, sd_status, sd_created, sd_updated)
VALUES (0, '00000000-0000-0000-0000-000000000000', 'NONE', 0, '', 'DEACTIVATED', current_timestamp, current_timestamp);

INSERT INTO isc.regions (r_id, r_guid, r_name, r_type, r_status, r_created, r_updated)
VALUES (NEXTVAL('isc.r_id_seq'), '32266835-870e-4ed1-96e6-2e67206a57eb', 'Unified School District Region', 'SCHOOL_DISTRICT', 'ACTIVATED', current_timestamp, current_timestamp);

INSERT INTO isc.school_districts (sd_id, sd_guid, sd_name, sd_r_id, sd_contact_email, sd_address, sd_city, sd_state, sd_zip_code, sd_country, sd_status, sd_created, sd_updated)
VALUES (NEXTVAL('isc.sd_id_seq'), '3beb8500-1a62-4921-8054-9c5f23287ce1', 'Unified School District', CURRVAL('isc.r_id_seq'), '(310)', '255', 'BH', 'CA', '90212', 'US', 'ACTIVATED', current_timestamp, current_timestamp);

INSERT INTO isc.region_region_joins (rrj_id, rrj_r_id_p, rrj_r_id_c, rrj_created, rrj_updated) VALUES (NEXTVAL('isc.rrj_id_seq'), 0, CURRVAL('isc.r_id_seq'), current_timestamp, current_timestamp);

-- schools
INSERT INTO isc.schools (s_id, s_guid, s_name, s_sd_id, s_r_id, s_contact_email, s_status, s_created, s_updated)
VALUES (0, '00000000-0000-0000-0000-000000000000', 'NONE', 0, null, '', 'DEACTIVATED', current_timestamp, current_timestamp);
---
INSERT INTO isc.regions (r_id, r_guid, r_name, r_type, r_status, r_created, r_updated)
VALUES (NEXTVAL('isc.r_id_seq'), '8c8d6b49-33a8-4da4-b9b6-7b146453bf3d', 'High School Region', 'SCHOOL', 'ACTIVATED', current_timestamp, current_timestamp);

INSERT INTO isc.schools (s_id, s_guid, s_name, s_sd_id, s_r_id, s_contact_email, s_address, s_city, s_state, s_zip_code, s_country, s_status, s_created, s_updated)
VALUES (NEXTVAL('isc.s_id_seq'), '2545c9bb-33d9-4586-8332-8957bec6d7e9', 'High School', CURRVAL('isc.sd_id_seq'), CURRVAL('isc.r_id_seq'), 'iscsuperdevelopment@isc.io', '241', 'BH', 'CA', '90211', 'US', 'ACTIVATED', current_timestamp, current_timestamp);

INSERT INTO isc.region_region_joins (rrj_id, rrj_r_id_p, rrj_r_id_c, rrj_created, rrj_updated) VALUES (NEXTVAL('isc.rrj_id_seq'), 1, CURRVAL('isc.r_id_seq'), current_timestamp, current_timestamp);
---
