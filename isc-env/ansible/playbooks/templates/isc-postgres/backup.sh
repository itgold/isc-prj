#!/bin/bash

. "{{ isc_docker_home }}/{{ container_name }}/.env"
BACKUP_DATE=$(date +"%Y-%m-%d_%H_%M_%S")

docker exec -t ${container_name} pg_dumpall -c -U ${POSTGRES_USER} \
	| gzip > "${ISC_BACKUP_HOME}/${container_name}/dump_${BACKUP_DATE}.gz"
