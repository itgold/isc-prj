variable "aws_region" {
  description = "AWS region."
  type        = string
  default     = "us-west-1"
}

variable "preprod_vpc_id" {
  description = "pre-prod VPC to make peering connection."
  type        = string
  default     = "vpc-0c6634ac6613e1ce3"
}

variable "environment" {
  description = "A name that identifies the environment, will used as prefix and for tagging."
  type        = string
  default     = "runners-default"
}

variable "runner_name" {
  description = "Name of the runner, will be used in the runner config.toml"
  type        = string
  default     = "default-auto"
}

variable "gitlab_url" {
  description = "URL of the gitlab instance to connect to."
  type        = string
  default     = "https://gitlab.com"
}

variable "registration_token" {
  type        = string
  default     = "GR13489413bvxar8NMx8fuCt7WiK4"
}

variable "timezone" {
  description = "Name of the timezone that the runner will be used in."
  type        = string
  default     = "America/Los_Angeles"
}
