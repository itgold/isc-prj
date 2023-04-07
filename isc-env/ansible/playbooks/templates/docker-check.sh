#!/bin/bash

containers=$(docker ps -f status=running --format {%raw%}"{{.Names}}"{%endraw%})

if echo ${containers} | grep -q {{ container_name }}; then
  echo "{{ container_name }} online "
else
  echo "{{ container_name }} offline"
  exit 1
fi
