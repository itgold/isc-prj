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

    CONSTRAINT external_users_pk PRIMARY KEY (eu_id)
);
