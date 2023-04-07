-- noinspection SqlResolveForFile
DROP SCHEMA IF EXISTS isc CASCADE;
CREATE SCHEMA isc;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ====================== School districts ===============================
CREATE SEQUENCE isc.sd_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.school_districts
(
    sd_id            BIGINT       NOT NULL DEFAULT nextval('isc.sd_id_seq'),
    sd_guid          VARCHAR      NOT NULL DEFAULT uuid_generate_v4(),
    sd_name          VARCHAR(500) NOT NULL,
    sd_r_id          BIGINT, -- School district root region

    sd_contact_email VARCHAR(500) NOT NULL,
    sd_address       VARCHAR(500),
    sd_city          VARCHAR(200),
    sd_state         VARCHAR(200),
    sd_zip_code      VARCHAR(50),
    sd_country       VARCHAR(200),
    sd_status        VARCHAR(200),

    sd_created       TIMESTAMP WITH TIME ZONE,
    sd_updated       TIMESTAMP WITH TIME ZONE,

    CONSTRAINT school_districts_pk PRIMARY KEY (sd_id)
);

CREATE UNIQUE INDEX ix_sd_guid ON isc.school_districts (sd_guid);
CREATE UNIQUE INDEX ix_sd_name ON isc.school_districts (sd_name);

-- ====================== Schools ===============================

CREATE SEQUENCE isc.s_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.schools
(
    s_id            BIGINT       NOT NULL DEFAULT nextval('isc.s_id_seq'),
    s_guid          VARCHAR      NOT NULL DEFAULT uuid_generate_v4(),
    s_name          VARCHAR(500) NOT NULL,
    s_sd_id         BIGINT, -- School district
    s_r_id          BIGINT, -- School root region

    s_contact_email VARCHAR(500) NOT NULL,
    s_address       VARCHAR(500),
    s_city          VARCHAR(200),
    s_state         VARCHAR(200),
    s_zip_code      VARCHAR(50),
    s_country       VARCHAR(200),
    s_status        VARCHAR(200),

    s_created       TIMESTAMP WITH TIME ZONE,
    s_updated       TIMESTAMP WITH TIME ZONE,

    CONSTRAINT schools_pk PRIMARY KEY (s_id),
    FOREIGN KEY (s_sd_id) REFERENCES isc.school_districts (sd_id)
    -- Add constraint to user table after the user table exists
);

CREATE UNIQUE INDEX ix_s_guid ON isc.schools (s_guid);
CREATE UNIQUE INDEX ix_s_name ON isc.schools (s_name);

-- ====================== Users ===============================

CREATE SEQUENCE isc.u_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.users
(
    u_id              BIGINT       NOT NULL DEFAULT nextval('isc.u_id_seq'),
    u_guid            VARCHAR      NOT NULL DEFAULT uuid_generate_v4(),
    u_sd_id           BIGINT, -- User's school district

    u_email           VARCHAR(300) NOT NULL,
    u_password        VARCHAR(300) NOT NULL,
    u_first_name      VARCHAR(100),
    u_last_name       VARCHAR(100),
    u_image_url       VARCHAR,
    u_preferences     TEXT,

    u_status          VARCHAR(50)  NOT NULL,
    u_status_date     TIMESTAMP WITH TIME ZONE,
    u_last_login      TIMESTAMP WITH TIME ZONE,
    u_activation_date TIMESTAMP WITH TIME ZONE,

    u_created         TIMESTAMP WITH TIME ZONE,
    u_updated         TIMESTAMP WITH TIME ZONE,

    CONSTRAINT users_pk PRIMARY KEY (u_id),
    FOREIGN KEY (u_sd_id) REFERENCES isc.school_districts (sd_id)
);

CREATE UNIQUE INDEX ix_u_guid ON isc.users (u_guid);
CREATE UNIQUE INDEX ix_u_email ON isc.users (u_email);

-- ====================== Roles ===============================

CREATE SEQUENCE isc.ro_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.roles
(
    ro_id      BIGINT  NOT NULL DEFAULT nextval('isc.ro_id_seq'),
    ro_guid    VARCHAR NOT NULL DEFAULT uuid_generate_v4(),
    ro_name    VARCHAR NOT NULL,

    ro_created TIMESTAMP WITH TIME ZONE,
    ro_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT roles_pk PRIMARY KEY (ro_id)
);

CREATE UNIQUE INDEX ix_ro_name ON isc.roles (ro_name);

-- ====================== User Role Joins ==============================

CREATE SEQUENCE isc.urj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.user_role_joins
(
    urj_id      BIGINT NOT NULL DEFAULT nextval('isc.urj_id_seq'),

    urj_u_id    BIGINT NOT NULL,
    urj_ro_id   BIGINT NOT NULL,

    urj_created TIMESTAMP WITH TIME ZONE,
    urj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT user_role_joins_pk PRIMARY KEY (urj_id),
    CONSTRAINT user_role_joins_uk UNIQUE (urj_u_id, urj_ro_id),
    FOREIGN KEY (urj_u_id) REFERENCES isc.users (u_id),
    FOREIGN KEY (urj_ro_id) REFERENCES isc.roles (ro_id)
);

-- ====================== Regions ======================

CREATE SEQUENCE isc.r_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.regions
(
    r_id             BIGINT                   NOT NULL DEFAULT nextval('isc.r_id_seq'),
    r_guid           VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),

    r_name           VARCHAR(500),
    r_description    VARCHAR(500),
    r_type           VARCHAR(50)              NOT NULL,
    r_subtype        VARCHAR(50),
    r_status         VARCHAR(50)              NOT NULL,

    r_geo_location   GEOMETRY(POINT, 4326),
    r_geo_boundaries GEOMETRY(POLYGON, 4326),
    r_geo_zoom       real,
    r_geo_rotation   real,

    r_created        TIMESTAMP WITH TIME ZONE NOT NULL,
    r_updated        TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT regions_pk PRIMARY KEY (r_id)
);

CREATE UNIQUE INDEX ix_r_guid ON isc.regions (r_guid);

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

-- ====================== Doors ===============================

CREATE SEQUENCE isc.d_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.doors
(
    d_id             BIGINT                   NOT NULL DEFAULT nextval('isc.d_id_seq'),
    d_guid           VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),

    d_external_id    VARCHAR(500),
    d_status         VARCHAR(50)              NOT NULL,
    d_type           VARCHAR(50)              NOT NULL,
    d_name           VARCHAR(250),
    d_description    VARCHAR(500),

    d_geo_location   GEOMETRY(POINT, 4326),

    d_created        TIMESTAMP WITH TIME ZONE NOT NULL,
    d_updated        TIMESTAMP WITH TIME ZONE NOT NULL,
    d_last_sync_time TIMESTAMP WITH TIME ZONE,

    d_conn_status    VARCHAR(50),
    d_online_status  VARCHAR(50),
    d_battery_status VARCHAR(50),
    d_tamper_status  VARCHAR(50),
    d_opening_mode   VARCHAR(50),
    d_battery_level  INTEGER,
    d_require_update BOOLEAN,

    CONSTRAINT doors_pk PRIMARY KEY (d_id),
    CONSTRAINT doors_external_id unique (d_external_id)
);

CREATE UNIQUE INDEX ix_d_guid ON isc.doors (d_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_d_external_id ON isc.doors (d_external_id);

CREATE SEQUENCE IF NOT EXISTS isc.ds_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.doors_state
(
    ds_id           BIGINT  NOT NULL DEFAULT nextval('isc.ds_id_seq'),
    ds_door_id      BIGINT,
    ds_name         VARCHAR NOT NULL,
    ds_value        VARCHAR,
    ds_updated      TIMESTAMP WITH TIME ZONE
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

-- ====================== Cameras ===============================

CREATE SEQUENCE isc.c_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.cameras
(
    c_id           BIGINT                   NOT NULL DEFAULT nextval('isc.c_id_seq'),
    c_guid         VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),

    c_external_id  VARCHAR(500),
    c_status       VARCHAR(50)              NOT NULL,
    c_type         VARCHAR(50)              NOT NULL,
    c_name         VARCHAR(250),
    c_description  VARCHAR(2048),

    c_geo_location GEOMETRY(POINT, 4326),

    c_created      TIMESTAMP WITH TIME ZONE NOT NULL,
    c_updated      TIMESTAMP WITH TIME ZONE NOT NULL,
    c_last_sync_time TIMESTAMP WITH TIME ZONE,

    CONSTRAINT cameras_pk PRIMARY KEY (c_id),
    CONSTRAINT cameras_external_id unique (c_external_id)
);

CREATE UNIQUE INDEX ix_c_guid ON isc.cameras (c_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_c_external_id ON isc.cameras (c_external_id);

CREATE SEQUENCE IF NOT EXISTS isc.cs_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.cameras_state
(
    cs_id           BIGINT  NOT NULL DEFAULT nextval('isc.cs_id_seq'),
    cs_camera_id    BIGINT,
    cs_name         VARCHAR NOT NULL,
    cs_value        VARCHAR,
    cs_updated      TIMESTAMP WITH TIME ZONE
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

-- ====================== Drones ===============================

CREATE SEQUENCE isc.dr_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.drones
(
    dr_id                BIGINT                   NOT NULL DEFAULT nextval('isc.dr_id_seq'),
    dr_guid              VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),

    dr_external_id       VARCHAR(500),
    dr_status            VARCHAR(50)              NOT NULL,
    dr_type              VARCHAR(50)              NOT NULL,
    dr_name              VARCHAR(250),
    dr_description       VARCHAR(500),

    dr_geo_location      GEOMETRY(POINT, 4326),
    dr_geo_curr_location GEOMETRY(POINT, 4326),

    dr_created           TIMESTAMP WITH TIME ZONE NOT NULL,
    dr_updated           TIMESTAMP WITH TIME ZONE NOT NULL,
    dr_last_sync_time    TIMESTAMP WITH TIME ZONE,

    CONSTRAINT drones_pk PRIMARY KEY (dr_id),
    CONSTRAINT drones_external_id unique (dr_external_id)
);

CREATE UNIQUE INDEX ix_dr_guid ON isc.drones (dr_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_dr_external_id ON isc.drones (dr_external_id);

CREATE SEQUENCE IF NOT EXISTS isc.drs_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.drones_state
(
    drs_id           BIGINT  NOT NULL DEFAULT nextval('isc.drs_id_seq'),
    drs_drone_id      BIGINT,
    drs_name         VARCHAR NOT NULL,
    drs_value        VARCHAR,
    drs_updated      TIMESTAMP WITH TIME ZONE
);

CREATE SEQUENCE isc.drrj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.drone_region_joins
(
    drrj_id      BIGINT NOT NULL DEFAULT nextval('isc.drrj_id_seq'),

    drrj_r_id    BIGINT NOT NULL, -- parent region
    drrj_dr_id   BIGINT NOT NULL, -- child drone

    drrj_created TIMESTAMP WITH TIME ZONE,
    drrj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT drone_region_joins_pk PRIMARY KEY (drrj_id)
);

-- ====================== Speakers ===============================

CREATE SEQUENCE isc.sp_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.speakers
(
    sp_id           BIGINT                   NOT NULL DEFAULT nextval('isc.sp_id_seq'),
    sp_guid         VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),

    sp_external_id  VARCHAR(500),
    sp_status       VARCHAR(50)              NOT NULL,
    sp_type         VARCHAR(50)              NOT NULL,
    sp_name         VARCHAR(250),
    sp_description  VARCHAR(2048),

    sp_geo_location GEOMETRY(POINT, 4326),

    sp_created      TIMESTAMP WITH TIME ZONE NOT NULL,
    sp_updated      TIMESTAMP WITH TIME ZONE NOT NULL,
    sp_last_sync_time TIMESTAMP WITH TIME ZONE,

    CONSTRAINT speakers_pk PRIMARY KEY (sp_id),
    CONSTRAINT speakers_external_id unique (sp_external_id)
);

CREATE UNIQUE INDEX ix_sp_guid ON isc.speakers (sp_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_sp_external_id ON isc.speakers (sp_external_id);

CREATE SEQUENCE IF NOT EXISTS isc.sps_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.speakers_state
(
    sps_id           BIGINT  NOT NULL DEFAULT nextval('isc.sps_id_seq'),
    sps_speaker_id   BIGINT,
    sps_name         VARCHAR NOT NULL,
    sps_value        VARCHAR,
    sps_updated      TIMESTAMP WITH TIME ZONE
);

CREATE SEQUENCE isc.srj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.speaker_region_joins
(
    srj_id      BIGINT NOT NULL DEFAULT nextval('isc.srj_id_seq'),

    srj_r_id    BIGINT NOT NULL, -- parent region
    srj_sp_id   BIGINT NOT NULL, -- child speaker

    srj_created TIMESTAMP WITH TIME ZONE,
    srj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT speaker_region_joins_pk PRIMARY KEY (srj_id)
);

-- ====================== Tags ===============================

CREATE SEQUENCE isc.t_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.tags
(
    t_id      BIGINT                   NOT NULL DEFAULT nextval('isc.t_id_seq'),
    t_guid    VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    t_name    VARCHAR(100)             NOT NULL,

    t_created TIMESTAMP WITH TIME ZONE NOT NULL,
    t_updated TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT tags_pk PRIMARY KEY (t_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS ix_t_name ON isc.tags (t_name);

CREATE SEQUENCE isc.tej_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.tag_entity_joins
(
    tej_id          BIGINT                   NOT NULL DEFAULT nextval('isc.tej_id_seq'),
    tej_guid        VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    tej_t_id        BIGINT                   NOT NULL,
    tej_entity_id   BIGINT                   NOT NULL,

    tej_entity_type VARCHAR(100)             NOT NULL,

    tej_created     TIMESTAMP WITH TIME ZONE NOT NULL,
    tej_updated     TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT tag_entity_joins_pk PRIMARY KEY (tej_id)
);

-- ====================== Alerts ===============================

CREATE SEQUENCE isc.a_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.alerts
(
    a_id            BIGINT                      NOT NULL DEFAULT nextval('isc.a_id_seq'),
    a_guid          VARCHAR                     NOT NULL DEFAULT uuid_generate_v4(),
    a_severity      VARCHAR(100)                NOT NULL,

    a_device_guid   VARCHAR(40),
    a_device_type   VARCHAR(100)                NOT NULL,
    a_count         INTEGER                     NOT NULL,
    a_status        VARCHAR(100)                NOT NULL,
    a_event_id      VARCHAR(40),
    a_at_guid       VARCHAR(40)                 NOT NULL,
    a_school_id     VARCHAR(40),
    a_district_id   VARCHAR(40),
    a_code          VARCHAR(100)                 NOT NULL,
    a_description   VARCHAR(1024),

    a_created  TIMESTAMP WITH TIME ZONE NOT NULL,
    a_updated  TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT alerts_pk PRIMARY KEY (a_id)
);
CREATE UNIQUE INDEX IF NOT EXISTS ix_alerts ON isc.alerts (a_at_guid, a_device_guid, a_device_type);

CREATE SEQUENCE isc.at_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.alert_triggers
(
    at_id       BIGINT                      NOT NULL DEFAULT nextval('isc.at_id_seq'),
    at_guid     VARCHAR                     NOT NULL DEFAULT uuid_generate_v4(),
    at_name     VARCHAR(100)                NOT NULL,
    at_active   BOOLEAN                     NOT NULL,
    at_processor_type VARCHAR(100)                NOT NULL,

    at_created  TIMESTAMP WITH TIME ZONE    NOT NULL,
    at_updated  TIMESTAMP WITH TIME ZONE    NOT NULL,

    CONSTRAINT alert_triggers_pk PRIMARY KEY (at_id)
);

-- Alert config matcher is simplified Set of string and no need to define them as a full entities
CREATE TABLE IF NOT EXISTS isc.alert_trigger_matchers
(
    atm_at_id           BIGINT                      NOT NULL,
    atm_body            VARCHAR(1024)               NOT NULL,
    atm_type            VARCHAR(50)                 NOT NULL,
    atm_created         TIMESTAMP WITH TIME ZONE    NOT NULL,
    atm_updated         TIMESTAMP WITH TIME ZONE    NOT NULL
);

-- ====================== Indexes ===============================

CREATE SEQUENCE isc.i_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.indexes
(
    i_id          BIGINT                   NOT NULL DEFAULT nextval('isc.i_id_seq'),
    i_guid        VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),

    i_name        VARCHAR(250)             NOT NULL,
    i_description VARCHAR(500),
    i_status      VARCHAR(50)              NOT NULL,

    i_created     TIMESTAMP WITH TIME ZONE NOT NULL,
    i_updated     TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT indexes_pk PRIMARY KEY (i_id)
);

CREATE UNIQUE INDEX ix_i_guid ON isc.indexes (i_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_i_name ON isc.indexes (i_name);

-- =================== School District Index Joins ======================

CREATE SEQUENCE isc.sdij_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.school_district_index_joins
(
    sdij_id      BIGINT  NOT NULL DEFAULT nextval('isc.sdij_id_seq'),
    sdij_guid    VARCHAR NOT NULL DEFAULT uuid_generate_v4(),
    sdij_sd_id   BIGINT  NOT NULL,
    sdij_i_id    BIGINT  NOT NULL,

    sdij_created TIMESTAMP WITH TIME ZONE,
    sdij_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT school_district_index_joins_pk PRIMARY KEY (sdij_id),
    CONSTRAINT school_district_index_joins_uk UNIQUE (sdij_sd_id, sdij_i_id),
    FOREIGN KEY (sdij_sd_id) REFERENCES isc.school_districts (sd_id),
    FOREIGN KEY (sdij_i_id) REFERENCES isc.indexes (i_id)
);

-- =================== School Index Joins ======================

CREATE SEQUENCE isc.sij_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.school_index_joins
(
    sij_id      BIGINT  NOT NULL DEFAULT nextval('isc.sij_id_seq'),
    sij_guid    VARCHAR NOT NULL DEFAULT uuid_generate_v4(),
    sij_s_id    BIGINT  NOT NULL,
    sij_i_id    BIGINT  NOT NULL,

    sij_created TIMESTAMP WITH TIME ZONE,
    sij_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT school_index_joins_pk PRIMARY KEY (sij_id),
    CONSTRAINT school_index_joins_uk UNIQUE (sij_s_id, sij_i_id),
    FOREIGN KEY (sij_s_id) REFERENCES isc.schools (s_id),
    FOREIGN KEY (sij_i_id) REFERENCES isc.indexes (i_id)
);

-- ====================== Integrations ==============================

CREATE SEQUENCE isc.in_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.integrations
(
    in_id          BIGINT                   NOT NULL DEFAULT nextval('isc.in_id_seq'),
    in_guid        VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),

    in_name        VARCHAR(250)             NOT NULL,
    in_description VARCHAR(500),
    in_connection  VARCHAR,
    in_meta        VARCHAR,
    in_status      VARCHAR(50)              NOT NULL,

    in_created     TIMESTAMP WITH TIME ZONE NOT NULL,
    in_updated     TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT integrations_pk PRIMARY KEY (in_id)
);

CREATE UNIQUE INDEX ix_in_guid ON isc.integrations (in_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_in_name ON isc.integrations (in_name);

-- =================== Integration Index Joins ======================

CREATE SEQUENCE isc.inij_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.integration_index_joins
(
    inij_id      BIGINT  NOT NULL DEFAULT nextval('isc.inij_id_seq'),
    inij_guid    VARCHAR NOT NULL DEFAULT uuid_generate_v4(),
    inij_in_id   BIGINT  NOT NULL,
    inij_i_id    BIGINT  NOT NULL,

    inij_created TIMESTAMP WITH TIME ZONE,
    inij_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT integration_index_joins_pk PRIMARY KEY (inij_id),
    FOREIGN KEY (inij_in_id) REFERENCES isc.integrations (in_id),
    FOREIGN KEY (inij_i_id) REFERENCES isc.indexes (i_id)
);

-- Region properties is simplified Map of string and no need to define them as a full entities
CREATE SEQUENCE isc.rp_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.region_props
(
    rp_id           BIGINT  NOT NULL DEFAULT nextval('isc.rp_seq'),
    rp_region_id    BIGINT,
    rp_name         VARCHAR NOT NULL,
    rp_value        VARCHAR
);

CREATE SEQUENCE isc.eu_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.external_users
(
    eu_id                BIGINT                   NOT NULL DEFAULT nextval('isc.eu_id_seq'),
    eu_guid              VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),

    eu_title             VARCHAR(20),
    eu_first_name        VARCHAR(100),
    eu_last_name         VARCHAR(100),
    eu_external_id       VARCHAR(500),
    eu_source            VARCHAR(40)              NOT NULL,
    eu_status            VARCHAR(50)              NOT NULL,
    eu_phone             VARCHAR(30),

    eu_school_site       VARCHAR(255),
    eu_job_title         VARCHAR(100),
    eu_id_full_name      VARCHAR(255),
    eu_id_number         VARCHAR(20),
    eu_office_class      VARCHAR(255),

    eu_created           TIMESTAMP WITH TIME ZONE NOT NULL,
    eu_updated           TIMESTAMP WITH TIME ZONE NOT NULL,
    eu_last_sync_time    TIMESTAMP WITH TIME ZONE,

    CONSTRAINT external_users_pk PRIMARY KEY (eu_id),
    CONSTRAINT external_users_external_id unique (eu_external_id)
);
CREATE UNIQUE INDEX ix_eu_guid ON isc.external_users (eu_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_eu_external_id ON isc.external_users (eu_external_id);

-- ====================== Utilities ===============================
CREATE SEQUENCE isc.ut_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.utilities
(
    ut_id                BIGINT                   NOT NULL DEFAULT nextval('isc.ut_id_seq'),
    ut_guid              VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    ut_r_id              BIGINT,

    ut_status            VARCHAR(50)              NOT NULL,

    ut_external_id       VARCHAR(500),
    ut_type              VARCHAR(50)              NOT NULL,
    ut_name              VARCHAR(50),
    ut_description       VARCHAR(500),

    ut_geo_location      GEOMETRY(POINT, 4326),

    ut_created           TIMESTAMP WITH TIME ZONE NOT NULL,
    ut_updated           TIMESTAMP WITH TIME ZONE NOT NULL,
    ut_last_sync_time    TIMESTAMP WITH TIME ZONE,

    CONSTRAINT utilities_pk PRIMARY KEY (ut_id),
    CONSTRAINT utilities_external_id unique (ut_external_id)
);

CREATE UNIQUE INDEX ix_ut_guid ON isc.utilities (ut_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_ut_external_id ON isc.utilities (ut_external_id);

-- ====================== Utilities Region Joins ===============================
CREATE SEQUENCE isc.utrj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.utilities_region_joins
(
    utrj_id      BIGINT NOT NULL DEFAULT nextval('isc.utrj_id_seq'),

    utrj_r_id    BIGINT NOT NULL, -- parent region
    utrj_u_id    BIGINT NOT NULL, -- child utility

    utrj_created TIMESTAMP WITH TIME ZONE,
    utrj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT utilities_region_joins_pk PRIMARY KEY (utrj_id)
);

-- ====================== Safety Items ===============================
CREATE SEQUENCE IF NOT EXISTS isc.sf_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.safeties
(
    sf_id              BIGINT                   NOT NULL DEFAULT nextval('isc.sf_id_seq'),
    sf_guid            VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    sf_r_id            BIGINT,

    sf_status          VARCHAR(50)              NOT NULL,

    sf_external_id     VARCHAR(500),
    sf_type            VARCHAR(50)              NOT NULL,
    sf_name            VARCHAR(50),
    sf_description     VARCHAR(500),

    sf_geo_location    GEOMETRY(POINT, 4326),

    sf_created         TIMESTAMP WITH TIME ZONE NOT NULL,
    sf_updated         TIMESTAMP WITH TIME ZONE NOT NULL,
    sf_last_sync_time  TIMESTAMP WITH TIME ZONE,

    CONSTRAINT safeties_pk PRIMARY KEY (sf_id),
    CONSTRAINT safeties_external_id unique (sf_external_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS ix_sf_guid ON isc.safeties (sf_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_sf_external_id ON isc.safeties (sf_external_id);

-- ====================== Safety Items Region Joins ===============================
CREATE SEQUENCE IF NOT EXISTS isc.sfrj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.safeties_region_joins
(
    sfrj_id      BIGINT NOT NULL DEFAULT nextval('isc.sfrj_id_seq'),

    sfrj_r_id    BIGINT NOT NULL, -- parent region
    sfrj_s_id    BIGINT NOT NULL, -- child utility

    sfrj_created TIMESTAMP WITH TIME ZONE,
    sfrj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT safeties_region_joins_pk PRIMARY KEY (sfrj_id)
);

-- ====================== Radios ===============================
CREATE SEQUENCE isc.ra_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.radios
(
    ra_id              BIGINT                   NOT NULL DEFAULT nextval('isc.ra_id_seq'),
    ra_guid            VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),

    ra_external_id     VARCHAR(500),
    ra_status          VARCHAR(50)              NOT NULL,
    ra_type            VARCHAR(50),
    ra_name            VARCHAR(250)             NOT NULL,
    ra_description     VARCHAR(500),

    ra_geo_location    GEOMETRY(POINT, 4326),

    ra_created         TIMESTAMP WITH TIME ZONE NOT NULL,
    ra_updated         TIMESTAMP WITH TIME ZONE NOT NULL,
    ra_last_sync_time  TIMESTAMP WITH TIME ZONE,

    ra_conn_status     VARCHAR(50),
    ra_device_state    INTEGER,
    ra_radio_user_id   VARCHAR(50),
    ra_battery_level   INTEGER,
    ra_gps_altitude    DECIMAL,
    ra_gps_direction   INTEGER,
    ra_gps_update_time TIMESTAMP WITH TIME ZONE,

    CONSTRAINT radios_pk PRIMARY KEY (ra_id),
    CONSTRAINT radios_external_id unique (ra_external_id)
);

CREATE UNIQUE INDEX ix_ra_guid ON isc.radios (ra_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_ra_external_id ON isc.radios (ra_external_id);

CREATE SEQUENCE IF NOT EXISTS isc.ras_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.radios_state
(
    ras_id           BIGINT  NOT NULL DEFAULT nextval('isc.ras_id_seq'),
    ras_radio_id     BIGINT,
    ras_name         VARCHAR NOT NULL,
    ras_value        VARCHAR,
    ras_updated      TIMESTAMP WITH TIME ZONE
);

CREATE SEQUENCE isc.rarj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.radio_region_joins
(
    rarj_id      BIGINT NOT NULL DEFAULT nextval('isc.rarj_id_seq'),

    rarj_r_id    BIGINT NOT NULL, -- parent region
    rarj_ra_id    BIGINT NOT NULL, -- child door

    rarj_created TIMESTAMP WITH TIME ZONE,
    rarj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT radio_region_joins_pk PRIMARY KEY (rarj_id)
);