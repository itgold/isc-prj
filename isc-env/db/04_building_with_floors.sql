DO
LANGUAGE plpgsql $$
    DECLARE
        parent_region_id int := 2;
        total_floors int := 1;
        rooms_per_floor int := 5;
    BEGIN
        INSERT INTO isc.regions (r_id, r_guid, r_name, r_type, r_status, r_geo_boundaries, r_created, r_updated)
        VALUES (NEXTVAL('isc.r_id_seq'),  uuid_generate_v4(), 'Building - A', 'BUILDING', 'ACTIVATED', '0103000020E61000000100000013000000B4930DD1609A5DC0AE308278DA0741403DFA70F55F9A5DC063422850DB07414003DFC2C95F9A5DC0BFCE92D6DA074140086FE1C45D9A5DC0F91144E1DC0741400B3520A85D9A5DC0CD5B6394DC074140637A61945D9A5DC0F5CF10A9DC074140FC64F95D5D9A5DC0032D691ADC07414058BF08725D9A5DC0D0A19005DC0741404EEF42445C9A5DC0D224C5D3D8074140BE99FB37599A5DC069043AD7DB0741406BFED7DD5A9A5DC065335C43E00741408C97F36E599A5DC0FEEB68B2E1074140647F8D75589A5DC04B47C911DF07414040838ECC569A5DC0AB04B7B1E0074140AEA4B0D9549A5DC0B8E8699CDB074140EA55B8255B9A5DC0A38E5966D50741403C36896D5A9A5DC03322F95CD30741408E4A01375D9A5DC0652E2280D0074140B4930DD1609A5DC0AE308278DA074140', current_timestamp, current_timestamp);
        INSERT INTO isc.region_region_joins (rrj_id, rrj_r_id_p, rrj_r_id_c, rrj_created, rrj_updated)
        VALUES (NEXTVAL('isc.rrj_id_seq'), parent_region_id, CURRVAL('isc.r_id_seq'), current_timestamp, current_timestamp);

        DECLARE
            i int := 0;
        BEGIN
            loop
                exit when i = total_floors ; i := i + 1 ;

                INSERT INTO isc.regions (r_id, r_guid, r_name, r_type, r_status, r_geo_boundaries, r_created, r_updated)
                VALUES (NEXTVAL('isc.r_id_seq'),  uuid_generate_v4(), 'Floor-' || i, 'FLOOR', 'ACTIVATED', '0103000020E6100000010000000F000000154910C45F9A5DC0716F9EF0DA074140121A7AAB5C9A5DC0EEB6DE0EDE074140B4A51FBC629A5DC0EE3E4AD4EE074140E88D24D3639A5DC085A56CBDED074140274F276B649A5DC04E7D0063EF0741408B25DE4C649A5DC0BC2FF781EF074140AD7E60D3659A5DC0E0552FC5F307414024D285ED659A5DC0F54D74ABF307414007151C3D669A5DC04971B287F4074140EE0A06EE669A5DC076BC51D8F30741402806469F669A5DC0702343FDF207414062B3D2D8689A5DC084FC15C7F0074140AC6F6D4D679A5DC0DC957289EC074140364A306D669A5DC09DF0E269ED074140154910C45F9A5DC0716F9EF0DA074140', current_timestamp, current_timestamp);
                INSERT INTO isc.region_region_joins (rrj_id, rrj_r_id_p, rrj_r_id_c, rrj_created, rrj_updated)
                VALUES (NEXTVAL('isc.rrj_id_seq'), (CURRVAL('isc.r_id_seq') - i ), CURRVAL('isc.r_id_seq'), current_timestamp, current_timestamp);

                INSERT INTO isc.cameras(c_id, c_guid, c_name, c_external_id, c_status, c_type, c_created, c_updated)
                VALUES (NEXTVAL('isc.c_id_seq'), uuid_generate_v4(), 'Camera-F' || i, uuid_generate_v4(), 'ACTIVATED', 'VIDEO', current_timestamp, current_timestamp);
                INSERT INTO isc.camera_region_joins (crj_id, crj_r_id, crj_c_id, crj_created, crj_updated)
                VALUES (NEXTVAL('isc.crj_id_seq'), (CURRVAL('isc.r_id_seq')), CURRVAL('isc.c_id_seq'), current_timestamp, current_timestamp);

                INSERT INTO isc.utilities(ut_id, ut_guid, ut_name, ut_external_id, ut_status, ut_type, ut_created, ut_updated)
                VALUES (NEXTVAL('isc.ut_id_seq'), uuid_generate_v4(), 'Ventilation-F' || i, uuid_generate_v4(), 'ACTIVATED', 'VENTILATION_SHUTOFF', current_timestamp, current_timestamp);
                INSERT INTO isc.utilities_region_joins (utrj_id, utrj_r_id, utrj_u_id, utrj_created, utrj_updated)
                VALUES (NEXTVAL('isc.crj_id_seq'), (CURRVAL('isc.r_id_seq')), CURRVAL('isc.ut_id_seq'), current_timestamp, current_timestamp);

                DECLARE
                    ii int := 0;
                BEGIN
                    loop
                        exit when ii = rooms_per_floor ; ii := ii + 1 ;
                        INSERT INTO isc.regions (r_id, r_guid, r_name, r_type, r_status, r_created, r_updated)
                        VALUES (NEXTVAL('isc.r_id_seq'),  uuid_generate_v4(), 'Room-' || i || '-'|| ii, 'ROOM', 'ACTIVATED', current_timestamp, current_timestamp);
                        INSERT INTO isc.region_region_joins (rrj_id, rrj_r_id_p, rrj_r_id_c, rrj_created, rrj_updated)
                        VALUES (NEXTVAL('isc.rrj_id_seq'), (CURRVAL('isc.r_id_seq') - ii ), CURRVAL('isc.r_id_seq'), current_timestamp, current_timestamp);

                        INSERT INTO isc.doors(d_id, d_guid, d_name, d_external_id, d_status, d_type, d_created, d_updated)
                        VALUES (NEXTVAL('isc.d_id_seq'), uuid_generate_v4(), 'Door-' || i || '-'|| ii, uuid_generate_v4(), 'ACTIVATED', 'INDOOR', current_timestamp, current_timestamp);
                        INSERT INTO isc.door_region_joins (drj_id, drj_r_id, drj_d_id, drj_created, drj_updated)
                        VALUES (NEXTVAL('isc.drj_id_seq'), (CURRVAL('isc.r_id_seq')), CURRVAL('isc.d_id_seq'), current_timestamp, current_timestamp);

                    end loop;
                END;
            end loop;
        END;
    END;
$$
