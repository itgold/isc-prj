FROM west-1.amazonaws.com/python:3.9.12-alpine3.15 as builder

ARG PIPENV_VENV_IN_PROJECT=1

# to update Pipfile.lock file
# docker run -ti -v $(pwd):/update python:3.9.12-alpine3.15 sh -c "pip install pipenv; cd /update; pipenv lock"
COPY Pipfile Pipfile.lock .

RUN apk add --update gcc libffi-dev musl-dev \
	&& pip install pipenv \
	&& pipenv install --deploy

FROM west-1.amazonaws.com/python:3.9.12-alpine3.15 as runtime

RUN apk add --update --no-cache git openssh openvpn

COPY --from=builder /.venv /.venv
ENV PATH="/.venv/bin:${PATH}"
