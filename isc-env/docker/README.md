<b>To build local container</b>

```build-local.sh isc-app```

<b>To start a new container.</b>

You should have `docker-compose.override.yaml` file in the container's directory 
with the environment specific properties initialized.

Run from the container's directory.

```docker-compose up -d```

<b>Misc</b>

`config.override.env` - overrides environment variables defined by `config.env`
`profile.override.env` - overrides containers built and executed by `run-all.sh` and defaulted to `profile.env` 

Detailed instructions:
https://docs.google.com/document/d/197XiH4zyg4EF3EGKDc49Mct1euFralxOIKxrw4kPTb4
