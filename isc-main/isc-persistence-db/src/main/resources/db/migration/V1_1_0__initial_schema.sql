-- noinspection SqlResolveForFile
-- Warning: Never uncomment the following line when running in automated schema update mode:
-- DROP SCHEMA IF EXISTS isc CASCADE;
CREATE SCHEMA isc;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ====================== School districts ===============================

CREATE SEQUENCE isc.sd_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.school_districts
(
    sd_id             BIGINT       NOT NULL DEFAULT nextval('isc.sd_id_seq'),
    sd_guid           VARCHAR      NOT NULL DEFAULT uuid_generate_v4(),
    sd_name           VARCHAR(500) NOT NULL,

    sd_contact_email  VARCHAR(500) NOT NULL,
    sd_address        VARCHAR(500),
    sd_city           VARCHAR(200),
    sd_state          VARCHAR(200),
    sd_zip_code       VARCHAR(50),
    sd_country        VARCHAR(200),
    sd_status         VARCHAR(200),

    sd_geo_location   GEOMETRY(POINT, 4326),
    sd_geo_zoom       real,
    sd_geo_rotation   real,
    sd_geo_boundaries GEOMETRY(POLYGON, 4326),

    sd_created        TIMESTAMP WITH TIME ZONE,
    sd_updated        TIMESTAMP WITH TIME ZONE,

    CONSTRAINT school_districts_pk PRIMARY KEY (sd_id)
);

CREATE UNIQUE INDEX ix_sd_guid ON isc.school_districts (sd_guid);
CREATE UNIQUE INDEX ix_sd_name ON isc.school_districts (sd_name);

-- ====================== Schools ===============================

CREATE SEQUENCE isc.s_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.schools
(
    s_id             BIGINT       NOT NULL DEFAULT nextval('isc.s_id_seq'),
    s_guid           VARCHAR      NOT NULL DEFAULT uuid_generate_v4(),
    s_name           VARCHAR(500) NOT NULL,
    s_sd_id          BIGINT, -- School's district

    s_contact_email  VARCHAR(500) NOT NULL,
    s_address        VARCHAR(500),
    s_city           VARCHAR(200),
    s_state          VARCHAR(200),
    s_zip_code       VARCHAR(50),
    s_country        VARCHAR(200),
    s_status         VARCHAR(200),

    s_geo_location   GEOMETRY(POINT, 4326),
    s_geo_zoom       real,
    s_geo_rotation   real,
    s_geo_boundaries GEOMETRY(POLYGON, 4326),

    s_created        TIMESTAMP WITH TIME ZONE,
    s_updated        TIMESTAMP WITH TIME ZONE,

    CONSTRAINT schools_pk PRIMARY KEY (s_id)
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
    ro_guid    VARCHAR      NOT NULL DEFAULT uuid_generate_v4(),
    ro_name    VARCHAR NOT NULL,

    ro_created TIMESTAMP WITH TIME ZONE,
    ro_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT roles_pk PRIMARY KEY (ro_id)
);

CREATE UNIQUE INDEX ix_ro_name ON isc.roles (ro_name);

-- ====================== User Role Joins ==============================

CREATE SEQUENCE isc.urj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.user_roles_joins
(
    urj_id      BIGINT NOT NULL DEFAULT nextval('isc.urj_id_seq'),

    urj_u_id    BIGINT NOT NULL,
    urj_ro_id   BIGINT NOT NULL,

    urj_created TIMESTAMP WITH TIME ZONE,
    urj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT user_roles_joins_pk PRIMARY KEY (urj_id),
    CONSTRAINT user_roles_joins_uk UNIQUE (urj_u_id, urj_ro_id),
    FOREIGN KEY (urj_u_id) REFERENCES isc.users (u_id),
    FOREIGN KEY (urj_ro_id) REFERENCES isc.roles (ro_id)
);

-- ====================== School Regions ======================

CREATE SEQUENCE isc.r_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.regions
(
    r_id             BIGINT                   NOT NULL DEFAULT nextval('isc.r_id_seq'),
    r_guid           VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    r_s_id           BIGINT                   NOT NULL,
    r_r_id           BIGINT,

    r_name           VARCHAR(500),
    r_type           VARCHAR(50)              NOT NULL,
    r_status         VARCHAR(50)              NOT NULL,

    r_geo_location   GEOMETRY(POINT, 4326),
    r_geo_boundaries GEOMETRY(POLYGON, 4326),

    r_created        TIMESTAMP WITH TIME ZONE NOT NULL,
    r_updated        TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT regions_pk PRIMARY KEY (r_id)
);

CREATE UNIQUE INDEX ix_r_guid ON isc.regions (r_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_r_name ON isc.regions (r_name);

-- ====================== Doors ===============================

CREATE SEQUENCE isc.d_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.doors
(
    d_id           BIGINT                   NOT NULL DEFAULT nextval('isc.d_id_seq'),
    d_guid         VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    d_s_id         BIGINT                   NOT NULL,
    d_r_id         BIGINT,

    d_external_id  VARCHAR(500),
    d_status       VARCHAR(50)              NOT NULL,
    d_type         VARCHAR(50)              NOT NULL,
    d_name         VARCHAR(50),
    d_description  VARCHAR(500),

    d_geo_location GEOMETRY(POINT, 4326),

    d_created      TIMESTAMP WITH TIME ZONE NOT NULL,
    d_updated      TIMESTAMP WITH TIME ZONE NOT NULL,

    d_conn_status   VARCHAR(50),
    d_online_status VARCHAR(50),
    d_battery_status VARCHAR(50),
    d_tamper_status VARCHAR(50),
    d_opening_mode  VARCHAR(50),
    d_battery_level INTEGER,
    d_require_update BOOLEAN,

    CONSTRAINT doors_pk PRIMARY KEY (d_id)
);

CREATE UNIQUE INDEX ix_d_guid ON isc.doors (d_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_d_external_id ON isc.doors (d_external_id);

-- ====================== Cameras ===============================

CREATE SEQUENCE isc.c_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.cameras
(
    c_id           BIGINT                   NOT NULL DEFAULT nextval('isc.c_id_seq'),
    c_guid         VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    c_s_id         BIGINT                   NOT NULL,
    c_r_id         BIGINT,

    c_external_id  VARCHAR(500),
    c_status       VARCHAR(50)              NOT NULL,
    c_type         VARCHAR(50)              NOT NULL,
    c_description  VARCHAR(2048),

    c_geo_location GEOMETRY(POINT, 4326),

    c_created      TIMESTAMP WITH TIME ZONE NOT NULL,
    c_updated      TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT cameras_pk PRIMARY KEY (c_id)
);

CREATE UNIQUE INDEX ix_c_guid ON isc.cameras (c_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_c_external_id ON isc.cameras (c_external_id);

-- ====================== Drones ===============================

CREATE SEQUENCE isc.dr_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.drones
(
    dr_id                BIGINT                   NOT NULL DEFAULT nextval('isc.dr_id_seq'),
    dr_guid              VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    dr_s_id              BIGINT                   NOT NULL,
    dr_r_id              BIGINT,

    dr_external_id       VARCHAR(500),
    dr_status            VARCHAR(50)              NOT NULL,
    dr_type              VARCHAR(50)              NOT NULL,

    dr_geo_location      GEOMETRY(POINT, 4326),
    dr_geo_curr_location GEOMETRY(POINT, 4326),

    dr_created           TIMESTAMP WITH TIME ZONE NOT NULL,
    dr_updated           TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT drones_pk PRIMARY KEY (dr_id)
);

CREATE UNIQUE INDEX ix_dr_guid ON isc.drones (dr_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_dr_external_id ON isc.drones (dr_external_id);

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
    a_id       BIGINT                   NOT NULL DEFAULT nextval('isc.a_id_seq'),
    a_guid     VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    a_name     VARCHAR(100)             NOT NULL,
    a_severity VARCHAR(100)             NOT NULL,

    a_created  TIMESTAMP WITH TIME ZONE NOT NULL,
    a_updated  TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT alerts_pk PRIMARY KEY (a_id)
);

CREATE SEQUENCE isc.aej_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.alert_entity_joins
(
    aej_id          BIGINT                   NOT NULL DEFAULT nextval('isc.aej_id_seq'),
    aej_guid        VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    aej_a_id        BIGINT                   NOT NULL,
    aej_entity_id   BIGINT                   NOT NULL,

    aej_entity_type VARCHAR(100)             NOT NULL,

    aej_created     TIMESTAMP WITH TIME ZONE NOT NULL,
    aej_updated     TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT alert_entity_joins_pk PRIMARY KEY (aej_id)
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
CREATE TABLE IF NOT EXISTS isc.school_district_indexes_joins
(
    sdij_id      BIGINT                     NOT NULL DEFAULT nextval('isc.sdij_id_seq'),
    sdij_guid    VARCHAR                    NOT NULL DEFAULT uuid_generate_v4(),
    sdij_sd_id   BIGINT                     NOT NULL,
    sdij_i_id    BIGINT                     NOT NULL,

    sdij_created TIMESTAMP WITH TIME ZONE,
    sdij_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT school_district_indexes_joins_pk PRIMARY KEY (sdij_id),
    CONSTRAINT school_district_indexes_joins_uk UNIQUE (sdij_sd_id, sdij_i_id),
    FOREIGN KEY (sdij_sd_id) REFERENCES isc.school_districts (sd_id),
    FOREIGN KEY (sdij_i_id) REFERENCES isc.indexes (i_id)
);

-- =================== School Index Joins ======================

CREATE SEQUENCE isc.sij_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.school_indexes_joins
(
    sij_id      BIGINT                      NOT NULL DEFAULT nextval('isc.sij_id_seq'),
    sij_guid    VARCHAR                     NOT NULL DEFAULT uuid_generate_v4(),
    sij_s_id    BIGINT                      NOT NULL,
    sij_i_id    BIGINT                      NOT NULL,

    sij_created TIMESTAMP WITH TIME ZONE,
    sij_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT school_indexes_joins_pk PRIMARY KEY (sij_id),
    CONSTRAINT school_indexes_joins_uk UNIQUE (sij_s_id, sij_i_id),
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
CREATE TABLE IF NOT EXISTS isc.integration_indexes_joins
(
    inij_id      BIGINT                     NOT NULL DEFAULT nextval('isc.inij_id_seq'),
    inij_guid    VARCHAR                    NOT NULL DEFAULT uuid_generate_v4(),
    inij_in_id   BIGINT                     NOT NULL,
    inij_i_id    BIGINT                     NOT NULL,

    inij_created TIMESTAMP WITH TIME ZONE,
    inij_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT integration_indexes_joins_pk PRIMARY KEY (inij_id),
    FOREIGN KEY (inij_in_id) REFERENCES isc.integrations (in_id),
    FOREIGN KEY (inij_i_id) REFERENCES isc.indexes (i_id)
);
