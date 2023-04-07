CREATE SEQUENCE IF NOT EXISTS isc.ds_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.doors_state
(
    ds_id               BIGINT      	NOT NULL DEFAULT nextval('isc.ds_id_seq'),
    ds_guid             VARCHAR     	NOT NULL DEFAULT uuid_generate_v4(),
    d_id                BIGINT,
    ds_type             VARCHAR			NOT NULL,
    ds_value            VARCHAR(500),

    ds_created          TIMESTAMP WITH TIME ZONE,
    ds_updated          TIMESTAMP WITH TIME ZONE,

    CONSTRAINT doors_state_pk PRIMARY KEY (ds_id)
);

CREATE SEQUENCE IF NOT EXISTS isc.cs_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.cameras_state
(
    cs_id               BIGINT      	NOT NULL DEFAULT nextval('isc.cs_id_seq'),
    cs_guid             VARCHAR     	NOT NULL DEFAULT uuid_generate_v4(),
    c_id                BIGINT,
    cs_type             VARCHAR			NOT NULL,
    cs_value            VARCHAR(500),

    cs_created          TIMESTAMP WITH TIME ZONE,
    cs_updated          TIMESTAMP WITH TIME ZONE,

    CONSTRAINT cameras_state_pk PRIMARY KEY (cs_id)
);

CREATE SEQUENCE IF NOT EXISTS isc.drs_id_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.drones_state
(
    drs_id               BIGINT      	NOT NULL DEFAULT nextval('isc.drs_id_seq'),
    drs_guid             VARCHAR     	NOT NULL DEFAULT uuid_generate_v4(),
    dr_id                BIGINT,
    drs_type             VARCHAR			NOT NULL,
    drs_value            VARCHAR(500),

    drs_created          TIMESTAMP WITH TIME ZONE,
    drs_updated          TIMESTAMP WITH TIME ZONE,

    CONSTRAINT drones_state_pk PRIMARY KEY (drs_id)
);
