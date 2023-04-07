-- add support for regions multi parent
ALTER TABLE isc.regions ADD COLUMN r_sec_r_id BIGINT;
ALTER TABLE isc.regions ADD FOREIGN KEY (r_r_id) REFERENCES isc.regions (r_id);
ALTER TABLE isc.regions ADD FOREIGN KEY (r_sec_r_id) REFERENCES isc.regions (r_id);

-- doors multi parent support and primary parent mandatory constraint
ALTER TABLE isc.doors ALTER COLUMN d_r_id SET NOT NULL;
ALTER TABLE isc.doors ADD COLUMN d_sec_r_id BIGINT;
ALTER TABLE isc.doors ADD FOREIGN KEY (d_r_id) REFERENCES isc.regions (r_id);
ALTER TABLE isc.doors ADD FOREIGN KEY (d_sec_r_id) REFERENCES isc.regions (r_id);

-- parent region is mandatory for cameras
ALTER TABLE isc.cameras ALTER COLUMN c_r_id SET NOT NULL;

-- parent region is mandatory for drones
ALTER TABLE isc.drones ALTER COLUMN dr_r_id SET NOT NULL;
