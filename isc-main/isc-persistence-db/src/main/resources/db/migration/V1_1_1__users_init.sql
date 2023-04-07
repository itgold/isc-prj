-- users
INSERT INTO isc.roles (ro_id, ro_name, ro_created, ro_updated) VALUES (NEXTVAL('isc.ro_id_seq'), 'ROLE_SYSTEM_ADMINISTRATOR', current_timestamp, current_timestamp);
INSERT INTO isc.roles (ro_id, ro_name, ro_created, ro_updated) VALUES (NEXTVAL('isc.ro_id_seq'), 'ROLE_DISTRICT_ADMINISTRATOR', current_timestamp, current_timestamp);
INSERT INTO isc.roles (ro_id, ro_name, ro_created, ro_updated) VALUES (NEXTVAL('isc.ro_id_seq'), 'ROLE_ANALYST', current_timestamp, current_timestamp);
INSERT INTO isc.roles (ro_id, ro_name, ro_created, ro_updated) VALUES (NEXTVAL('isc.ro_id_seq'), 'ROLE_GUARD', current_timestamp, current_timestamp);
INSERT INTO isc.roles (ro_id, ro_name, ro_created, ro_updated) VALUES (NEXTVAL('isc.ro_id_seq'), 'ROLE_GUEST', current_timestamp, current_timestamp);

INSERT INTO isc.users (u_id, u_email, u_password, u_status, u_status_date, u_activation_date, u_created, u_updated)
VALUES (NEXTVAL('isc.u_id_seq'), 'admin@usd.io', 'pass', 'ACTIVATED', current_timestamp, current_timestamp, current_timestamp, current_timestamp);

INSERT INTO isc.users (u_id, u_email, u_password, u_status, u_status_date, u_activation_date, u_created, u_updated)
VALUES (NEXTVAL('isc.u_id_seq'), 'district-admin@usd.io', 'pass', 'ACTIVATED', current_timestamp, current_timestamp, current_timestamp, current_timestamp);

INSERT INTO isc.users (u_id, u_email, u_password, u_status, u_status_date, u_activation_date, u_created, u_updated)
VALUES (NEXTVAL('isc.u_id_seq'), 'analyst@usd.io', 'pass', 'ACTIVATED', current_timestamp, current_timestamp, current_timestamp, current_timestamp);

INSERT INTO isc.users (u_id, u_email, u_password, u_status, u_status_date, u_activation_date, u_created, u_updated)
VALUES (NEXTVAL('isc.u_id_seq'), 'guard@usd.io', 'pass', 'ACTIVATED', current_timestamp, current_timestamp, current_timestamp, current_timestamp);

INSERT INTO isc.users (u_id, u_email, u_password, u_status, u_status_date, u_activation_date, u_created, u_updated)
VALUES (NEXTVAL('isc.u_id_seq'), 'guest@usd.io', 'pass', 'ACTIVATED', current_timestamp, current_timestamp, current_timestamp, current_timestamp);

INSERT INTO isc.user_roles_joins (urj_id, urj_u_id, urj_ro_id, urj_created, urj_updated)
VALUES (NEXTVAL('isc.urj_id_seq'), 1, 1, current_timestamp, current_timestamp);

INSERT INTO isc.user_roles_joins (urj_id, urj_u_id, urj_ro_id, urj_created, urj_updated)
VALUES (NEXTVAL('isc.urj_id_seq'), 2, 2, current_timestamp, current_timestamp);

INSERT INTO isc.user_roles_joins (urj_id, urj_u_id, urj_ro_id, urj_created, urj_updated)
VALUES (NEXTVAL('isc.urj_id_seq'), 3, 3, current_timestamp, current_timestamp);

INSERT INTO isc.user_roles_joins (urj_id, urj_u_id, urj_ro_id, urj_created, urj_updated)
VALUES (NEXTVAL('isc.urj_id_seq'), 4, 4, current_timestamp, current_timestamp);

INSERT INTO isc.user_roles_joins (urj_id, urj_u_id, urj_ro_id, urj_created, urj_updated)
VALUES (NEXTVAL('isc.urj_id_seq'), 5, 5, current_timestamp, current_timestamp);
