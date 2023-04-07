DOCKER_IMAGES=(
'python:3.9.12-alpine3.15'
'docker:20.10.14-alpine3.15'
'docker:20.10.14-dind-alpine3.15'
)

export AWS_PROFILE='iscweb'
export AWS_DEFAULT_REGION='us-west-1'
export ECR_REGISTRY='us-west-1.amazonaws.com'
aws ecr get-login-password | docker login --username AWS --password-stdin ${ECR_REGISTRY}

for img in ${DOCKER_IMAGES[@]}; do
  REPO_NAME=${img%%:*}
	aws ecr describe-repositories --repository-names ${REPO_NAME} || aws ecr create-repository --repository-name ${REPO_NAME}
	docker pull ${img}
	docker tag ${img} ${ECR_REGISTRY}/${img}
  docker tag ${img} ${ECR_REGISTRY}/${REPO_NAME}:latest
  docker push ${ECR_REGISTRY}/${img}
  docker push ${ECR_REGISTRY}/${REPO_NAME}:latest
done

REPO_NAME='isc-builder'
ISC_BUILDER_VER='1.0.0'
aws ecr describe-repositories --repository-names ${REPO_NAME} || aws ecr create-repository --repository-name ${REPO_NAME}
docker build . -t ${ECR_REGISTRY}/${REPO_NAME}:${ISC_BUILDER_VER} -t ${ECR_REGISTRY}/${REPO_NAME}:latest
docker push ${ECR_REGISTRY}/${REPO_NAME}:${ISC_BUILDER_VER}
docker push ${ECR_REGISTRY}/${REPO_NAME}:latest
