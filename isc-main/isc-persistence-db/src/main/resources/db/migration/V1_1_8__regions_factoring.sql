-- rename references to conform naming conventions
ALTER TABLE isc.doors_state RENAME COLUMN d_id TO ds_d_id;
ALTER TABLE isc.cameras_state RENAME COLUMN c_id TO cs_c_id;
ALTER TABLE isc.drones_state RENAME COLUMN dr_id TO drs_dr_id;
ALTER TABLE isc.speakers_state RENAME COLUMN sp_id TO sps_sp_id;

-- guid indexes
CREATE UNIQUE INDEX ix_ds_guid ON isc.doors_state (ds_guid);
CREATE UNIQUE INDEX ix_cs_guid ON isc.cameras_state (cs_guid);
CREATE UNIQUE INDEX ix_drs_guid ON isc.drones_state (drs_guid);
CREATE UNIQUE INDEX ix_sps_guid ON isc.speakers_state (sps_guid);

-- drop one-to-one region/schools references
ALTER TABLE isc.regions DROP COLUMN r_sec_r_id;
ALTER TABLE isc.regions DROP COLUMN r_r_id;
ALTER TABLE isc.regions DROP COLUMN r_s_id;
ALTER TABLE isc.doors DROP COLUMN d_r_id;
ALTER TABLE isc.doors DROP COLUMN d_sec_r_id;
ALTER TABLE isc.doors DROP COLUMN d_s_id;
ALTER TABLE isc.cameras DROP COLUMN c_r_id;
ALTER TABLE isc.cameras DROP COLUMN c_s_id;
ALTER TABLE isc.drones DROP COLUMN dr_r_id;
ALTER TABLE isc.drones DROP COLUMN dr_s_id;
ALTER TABLE isc.speakers DROP COLUMN sp_r_id;
ALTER TABLE isc.speakers DROP COLUMN sp_s_id;

-- geo zoom and rotation properties on the region
ALTER TABLE isc.regions ADD COLUMN r_geo_zoom real;
ALTER TABLE isc.regions ADD COLUMN r_geo_rotation real;

-- drop schools/districts geo reference
ALTER TABLE isc.schools DROP COLUMN s_geo_location;
ALTER TABLE isc.schools DROP COLUMN s_geo_zoom;
ALTER TABLE isc.schools DROP COLUMN s_geo_rotation;
ALTER TABLE isc.schools DROP COLUMN s_geo_boundaries;
ALTER TABLE isc.school_districts DROP COLUMN sd_geo_location;
ALTER TABLE isc.school_districts DROP COLUMN sd_geo_zoom;
ALTER TABLE isc.school_districts DROP COLUMN sd_geo_rotation;
ALTER TABLE isc.school_districts DROP COLUMN sd_geo_boundaries;

-- a references to school and school district root regions
ALTER TABLE isc.schools ADD s_r_id BIGINT;
ALTER TABLE isc.school_districts ADD sd_r_id BIGINT;

-- school district foreign key
ALTER TABLE isc.schools ADD FOREIGN KEY (s_sd_id) REFERENCES isc.school_districts (sd_id);

-- multi region joins
CREATE SEQUENCE isc.rrj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.region_region_joins
(
    rrj_id      BIGINT NOT NULL DEFAULT nextval('isc.rrj_id_seq'),

    rrj_r_id_p  BIGINT NOT NULL, -- parent region id
    rrj_r_id_c  BIGINT NOT NULL, -- child region id

    rrj_created TIMESTAMP WITH TIME ZONE,
    rrj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT region_region_joins_pk PRIMARY KEY (rrj_id)
);

CREATE SEQUENCE isc.drj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.door_region_joins
(
    drj_id      BIGINT NOT NULL DEFAULT nextval('isc.drj_id_seq'),

    drj_r_id    BIGINT NOT NULL, -- parent region
    drj_d_id    BIGINT NOT NULL, -- child door

    drj_created TIMESTAMP WITH TIME ZONE,
    drj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT door_region_joins_pk PRIMARY KEY (drj_id)
);

CREATE SEQUENCE isc.crj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.camera_region_joins
(
    crj_id      BIGINT NOT NULL DEFAULT nextval('isc.crj_id_seq'),

    crj_r_id    BIGINT NOT NULL, -- parent region
    crj_c_id    BIGINT NOT NULL, -- child camera

    crj_created TIMESTAMP WITH TIME ZONE,
    crj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT camera_region_joins_pk PRIMARY KEY (crj_id)
);

CREATE SEQUENCE isc.drrj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.drone_region_joins
(
    drrj_id      BIGINT NOT NULL DEFAULT nextval('isc.drrj_id_seq'),

    drrj_r_id    BIGINT NOT NULL, -- parent region
    drrj_dr_id    BIGINT NOT NULL, -- child drone

    drrj_created TIMESTAMP WITH TIME ZONE,
    drrj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT drone_region_joins_pk PRIMARY KEY (drrj_id)
);

CREATE SEQUENCE isc.srj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.speaker_region_joins
(
    srj_id      BIGINT NOT NULL DEFAULT nextval('isc.srj_id_seq'),

    srj_r_id    BIGINT NOT NULL, -- parent region
    srj_sp_id    BIGINT NOT NULL, -- child speaker

    srj_created TIMESTAMP WITH TIME ZONE,
    srj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT speaker_region_joins_pk PRIMARY KEY (srj_id)
);

-- delete duplicated school
DELETE FROM isc.schools WHERE s_name LIKE 'High School';

-- populate regions table and regions associations
INSERT INTO isc.regions (r_id, r_guid, r_name, r_type, r_status, r_created, r_updated)
VALUES (NEXTVAL('isc.r_id_seq'), '32266835-870e-4ed1-96e6-2e67206a57eb', 'Unified School District Region', 'SCHOOL_DISTRICT', 'ACTIVATED', current_timestamp, current_timestamp);

UPDATE isc.school_districts SET sd_r_id = CURRVAL('isc.r_id_seq') WHERE sd_guid = '3beb8500-1a62-4921-8054-9c5f23287ce1';

INSERT INTO isc.region_region_joins (rrj_id, rrj_r_id_p, rrj_r_id_c, rrj_created, rrj_updated) VALUES (NEXTVAL('isc.rrj_id_seq'), 0, CURRVAL('isc.r_id_seq'), current_timestamp, current_timestamp);
---
INSERT INTO isc.regions (r_id, r_guid, r_name, r_type, r_status, r_created, r_updated)
VALUES (NEXTVAL('isc.r_id_seq'), '8c8d6b49-33a8-4da4-b9b6-7b146453bf3d', 'High School Region', 'SCHOOL', 'ACTIVATED', current_timestamp, current_timestamp);

UPDATE isc.schools SET s_r_id = CURRVAL('isc.r_id_seq') WHERE s_guid = '2545c9bb-33d9-4586-8332-8957bec6d7e9';

INSERT INTO isc.region_region_joins (rrj_id, rrj_r_id_p, rrj_r_id_c, rrj_created, rrj_updated) VALUES (NEXTVAL('isc.rrj_id_seq'), 1, CURRVAL('isc.r_id_seq'), current_timestamp, current_timestamp);
---
-- joins updates to conform naming conventions
ALTER TABLE isc.user_roles_joins RENAME TO user_role_joins;
ALTER TABLE isc.integration_indexes_joins RENAME TO integration_index_joins;
ALTER TABLE isc.school_district_indexes_joins RENAME TO school_district_index_joins;
ALTER TABLE isc.school_indexes_joins RENAME TO school_index_joins;