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
    ra_gps_altitude    DECIMAL,
    ra_gps_direction   INTEGER,
    ra_gps_update_time TIMESTAMP WITH TIME ZONE,

    ra_created         TIMESTAMP WITH TIME ZONE NOT NULL,
    ra_updated         TIMESTAMP WITH TIME ZONE NOT NULL,
    ra_last_sync_time  TIMESTAMP WITH TIME ZONE,

    ra_conn_status     VARCHAR(50),
    ra_device_state    INTEGER,
    ra_radio_user_id   VARCHAR(50),
    ra_battery_level   INTEGER,

    CONSTRAINT radios_pk PRIMARY KEY (ra_id)
);

CREATE UNIQUE INDEX ix_ra_guid ON isc.radios (ra_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_ra_external_id ON isc.radios (ra_external_id);

CREATE SEQUENCE IF NOT EXISTS isc.ras_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.radios_state
(
    ras_id           BIGINT  NOT NULL DEFAULT nextval('isc.ras_id_seq'),
    ras_radio_id      BIGINT,
    ras_name         VARCHAR NOT NULL,
    ras_value        VARCHAR,
    ras_updated      TIMESTAMP WITH TIME ZONE
);

CREATE SEQUENCE isc.rarj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.radio_region_joins
(
    rarj_id      BIGINT NOT NULL DEFAULT nextval('isc.rarj_id_seq'),

    rarj_r_id    BIGINT NOT NULL, -- parent region
    rarj_ra_id    BIGINT NOT NULL, -- child radio

    rarj_created TIMESTAMP WITH TIME ZONE,
    rarj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT radio_region_joins_pk PRIMARY KEY (rarj_id)
);
