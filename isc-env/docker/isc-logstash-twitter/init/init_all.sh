#!/usr/bin/env bash

G='\033[1;92m' # Green
Y='\033[0;33m' # Yellow
NC='\033[0m'   # No Color


es_host="isc-elasticsearch"
es_port="9200"
kibana_host="localhost"
kibana_port="5601"

waitForKibana() {
echo "${Y}|${NC} Waiting for Kibana..."
until curl -s "http://$kibana_host:$kibana_port/api/spaces/space" -o /dev/null; do
    sleep 5
    echo "${Y}|${NC} Waiting for Kibana $kibana_host:$kibana_port"
done
}

es_response=$(curl -s -w '%{http_code}' "http://$es_host:$es_port/_cat/health" -o /dev/null)
#echo $es_response

if [[ "$es_response" =~ ^0.*|^4.*|^5.* ]]; then
    echo "${Y}|${NC} Elasticsearch is down, exiting..."
    exit 1
fi

echo "${Y}| Elasticsearch:${NC} Creating mappings for twitter-log index"
curl -s -H 'Content-Type: application/json' -XPUT "http://$es_host:$es_port/twitter-log" -d @mappings/twitter-log.mapping.json -o /dev/null

kibana_response=$(curl -s -w '%{http_code}' "http://$kibana_host:$kibana_port/api/spaces/space" -o /dev/null)
#echo $kibana_response

if [[ "$kibana_response" =~ ^0.*|^4.*|^5.* ]]; then
    waitForKibana
fi

echo "${Y}| Kibana:${NC} Create twitter space"
curl -s -X POST "http://$kibana_host:$kibana_port/api/spaces/space" -H 'kbn-xsrf:true' -H 'Content-type:application/json' -d @spaces/create-space.json -o /dev/null
echo "${Y}| Kibana:${NC} Load twitter activity dashboard"
curl -s -XPOST "http://$kibana_host:$kibana_port/s/isc/api/kibana/dashboards/import" -H 'kbn-xsrf:true' -H 'Content-type:application/json' -d @dashboards/twitter-activity-dashboards.json -o /dev/null
