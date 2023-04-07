#!/bin/bash

container="{{ container_name }}"
containerId="$(docker ps -a --format {%raw%}"{{.ID}}"{%endraw%} --filter name=${container})"

if [ -z "${containerId}" ]; then
  echo "The container '${container}' is not running"
else
  echo "Stopping container '${container}' with ID: '${containerId}'"
  docker stop ${containerId}
  docker rm ${containerId}
fi
