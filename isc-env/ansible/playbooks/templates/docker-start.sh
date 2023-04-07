#!/bin/bash

if docker network create isc-network &> /dev/null; then
  echo "(Docker network created)"
else
  echo "(Docker network exists, skipping)"
fi

ECR_REGISTRY="{{ ecr_registry }}"
aws ecr get-login-password | docker login --username AWS --password-stdin ${ECR_REGISTRY}

echo "Starting {{ container_name }}"
cd {{ isc_docker_home }}/{{ container_name }} && docker-compose pull && docker-compose up -d
