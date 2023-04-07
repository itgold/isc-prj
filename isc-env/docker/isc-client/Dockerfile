FROM west-1.amazonaws.com/nginx:1.20.2

WORKDIR /etc/nginx

COPY conf/common-security.conf .
COPY conf/common-headers.conf .
COPY conf/default.conf .
COPY conf/dhparam.pem /etc/ssl/certs/dhparam.pem

COPY artifacts/index.html artifacts/build* /usr/share/nginx/html/

COPY cert/ssl-cert.crt /etc/ssl/certs/ssl-cert.crt
COPY cert/ssl-cert.key /etc/ssl/private/ssl-cert.key

ENTRYPOINT ["/bin/bash",  "-c",  "envsubst '$${APP_SERVER_HOST} $${APP_SERVER_PORT} $${KIBANA_HOST} $${KIBANA_PORT} $${ES_HOST} $${ES_PORT}' < /etc/nginx/default.conf > /etc/nginx/conf.d/default.conf && exec nginx -g 'daemon off;'"]
