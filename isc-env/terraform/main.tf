terraform {
  backend "s3" {
    bucket = "iscweb-tfstate"
    key = "dev/terraform.tfstate"
    region = "us-west-1"
  }
}

provider "aws" {
  region = "us-west-1"
}

module "vpc" {
  # https://registry.terraform.io/modules/terraform-aws-modules/vpc/aws/
  source  = "terraform-aws-modules/vpc/aws"
  version = "3.13.0"

  name = "vpc-${var.environment}"
  cidr = "10.1.0.0/16"

  azs             = data.aws_availability_zones.available.names
  private_subnets = ["10.1.4.0/24"]
  public_subnets  = ["10.1.104.0/24"]

  enable_nat_gateway   = true
  single_nat_gateway   = true
  enable_dns_hostnames = true
}

module "vpc_endpoints" {
  # https://registry.terraform.io/modules/terraform-aws-modules/vpc/aws/
  source = "terraform-aws-modules/vpc/aws//modules/vpc-endpoints"
  version = "3.13.0"

  vpc_id             = module.vpc.vpc_id
  security_group_ids = [data.aws_security_group.default.id]

  endpoints = {
    s3 = {
      service         = "s3"
      service_type    = "Gateway"
      route_table_ids = flatten([module.vpc.private_route_table_ids, module.vpc.public_route_table_ids])
      tags            = { Name = "s3-endpoint" }
    }
  }
}

module "runner" {
  # https://registry.terraform.io/modules/npalm/gitlab-runner/aws/
  source  = "npalm/gitlab-runner/aws"
  version = "4.41.1"

  aws_region         = var.aws_region
  environment        = var.environment
  vpc_id             = module.vpc.vpc_id
  subnet_id          = element(module.vpc.private_subnets, 0)
  runners_name       = var.runner_name
  runners_gitlab_url = var.gitlab_url

  runners_install_amazon_ecr_credential_helper = "true"
  runner_iam_policy_arns                       = [aws_iam_policy.ecr_pull_policy.arn] # only the agent needs ECR access

  enable_runner_ssm_access         = "true"
  enable_docker_machine_ssm_access = "true"
  runners_privileged               = "true"
  runners_add_dind_volumes         = "true"

  instance_type                = "t3.micro"
  docker_machine_instance_type = "m5.large"
  docker_machine_download_url  = "https://gitlab-docker-machine-downloads.s3.amazonaws.com/v0.16.2-gitlab.12/docker-machine-Linux-x86_64"
  runners_image                = "docker:20"

  gitlab_runner_registration_config = {
    registration_token = var.registration_token
    tag_list           = "docker_spot_runner"
    description        = "runner default - auto"
    locked_to_project  = "true"
    run_untagged       = "false"
    maximum_timeout    = "3600"
  }

overrides = {
    name_sg                     = "gitlab-security-group"
    name_runner_agent_instance  = "gitlab-runner-agent"
    name_docker_machine_runners = "gitlab-runners-dm"
    name_iam_objects            = "gitlab-runner"
  }
}

################################################################################
# Supporting Resources
################################################################################

data "aws_availability_zones" "available" {
  state = "available"
}

data "aws_security_group" "default" {
  name   = "default"
  vpc_id = module.vpc.vpc_id
}

resource "aws_vpc_peering_connection" "gitlab" {
  vpc_id        = module.vpc.vpc_id
  peer_vpc_id   = var.preprod_vpc_id
  auto_accept   = true
  tags = {
    Name = "gitlab-2-pre-prod"
  }
}

resource "aws_iam_policy" "ecr_pull_policy" {
  name        = "${var.environment}-ecr"
  description = "Add access to ECR for CI/CD process"
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "ecr-public:BatchCheckLayerAvailability",
          "ecr-public:CompleteLayerUpload",
          "ecr-public:DescribeImages",
          "ecr-public:DescribeRepositories",
          "ecr-public:GetAuthorizationToken",
          "ecr-public:InitiateLayerUpload",
          "ecr-public:PutImage",
          "ecr-public:UploadLayerPart",
          "ecr:BatchCheckLayerAvailability",
          "ecr:BatchGetImage",
          "ecr:CompleteLayerUpload",
          "ecr:DescribeImages",
          "ecr:DescribeRepositories",
          "ecr:GetAuthorizationToken",
          "ecr:GetDownloadUrlForLayer",
          "ecr:InitiateLayerUpload",
          "ecr:ListImages",
          "ecr:PutImage",
          "ecr:UploadLayerPart",
        ]
        Resource = "*"
      },
    ]
  })
}
