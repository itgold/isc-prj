DROP TABLE IF EXISTS isc.doors_state;
DROP TABLE IF EXISTS isc.cameras_state;
DROP TABLE IF EXISTS isc.drones_state;
DROP TABLE IF EXISTS isc.speakers_state;

CREATE SEQUENCE IF NOT EXISTS isc.ds_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.doors_state
(
    ds_id           BIGINT  NOT NULL DEFAULT nextval('isc.ds_id_seq'),
    ds_door_id      BIGINT,
    ds_name         VARCHAR NOT NULL,
    ds_value        VARCHAR,
    ds_updated      TIMESTAMP WITH TIME ZONE
);

CREATE SEQUENCE IF NOT EXISTS isc.cs_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.cameras_state
(
    cs_id           BIGINT  NOT NULL DEFAULT nextval('isc.cs_id_seq'),
    cs_camera_id    BIGINT,
    cs_name         VARCHAR NOT NULL,
    cs_value        VARCHAR,
    cs_updated      TIMESTAMP WITH TIME ZONE
);

CREATE SEQUENCE IF NOT EXISTS isc.drs_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.drones_state
(
    drs_id           BIGINT  NOT NULL DEFAULT nextval('isc.drs_id_seq'),
    drs_drone_id      BIGINT,
    drs_name         VARCHAR NOT NULL,
    drs_value        VARCHAR,
    drs_updated      TIMESTAMP WITH TIME ZONE
);

CREATE SEQUENCE IF NOT EXISTS isc.sps_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.speakers_state
(
    sps_id           BIGINT  NOT NULL DEFAULT nextval('isc.sps_id_seq'),
    sps_speaker_id   BIGINT,
    sps_name         VARCHAR NOT NULL,
    sps_value        VARCHAR,
    sps_updated      TIMESTAMP WITH TIME ZONE
);