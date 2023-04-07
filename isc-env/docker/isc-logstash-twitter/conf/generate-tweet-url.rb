require 'csv'
require_relative 'isc-logstash-utils'

# the value of `params` is the value of the hash passed to `script_params`
# in the logstash configuration
def register(params)
end

def is_valid_event(event)
   return true
end

# the filter method receives an event and must return a list of events.
# Dropping an event means not including it in the return array,
# while creating new ones only requires you to add a new instance of
# LogStash::Event to the returned array
def filter(event)
   if is_valid_event(event)
      id = event.get("[id_str]")
      quoted_status = event.get("[quoted_status][id_str]")
      retweeted_status = event.get("[retweeted_status][id_str]")
      retweeted_status_quoted_status = event.get("[retweeted_status][quoted_status][id_str]")

      event.set("tweet_url", "https://twitter.com/i/web/status/#{id}") if !id.nil?
      event.set("quoted_status_url", "https://twitter.com/i/web/status/#{quoted_status}") if !quoted_status.nil?
      event.set("retweeted_status_url", "https://twitter.com/i/web/status/#{retweeted_status}") if !retweeted_status.nil?
      event.set("retweeted_status_quoted_status_url", "https://twitter.com/i/web/status/#{retweeted_status_quoted_status}") if !retweeted_status_quoted_status.nil?

      return [event]
   else
      return [] # return empty array to cancel event
   end
end
