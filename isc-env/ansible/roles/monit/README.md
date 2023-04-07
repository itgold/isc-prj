Role Name
=========

Install monit with configuration.

Requirements
------------

Because molecule will run this role in docker container and hostname is defined in `molecule/default/molecule.yml` file with name `instance` you can not change it during the test and `isc_hostname` should be equal to `instance`

Role Variables
--------------

* `monit_cycle`: Time between checks in seconds. Defaults to `60`.
* `monit_log_destination`: Where the log will be written. Can be a path to a file or "syslog", which will write to syslog daemon. Defaults to `/var/log/monit.log`.
* `monit_state_file`: State file path. Defaults to `/var/lib/monit/state`.
* `monit_id_file`: Id file path. Defaults to `/var/lib/monit/id`.
* `monit_eventqueue_dir`: Event queue directory path. It is only used when this variable is defined. Defaults to `/var/lib/monit/events`.
* `monit_eventqueue_slots`: Event queue slots. It is only used when `monit_eventqueue_dir` is defined. Defaults to `100`.
* `monit_mail_enabled`: Enable mail alerts. Defaults to `false`.
* `monit_mailserver_host`: Mailserver host address. Defaults to `localhost`.
* `monit_mailserver_port`: Mailserver host port. Defaults to `25`.
* `monit_mailserver_user`: Username for authentication on mailserver. Optional
* `monit_mailserver_password`: Password for authentication on mailserver. Optional
* `monit_mailserver_timeout`: Timeout for mailserver connection. Defaults to `5`.
* `monit_mailserver_ssl_version`: If defined, monit will use this algorithm for SSL connection to the mail server. Possible values are `SSLAUTO`, `SSLV2`, `SSLV3`, `TLSV1`, `TLSV11`, `TLSV12`.
* `monit_alert_addresses`: List of mail addresses where the alerts will be sent to.
* `monit_alert_mail_format`: A hash of options for mail-format.
  * `from`: Sender mail address.
  * `reply-to`: A reply-to mail address.
  * `subject`: Mail subject.
  * `message`: Mail message body.
* `monit_httpd_enabled`: Enable monit web interface. Defaults to `true`.
* `monit_httpd_bind`: IP address to bind web interface. Defaults to `0.0.0.0` (listen for external requests).
* `monit_httpd_port`: Port for web interface. Defaults to `2812`.
* `monit_httpd_rw_group`: Define group of users allowed to read and write on web interface. It is only applied when defined and is empty by default.
* `monit_httpd_r_group`: Define group of users allowed to read on web interface. It is only applied when defined and is empty by default.
* `monit_httpd_acl_rules`: List of ACL rules for the web interface, such as "localhost" or "hauk:password". It is only applied when defined and is empty by default. You should probably define at least one for the httpd service to start.
* `monit_monitors`: List of hashes of services to be monitorized by monit.
  * `name`: Name of the process or host.
  * `type`: Type of monitorization. Following types are supported:
  	* process
  	* process_by_name
  	* file
  	* fifo
  	* filesystem
  	* directory
  	* host
  	* system
  	* program
  	* network
  	* network_by_interface
  * `target`: Target of monitorization. Should be a pidfile, processname, an address or undefined, depending on the `type` of service.
  * `timeout`: Timeout for service. Optional.
  * `start`: Command that starts the service. Optional.
  * `stop`: Command that stop the service. Optional.
  * `restart`: Command that restart the service. Optional.
  * `user`: Linux username of the user starting the program. Optional.
  * `group`: Linux group of the user starting the program. Optional.
  * `rules`: List of rules to be included in this service. Optional.
* `monit_monitors_disable_unused`: Remove existing service monitorization configurations not declared in the `monit_monitors`. Defaults to `false`.

Example Playbook
----------------

Including an example of how to use your role (for instance, with variables passed in as parameters) is always nice for users too:

```yaml
- name: Provosion hosts
  hosts: all
  become: yes

  tasks:
	- name: Include monit role
	  include_role:
	    name: monit
      vars:
		monit_httpd_acl_rules:
		  - 'admin:password'
		monit_mail_enabled: true
		monit_mailserver_host: smtp.gmail.com
		monit_mailserver_port: 587
		monit_mailserver_user: isc.io.notifications@gmail.com
		monit_mailserver_password: 'password'
		monit_mailserver_ssl_version: tlsv1
		monit_mailserver_timeout: 30
		monit_alert_addresses:
		  - engineering@iscweb.io
		monit_monitors:
		  - name: "{{ container_name }}"
		    type: program
		    timeout: 60
		    target: "{{ monit_scripts_dir }}/{{ container_name }}/docker-check.sh"
		    start: "{{ monit_scripts_dir }}/{{ container_name }}/docker-start.sh"
		    stop: "{{ monit_scripts_dir }}/{{ container_name }}/docker-stop.sh"
		    rules:
		      - if status = 1 for 8 times within 10 cycles then alert
		      - if status = 1 for 8 times within 10 cycles then exec "{{ monit_scripts_dir }}/{{ container_name }}/docker-start.sh"
```

Contribution
------------

Before commit please run `molecule test` to verify your changes.
You also can run `yamllint .` and `ansible-lint` for syntax validation (these steps included in `molecule lint` which is part of `molecule test`).

License
-------

BSD
