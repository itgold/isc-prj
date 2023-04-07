#!/usr/bin/env bash

G='\033[1;92m' # Green
Y='\033[0;33m' # Yellow
NC='\033[0m'   # No Color

es_host="{{ groups['isc_elasticsearch'] | map('extract', hostvars, ['ansible_host']) | join(',') }}"
es_port="9200"
kibana_host="{{ groups['isc_kibana'] | map('extract', hostvars, ['ansible_host']) | join(',') }}"
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

echo "${Y}| Kibana:${NC} Load event dashboards"
curl -s -XPOST "http://$kibana_host:$kibana_port/s/default/api/kibana/dashboards/import" -H 'kbn-xsrf:true' -H 'Content-type:application/json' -d @dashboards/event-dashboard.json -o /dev/null
