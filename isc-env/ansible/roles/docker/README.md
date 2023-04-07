Role Name
=========

Install Docker CE edition and docker-compose.

Requirements
------------

This role was tested only on Debian/Ubuntu OS. Because I'm using variable `ansible_distribution` it require `gather_facts: yes` in playbook.

Role Variables
--------------

* `docker_ce_requirements` list of required packages which will be installed before docker
* `docker_ce_packages` list of docker-ce packages and dependencies
* `docker_pip_packages` list of python packages required for ansible modeules to interact with docker (like [docker_container](https://docs.ansible.com/ansible/2.10/collections/community/docker/docker_container_module.html#ansible-collections-community-docker-docker-container-module), [docker_image](https://docs.ansible.com/ansible/2.10/collections/community/docker/docker_image_module.html#ansible-collections-community-docker-docker-image-module), [docker_compose](https://docs.ansible.com/ansible/2.10/collections/community/docker/docker_compose_module.html#ansible-collections-community-docker-docker-compose-module))
* `docker_ce_apt_key_url` URL of GPG key for docker repo
* `docker_ce_apt_repository_repo` docker repo string
* `docker_ce_service` docker service name
* `docker_install_compose` trigger to install docker-compose (true by default)
* `docker_compose_version` version of docker-compose package (1.28.4 by default)
* `docker_compose_path` path where to put docker-compose package
* `docker_users` list of users to include in docker linux group

Example Playbook
----------------

Including an example of how to use your role (for instance, with variables passed in as parameters) is always nice for users too:

```yaml
- name: Provosion hosts
  hosts: all
  become: yes
  gather_facts: yes

  tasks:
	- name: Include docker role
	  include_role:
	    name: docker
      vars:
      	docker_users:
		  - satoshi
```

Contribution
------------

Before commit please run `molecule test` to verify your changes.
You also can run `yamllint .` and `ansible-lint` for syntax validation (these steps included in `molecule lint` which is part of `molecule test`).

License
-------

BSD
