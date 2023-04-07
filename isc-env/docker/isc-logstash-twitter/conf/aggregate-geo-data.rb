require_relative 'isc-logstash-utils'

# the value of `params` is the value of the hash passed to `script_params`
# in the logstash configuration
def register(params)
   @output_field = params["output_field"]
   @geo_point_fields = params["geo_point_fields"]
   @geo_shape_fields = params["geo_shape_fields"]
end

def is_valid_event(event)
   return true
end

# https://developer.twitter.com/en/docs/tutorials/filtering-tweets-by-location
# Geographical coordinates are provided in the [LONG, LAT] order. The one exception is the deprecated  ‘geo’ attribute, which has the reverse [LAT, LONG] order.
# Note that the “coordinates” attributes is formatted as [LONGITUDE, latitude], while the “geo” attribute is formatted as [latitude, LONGITUDE].
def fix_geo_coordinates(event, field_path, field_value)
   if !field_value.nil?
      tmp = field_value[0]
      field_value[0] = field_value[1]
      field_value[1] = tmp
      event.set(field_path, field_value)
   end
   return field_value
end

# the filter method receives an event and must return a list of events.
# Dropping an event means not including it in the return array,
# while creating new ones only requires you to add a new instance of
# LogStash::Event to the returned array
def filter(event)
   if is_valid_event(event)
      tweet_coordinates = nil

      # TODO: it might be more correct to check quoted_status.place.place_type == 'city' before extract coordinates
      @geo_point_fields.each do |event_field|
         field_path = join_with_prefix(event_field.split("."), "[", "]")
         field_value = event.get(field_path)

         if (event_field.include? "geo.coordinates") && !field_value.nil?
            field_value = fix_geo_coordinates(event, field_path, field_value)
         end

         if tweet_coordinates.nil? && !field_value.nil?
            tweet_coordinates = field_value
            logger.info("tweet_coordinates source:", "value" => "#{event_field}: " + geo_point_to_str(tweet_coordinates))
         end
      end
      if tweet_coordinates.nil?
         @geo_shape_fields.each do |event_field|
            field_path = join_with_prefix(event_field.split("."), "[", "]")
            field_value = event.get(field_path)
            if tweet_coordinates.nil? && !field_value.nil?
               tweet_coordinates = extract_geo_point(field_value)
               logger.info("tweet_coordinates source:", "value" => "#{event_field}: " + geo_point_to_str(tweet_coordinates))
            end
            if !tweet_coordinates.nil?
               break
            end
         end
      end
      
      event.set("tweet_coordinates", geo_point_to_elasticsearch(tweet_coordinates)) if !tweet_coordinates.nil?
      return [event]
   else
      return [] # return empty array to cancel event
   end
end
