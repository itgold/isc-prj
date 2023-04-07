-- Create Categories
TRUNCATE TABLE isc.tags CASCADE;
TRUNCATE TABLE isc.doors CASCADE;
TRUNCATE TABLE isc.doors_state CASCADE;
TRUNCATE TABLE isc.door_region_joins CASCADE;
TRUNCATE TABLE isc.cameras CASCADE;
TRUNCATE TABLE isc.cameras_state CASCADE;
TRUNCATE TABLE isc.camera_region_joins CASCADE;
TRUNCATE TABLE isc.drones CASCADE;
TRUNCATE TABLE isc.drones_state CASCADE;
TRUNCATE TABLE isc.drone_region_joins CASCADE;
TRUNCATE TABLE isc.speakers CASCADE;
TRUNCATE TABLE isc.speakers_state CASCADE;
TRUNCATE TABLE isc.speaker_region_joins CASCADE;

-- doors
INSERT INTO isc.doors(d_id, d_guid, d_name, d_external_id, d_status, d_type, d_geo_location, d_created, d_updated)
VALUES (NEXTVAL('isc.d_id_seq'), '92944e3c-4612-4cfd-a035-7edf6220a7f2', 'door_1', '1ce459fd-b3e7-4676-857a-eb701e2a05dc', 'ACTIVATED', 'INDOOR', '0101000020E6100000EAFD5D0B6E9A5DC053391C8B0E084140', current_timestamp, current_timestamp);
INSERT INTO isc.doors(d_id, d_guid, d_name, d_external_id, d_status, d_type, d_geo_location, d_created, d_updated)
VALUES (NEXTVAL('isc.d_id_seq'), '5a3f5c5f-e8ce-41c1-9ac3-6b67e0406b99', 'door_2', 'bcb87e1e-bc5c-496f-a6a4-8db926c52310', 'ACTIVATED', 'INDOOR', '0101000020E6100000F64BFE31709A5DC0B514C5C809084140', current_timestamp, current_timestamp);
INSERT INTO isc.doors(d_id, d_guid, d_name, d_external_id, d_status, d_type, d_geo_location, d_created, d_updated)
VALUES (NEXTVAL('isc.d_id_seq'), '32bd830b-8d09-44ac-bd45-f998e32b614d', 'door_3', 'fd91e689-b959-40f7-9af5-f40b158c4da4', 'ACTIVATED', 'GATE', '0101000020E61000006480183E649A5DC03273D9E408084140', current_timestamp, current_timestamp);

-- cameras
INSERT INTO isc.cameras(c_id, c_guid, c_name, c_external_id, c_status, c_type, c_geo_location, c_created, c_updated)
VALUES (NEXTVAL('isc.c_id_seq'), uuid_generate_v4(), 'camera_1', '656991f4-5911-451b-8b17-f64f08a0fd80', 'ACTIVATED', 'VIDEO', '0101000020E610000056CAA3D46D9A5DC0761EA6CC09084140', current_timestamp, current_timestamp);
INSERT INTO isc.cameras(c_id, c_guid, c_name, c_external_id, c_status, c_type, c_geo_location, c_created, c_updated)
VALUES (NEXTVAL('isc.c_id_seq'), uuid_generate_v4(), 'camera_2', '0ddab92b-d00a-437b-9853-e1b024866261', 'ACTIVATED', 'VIDEO', '0101000020E6100000CAD6FA3B6F9A5DC0D571BFB30A084140', current_timestamp, current_timestamp);
INSERT INTO isc.cameras(c_id, c_guid, c_name, c_external_id, c_status, c_type, c_geo_location, c_created, c_updated)
VALUES (NEXTVAL('isc.c_id_seq'), uuid_generate_v4(), 'camera_3', 'd0cb748d-25cf-42c1-9730-969fae0b0520', 'ACTIVATED', 'VIDEO', '0101000020E6100000F22D3DE76E9A5DC0A18D272C0B084140', current_timestamp, current_timestamp);

-- drones
INSERT INTO isc.drones(dr_id, dr_guid, dr_name, dr_external_id, dr_status, dr_type, dr_geo_location, dr_created, dr_updated)
VALUES (NEXTVAL('isc.dr_id_seq'), uuid_generate_v4(), 'drone_1', 'a1d088d3-8195-455d-9527-5b8eb8aab18c', 'ACTIVATED', 'TETHERED', '0101000020E61000001937C12C6E9A5DC05C36B10004084140', current_timestamp, current_timestamp);
INSERT INTO isc.drones(dr_id, dr_guid, dr_name, dr_external_id, dr_status, dr_type, dr_geo_location, dr_created, dr_updated)
VALUES (NEXTVAL('isc.dr_id_seq'), uuid_generate_v4(), 'drone_2', '775ae9ee-e6b1-4929-bac6-23fb74136277', 'ACTIVATED', 'TETHERED', '0101000020E610000004C3E4586F9A5DC072E7DF3009084140', current_timestamp, current_timestamp);
INSERT INTO isc.drones(dr_id, dr_guid, dr_name, dr_external_id, dr_status, dr_type, dr_geo_location, dr_created, dr_updated)
VALUES (NEXTVAL('isc.dr_id_seq'), uuid_generate_v4(), 'drone_3', 'e070d96e-83d7-4582-84b2-7addce3a3207', 'ACTIVATED', 'TETHERED', '0101000020E6100000B7BC370F6D9A5DC0FF99D35403084140', current_timestamp, current_timestamp);

-- speakers
INSERT INTO isc.speakers(sp_id, sp_guid, sp_name, sp_external_id, sp_status, sp_type, sp_geo_location, sp_created, sp_updated)
VALUES (NEXTVAL('isc.sp_id_seq'), uuid_generate_v4(), 'speaker_1', '2f8ba3ec-b314-4263-b211-93bd70f7d080', 'ACTIVATED', 'LEGACY', '0101000020E6100000C27BFA02699A5DC014D3098209084140', current_timestamp, current_timestamp);
INSERT INTO isc.speakers(sp_id, sp_guid, sp_name, sp_external_id, sp_status, sp_type, sp_geo_location, sp_created, sp_updated)
VALUES (NEXTVAL('isc.sp_id_seq'), uuid_generate_v4(), 'speaker_2', 'c7b39a69-7156-4cda-bc7c-c3a72e3f9871', 'ACTIVATED', 'CEILING', '0101000020E61000005FB2D77D699A5DC0E90EAA0609084140', current_timestamp, current_timestamp);
INSERT INTO isc.speakers(sp_id, sp_guid, sp_name, sp_external_id, sp_status, sp_type, sp_geo_location, sp_created, sp_updated)
VALUES (NEXTVAL('isc.sp_id_seq'), uuid_generate_v4(), 'speaker_3', '14ccaaf9-a8a0-4395-8226-a79e1df33c7f', 'ACTIVATED', 'FLOOR', '0101000020E610000088AC8785699A5DC013E31DBC07084140', current_timestamp, current_timestamp);

-- tags
INSERT INTO isc.tags(t_id, t_guid, t_name, t_created, t_updated)
VALUES (NEXTVAL('isc.t_id_seq'), uuid_generate_v4(), 'tag1', current_timestamp, current_timestamp);
INSERT INTO isc.tags(t_id, t_guid, t_name, t_created, t_updated)
VALUES (NEXTVAL('isc.t_id_seq'), uuid_generate_v4(), 'tag2', current_timestamp, current_timestamp);
INSERT INTO isc.tags(t_id, t_guid, t_name, t_created, t_updated)
VALUES (NEXTVAL('isc.t_id_seq'), uuid_generate_v4(), 'tag3', current_timestamp, current_timestamp);

-- utilities
INSERT INTO isc.utilities(ut_id, ut_guid, ut_name, ut_external_id, ut_status, ut_type, ut_geo_location, ut_created, ut_updated)
VALUES (NEXTVAL('isc.ut_id_seq'), uuid_generate_v4(), 'Name for Ventilation', uuid_generate_v4(),'ACTIVATED', 'VENTILATION_SHUTOFF', '0101000020E6100000EAFD5D0B6E9A5DC053391C8B0E084140', current_timestamp, current_timestamp);

INSERT INTO isc.utilities(ut_id, ut_guid, ut_name, ut_external_id, ut_status, ut_type, ut_geo_location, ut_created, ut_updated)
VALUES (NEXTVAL('isc.ut_id_seq'), uuid_generate_v4(), 'Name for Gas Valve', uuid_generate_v4(),'ACTIVATED', 'GAS_SHUTOFF', '0101000020E6100000EAFD5D0B6E9A5DC053391C8B0E084140', current_timestamp, current_timestamp);

INSERT INTO isc.utilities(ut_id, ut_guid, ut_name, ut_external_id, ut_status, ut_type, ut_geo_location, ut_created, ut_updated)
VALUES (NEXTVAL('isc.ut_id_seq'), uuid_generate_v4(), 'Name for Water shutoff', uuid_generate_v4(),'ACTIVATED', 'WATER_SHUTOFF', '0101000020E6100000EAFD5D0B6E9A5DC053391C8B0E084140', current_timestamp, current_timestamp);

INSERT INTO isc.utilities(ut_id, ut_guid, ut_name, ut_external_id, ut_status, ut_type, ut_geo_location, ut_created, ut_updated)
VALUES (NEXTVAL('isc.ut_id_seq'), uuid_generate_v4(), 'Name for Electrical', uuid_generate_v4(),'ACTIVATED', 'ELECTRICAL_SHUTOFF', '0101000020E6100000EAFD5D0B6E9A5DC053391C8B0E084140', current_timestamp, current_timestamp);

INSERT INTO isc.utilities(ut_id, ut_guid, ut_name, ut_external_id, ut_status, ut_type, ut_geo_location, ut_created, ut_updated)
VALUES (NEXTVAL('isc.ut_id_seq'), uuid_generate_v4(), 'Name for Fire Alarm Panel', uuid_generate_v4(),'ACTIVATED', 'FIRE_ALARM_PANEL', '0101000020E6100000EAFD5D0B6E9A5DC053391C8B0E084140', current_timestamp, current_timestamp);

INSERT INTO isc.utilities(ut_id, ut_guid, ut_name, ut_external_id, ut_status, ut_type, ut_geo_location, ut_created, ut_updated)
VALUES (NEXTVAL('isc.ut_id_seq'), uuid_generate_v4(), 'Name for Stand Pipe', uuid_generate_v4(),'ACTIVATED', 'STAND_PIPE', '0101000020E6100000EAFD5D0B6E9A5DC053391C8B0E084140', current_timestamp, current_timestamp);

INSERT INTO isc.utilities(ut_id, ut_guid, ut_name, ut_external_id, ut_status, ut_type, ut_geo_location, ut_created, ut_updated)
VALUES (NEXTVAL('isc.ut_id_seq'), uuid_generate_v4(), 'Name for Dry Stand Pipe', uuid_generate_v4(),'ACTIVATED', 'STAND_PIPE_DRY', '0101000020E6100000EAFD5D0B6E9A5DC053391C8B0E084140', current_timestamp, current_timestamp);

-- safeties
INSERT INTO isc.safeties(sf_id, sf_guid, sf_name, sf_external_id, sf_status, sf_type, sf_geo_location, sf_created, sf_updated)
VALUES (NEXTVAL('isc.sf_id_seq'), uuid_generate_v4(), 'Name for Fire Alarm', uuid_generate_v4(),'ACTIVATED', 'FIRE_ALARM', '0101000020E6100000EAFD5D0B6E9A5DC053391C8B0E084140', current_timestamp, current_timestamp);

INSERT INTO isc.safeties(sf_id, sf_guid, sf_name, sf_external_id, sf_status, sf_type, sf_geo_location, sf_created, sf_updated)
VALUES (NEXTVAL('isc.sf_id_seq'), uuid_generate_v4(), 'Name for Defibrillator', uuid_generate_v4(),'ACTIVATED', 'DEFIBRILLATOR', '0101000020E6100000FCFE9E706E9A5DC03245CB4808084140', current_timestamp, current_timestamp);

INSERT INTO isc.safeties(sf_id, sf_guid, sf_name, sf_external_id, sf_status, sf_type, sf_geo_location, sf_created, sf_updated)
VALUES (NEXTVAL('isc.sf_id_seq'), uuid_generate_v4(), 'Name for First Aid kit', uuid_generate_v4(),'ACTIVATED', 'FIRST_AID_KIT', '0101000020E6100000F6904F01689A5DC0E4D9694DFE074140', current_timestamp, current_timestamp);

INSERT INTO isc.safeties(sf_id, sf_guid, sf_name, sf_external_id, sf_status, sf_type, sf_geo_location, sf_created, sf_updated)
VALUES (NEXTVAL('isc.sf_id_seq'), uuid_generate_v4(), 'Name for Fire Extinguisher', uuid_generate_v4(),'ACTIVATED', 'FIRE_EXTINGUISHER', '0101000020E61000005DB172D66B9A5DC0DAAD17170A084140', current_timestamp, current_timestamp);

INSERT INTO isc.safeties(sf_id, sf_guid, sf_name, sf_external_id, sf_status, sf_type, sf_geo_location, sf_created, sf_updated)
VALUES (NEXTVAL('isc.sf_id_seq'), uuid_generate_v4(), 'Name for Smoke Detector', uuid_generate_v4(),'ACTIVATED', 'SMOKE_DETECTOR', '0101000020E61000005DB172D66B9A5DC0DAAD17170A084140', current_timestamp, current_timestamp);

INSERT INTO isc.safeties(sf_id, sf_guid, sf_name, sf_external_id, sf_status, sf_type, sf_geo_location, sf_created, sf_updated)
VALUES (NEXTVAL('isc.sf_id_seq'), uuid_generate_v4(), 'Name for Pull Station', uuid_generate_v4(),'ACTIVATED', 'PULL_STATION', '0101000020E61000005DB172D66B9A5DC0DAAD17170A084140', current_timestamp, current_timestamp);

INSERT INTO isc.safeties(sf_id, sf_guid, sf_name, sf_external_id, sf_status, sf_type, sf_geo_location, sf_created, sf_updated)
VALUES (NEXTVAL('isc.sf_id_seq'), uuid_generate_v4(), 'Name for Fire Hose', uuid_generate_v4(),'ACTIVATED', 'FIRE_HOSE', '0101000020E61000005DB172D66B9A5DC0DAAD17170A084140', current_timestamp, current_timestamp);

INSERT INTO isc.safeties(sf_id, sf_guid, sf_name, sf_external_id, sf_status, sf_type, sf_geo_location, sf_created, sf_updated)
VALUES (NEXTVAL('isc.sf_id_seq'), uuid_generate_v4(), 'Name for Fire Hose Cabinet', uuid_generate_v4(),'ACTIVATED', 'FIRE_HOSE_CABINET', '0101000020E61000005DB172D66B9A5DC0DAAD17170A084140', current_timestamp, current_timestamp);

