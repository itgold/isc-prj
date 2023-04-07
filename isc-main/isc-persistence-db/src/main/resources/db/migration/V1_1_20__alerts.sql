-- old non existing
DROP TABLE IF EXISTS isc.alert_entity_joins;
DROP SEQUENCE IF EXISTS isc.aej_id_seq;
DROP TABLE IF EXISTS isc.alert_configs;
DROP SEQUENCE IF EXISTS isc.ac_id_seq;
DROP TABLE IF EXISTS isc.alert_matchers;
DROP SEQUENCE IF EXISTS isc.acm_id_seq;

-- new/existing
DROP TABLE IF EXISTS isc.alerts;
DROP SEQUENCE IF EXISTS isc.a_id_seq;
DROP INDEX IF EXISTS isc.ix_alerts;
DROP TABLE IF EXISTS isc.alert_triggers;
DROP SEQUENCE IF EXISTS isc.at_id_seq;
DROP TABLE IF EXISTS isc.alert_trigger_matchers;

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
    a_at_guid       VARCHAR(40)                NOT NULL,

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

