-- ====================== Speakers ===============================
CREATE SEQUENCE isc.sp_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.speakers
(
    sp_id           BIGINT                   NOT NULL DEFAULT nextval('isc.sp_id_seq'),
    sp_guid         VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    sp_s_id         BIGINT                   NOT NULL,
    sp_r_id         BIGINT                   NOT NULL,

    sp_external_id  VARCHAR(500),
    sp_status       VARCHAR(50)              NOT NULL,
    sp_type         VARCHAR(50)              NOT NULL,
    sp_name         VARCHAR(50),
    sp_description  VARCHAR(2048),

    sp_geo_location GEOMETRY(POINT, 4326),

    sp_created      TIMESTAMP WITH TIME ZONE NOT NULL,
    sp_updated      TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT speakers_pk PRIMARY KEY (sp_id)
);

CREATE UNIQUE INDEX ix_sp_guid ON isc.speakers (sp_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_sp_external_id ON isc.speakers (sp_external_id);

-- ====================== Speakers State ===============================
CREATE SEQUENCE isc.sps_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.speakers_state
(
    sps_id      BIGINT  NOT NULL DEFAULT nextval('isc.sps_id_seq'),
    sps_guid    VARCHAR NOT NULL DEFAULT uuid_generate_v4(),
    sp_id       BIGINT,
    sps_type    VARCHAR NOT NULL,
    sps_value   VARCHAR(500),

    sps_created TIMESTAMP WITH TIME ZONE,
    sps_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT speakers_state_pk PRIMARY KEY (sps_id)
);
