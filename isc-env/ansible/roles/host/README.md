Role Name
=========

Install required packages, set hostname, create user and set PS1 variable

Requirements
------------

Because molecule will run this role in docker container and hostname is defined in `molecule/default/molecule.yml` file with name `instance` you can not change it during the test and `isc_hostname` should be equal to `instance`

Role Variables
--------------

* `apt_packages` list of required packages which will be installed
* `isc_hostname` hostname
* `isc_username` username which will be created
* `isc_ps1` PS1 variable for created user

Example Playbook
----------------

Including an example of how to use your role (for instance, with variables passed in as parameters) is always nice for users too:

```yaml
- name: Provosion hosts
  hosts: all
  become: yes
  gather_facts: no

  tasks:
	- name: Include host role
	  include_role:
	    name: host
      vars:
        isc_hostname: "{{ inventory_hostname }}"
        isc_username: satoshi
```

Contribution
------------

Before commit please run `molecule test` to verify your changes.
You also can run `yamllint .` and `ansible-lint` for syntax validation (these steps included in `molecule lint` which is part of `molecule test`).

License
-------

BSD
