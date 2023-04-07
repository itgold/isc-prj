FROM west-1.amazonaws.com/logstash:7.16.3

RUN rm -f /usr/share/logstash/pipeline/logstash.conf

ADD pipeline                    /usr/share/logstash/pipeline/
ADD conf/aggregate-geo-data.rb  /usr/share/logstash/aggregate-geo-data.rb
ADD conf/parse-user-location.rb /usr/share/logstash/parse-user-location.rb
ADD conf/generate-tweet-url.rb  /usr/share/logstash/generate-tweet-url.rb
ADD conf/isc-logstash-utils.rb  /usr/share/logstash/isc-logstash-utils.rb
ADD conf/uscities.csv           /usr/share/logstash/uscities.csv
