-- ====================== Utilities ===============================
CREATE SEQUENCE isc.ut_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.utilities
(
    ut_id           BIGINT                   NOT NULL DEFAULT nextval('isc.ut_id_seq'),
    ut_guid         VARCHAR                  NOT NULL DEFAULT uuid_generate_v4(),
    ut_r_id         BIGINT,

    ut_status       VARCHAR(50)              NOT NULL,

    ut_external_id  VARCHAR(500),
    ut_type         VARCHAR(50)              NOT NULL,
    ut_name         VARCHAR(50),
    ut_description  VARCHAR(500),

    ut_geo_location GEOMETRY(POINT, 4326),

    ut_created      TIMESTAMP WITH TIME ZONE NOT NULL,
    ut_updated      TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT utilities_pk PRIMARY KEY (ut_id)
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
