-- ====================== Safety Items ===============================
CREATE SEQUENCE isc.sf_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.safeties
(
    sf_id                BIGINT                   NOT NULL DEFAULT nextval('isc.sf_id_seq'),
    sf_guid              VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    sf_r_id              BIGINT,

    sf_status            VARCHAR(50)              NOT NULL,

    sf_external_id       VARCHAR(500),
    sf_type              VARCHAR(50)              NOT NULL,
    sf_name              VARCHAR(50),
    sf_description       VARCHAR(500),

    sf_geo_location      GEOMETRY(POINT, 4326),

    sf_created           TIMESTAMP WITH TIME ZONE NOT NULL,
    sf_updated           TIMESTAMP WITH TIME ZONE NOT NULL,
    sf_last_sync_time    TIMESTAMP WITH TIME ZONE,

    CONSTRAINT safeties_pk PRIMARY KEY (sf_id)
);

CREATE UNIQUE INDEX ix_sf_guid ON isc.safeties (sf_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_sf_external_id ON isc.safeties (sf_external_id);

-- ====================== Safety Items Region Joins ===============================
CREATE SEQUENCE isc.sfrj_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.safeties_region_joins
(
    sfrj_id      BIGINT NOT NULL DEFAULT nextval('isc.sfrj_id_seq'),

    sfrj_r_id    BIGINT NOT NULL, -- parent region
    sfrj_s_id    BIGINT NOT NULL, -- child utility

    sfrj_created TIMESTAMP WITH TIME ZONE,
    sfrj_updated TIMESTAMP WITH TIME ZONE,

    CONSTRAINT safeties_region_joins_pk PRIMARY KEY (sfrj_id)
);
