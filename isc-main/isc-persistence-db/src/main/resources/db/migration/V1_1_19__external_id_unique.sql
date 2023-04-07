-- ============ Add unique external id constraint for all devices ==============
ALTER TABLE isc.doors ADD CONSTRAINT doors_external_id UNIQUE (d_external_id);
ALTER TABLE isc.cameras ADD CONSTRAINT cameras_external_id UNIQUE (c_external_id);
ALTER TABLE isc.drones ADD CONSTRAINT drones_external_id UNIQUE (dr_external_id);
ALTER TABLE isc.speakers ADD CONSTRAINT speakers_external_id UNIQUE (sp_external_id);
ALTER TABLE isc.external_users ADD CONSTRAINT external_users_external_id UNIQUE (eu_external_id);
ALTER TABLE isc.utilities ADD CONSTRAINT utilities_external_id UNIQUE (ut_external_id);
ALTER TABLE isc.safeties ADD CONSTRAINT safeties_external_id UNIQUE (sf_external_id);
ALTER TABLE isc.radios ADD CONSTRAINT radios_external_id UNIQUE (ra_external_id);

-- ============ Add indexes for the external user entities ==============
CREATE UNIQUE INDEX ix_eu_guid ON isc.external_users (eu_guid);
CREATE UNIQUE INDEX IF NOT EXISTS ix_eu_external_id ON isc.external_users (eu_external_id);

