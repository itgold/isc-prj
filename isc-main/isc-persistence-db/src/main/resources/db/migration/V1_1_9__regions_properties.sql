ALTER TABLE isc.regions ADD r_subtype VARCHAR(50);
ALTER TABLE isc.regions ADD r_description VARCHAR(500);

CREATE SEQUENCE isc.rp_seq INCREMENT BY 1 MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS isc.region_props
(
    rp_id           BIGINT  NOT NULL DEFAULT nextval('isc.rp_seq'),
    rp_region_id    BIGINT,
    rp_name         VARCHAR NOT NULL,
    rp_value        VARCHAR
);
