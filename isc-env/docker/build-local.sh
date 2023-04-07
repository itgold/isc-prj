#!/usr/bin/env bash

function usage() {
cat <<EOF
Usage: $0 <DOCKER CONTAINER TO BUILD>
EOF
}
if [ -z "$1" ]; then
 usage
 exit
fi

cd ${1} || exit
docker build -t local/${1}:latest -f Dockerfile .
