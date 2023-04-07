#!/usr/bin/env bash
G='\033[1;92m' # Green
Y='\033[0;33m' # Yellow
NC='\033[0m'   # No Color

config_files=('config' 'profile')
FORCE=0

usage() {
  printf "${Y}| ${0} is an awesome tool!${NC}\n"
  printf "${Y}| -f, --force${NC} Clear old logs and data\n"
  printf "${Y}| -h, --help${NC} Displays this help.${NC}\n"
  exit
}

for arg; do
  delim=""
  case "$arg" in
  --help) args="${args}-h " ;;
  --force) args="${args}-f " ;;
  # pass through anything else
  *)
    [[ "${arg:0:1}" == "-" ]] || delim="\""
    args="${args}${delim}${arg}${delim} "
    ;;
  esac
done
# reset the translated args
eval set -- $args
# now we can process with getopt
while getopts ":hf" opt; do
  case $opt in
  h) usage ;;
  f) FORCE=1 ;;
  \?) exit ;;
  :)
    printf "${Y}| Error:${NC} Option ${Y}-$OPTARG${NC} requires an argument${NC}\n"
    exit
    ;;
  esac
done

load_configurations() {
  for config_file in "${config_files[@]}"; do
    . "./$config_file.env"
    echo -e "${Y}| Using default${NC} ./$config_file.env"
    echo -e "${Y}|${NC} Override with ${Y}$config_file.override.env${NC} file"
    if [ -f "./$config_file.override.env" ]; then
      . "./$config_file.override.env"
      echo -e "${G}| Using override file${NC} ./$config_file.override.env"
    fi
  done
  data_dirs=($(printenv | grep -i isc_ | grep -i _home | grep -iv shared))
  shared_dirs=($(printenv | grep -i isc_ | grep -i _share | grep -iv _home))
}

clear_previous() {
  docker kill `docker ps -q`
  docker system prune -af

  to_be_deleted=( "${data_dirs[@]}" "${shared_dirs[@]}" )

  for data_dir in "${to_be_deleted[@]}"; do
    IFS='=' read -ra tmp <<< "$data_dir"
    directory_path=${tmp[1]}
    rm -rf $directory_path
    echo -e "${Y}| Removing directories${NC} $directory_path"
  done
}

create_docker_network() {
  if docker network create isc-network &> /dev/null; then
    echo "(isc-network network created)"
  else
    echo "(isc-network network exists, skipping)"
  fi
}

pull_provision_lunch() {
pwd=`pwd`
for deployment_spec in "${deployment_specs[@]}"; do
  IFS=':' read -ra tmp <<< "$deployment_spec"
  container_name=${tmp[0]}
  container_version=${tmp[1]}
  echo -e "${Y}|${NC} Loading ${Y}$container_name:$container_version${NC}"
  for data_dir in "${data_dirs[@]}"; do
    IFS='=' read -ra tmp <<< "$data_dir"
     directory_path=${tmp[1]}
     mkdir -p "$directory_path/$container_name"
  done
  for shared_dir in "${shared_dirs[@]}"; do
    IFS='=' read -ra tmp <<< "$shared_dir"
     directory_path=${tmp[1]}
     mkdir -p "$directory_path"
  done

  cd "$container_name" || echo "| Error: $container_name does not exist"
  if [ -d "init" ]; then
    cd init || exit
    init_scripts=($(ls | grep .sh$))
    for init_script in "${init_scripts[@]}"; do
      sh $init_script
    done
    cd ..
  fi
  # docker-compose pull
  docker-compose up -d
  cd $pwd
done
}

ecr_login() {
  ECR_REGISTRY="us-west-1.amazonaws.com"
  aws ecr get-login-password | docker login --username AWS --password-stdin ${ECR_REGISTRY}
}

#------------------------------------
# MAIN
#------------------------------------
load_configurations
ecr_login

if [ $FORCE -eq 1 ]; then
  clear_previous
fi

create_docker_network
pull_provision_lunch
