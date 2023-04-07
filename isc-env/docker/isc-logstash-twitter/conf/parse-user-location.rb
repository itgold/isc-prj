require 'csv'
require_relative 'isc-logstash-utils'

class State
   def initialize(code, name, abbr)
      @state_code = code
      @state_name = name
      @state_abbr = abbr
   end

   def name
      @state_name
   end
   def abbr
      @state_abbr
   end
   def code
      @state_code
   end
end

class StateCitiesGeo
   def initialize(state, city, geo_point)
      @geo_state = state
      @geo_city = city
      @geo_point = geo_point
   end

   def state
      @geo_state
   end
   def city
      @geo_city
   end
   def geo_point 
      @geo_point
   end
end

# TODO: https://simplemaps.com/data/us-cities
STATES_GEO_MAP = {}
STATES_BY_ABBR = {}
STATES_BY_NAME = {}
STATES_BY_CODE = {
   "AL" => State.new("AL", "Alabama", "Ala."),
   "AK" => State.new("AK", "Alaska", "Alaska"),
   "AZ" => State.new("AZ", "Arizona", "Ariz."),
   "AR" => State.new("AZ", "Arkansas", "Ark."),
   "CA" => State.new("CA", "California", "Calif."),
   "CO" => State.new("CO", "Colorado", "Colo."),
   "CT" => State.new("CT", "Connecticut", "Conn."),
   "DE" => State.new("DE", "Delaware", "Del."),
   "DC" => State.new("DC", "District of Columbia", "D.C."),
   "FL" => State.new("FL", "Florida", "Fla."),
   "GA" => State.new("GA", "Georgia", "Ga."),
   "HI" => State.new("HI", "Hawaii", "Hawaii"),
   "ID" => State.new("ID", "Idaho", "Idaho"),
   "IL" => State.new("IL", "Illinois", "Ill."),
   "IN" => State.new("IN", "Indiana", "Ind."),
   "IA" => State.new("IA", "Iowa", "Iowa"),
   "KS" => State.new("KS", "Kansas", "Kans."),
   "KY" => State.new("KY", "Kentucky", "Ky."),
   "LA" => State.new("LA", "Louisiana", "La."),
   "ME" => State.new("ME", "Maine", "Maine"),
   "MD" => State.new("MD", "Maryland", "Md."),
   "MA" => State.new("MD", "Massachusetts", "Mass."),
   "MI" => State.new("MI", "Michigan", "Mich."),
   "MN" => State.new("MN", "Minnesota", "Minn."),
   "MS" => State.new("MS", "Mississippi", "Miss."),
   "MO" => State.new("MO", "Missouri", "Mo."),
   "MT" => State.new("MT", "Montana", "Mont."),
   "NE" => State.new("NE", "Nebraska", "Nebr."),
   "NV" => State.new("NV", "Nevada", "Nev."),
   "NH" => State.new("NH", "New Hampshire", "N.H."),
   "NJ" => State.new("NJ", "New Jersey", "N.J."),
   "NM" => State.new("NM", "New Mexico", "N.M."),
   "NY" => State.new("NY", "New York", "N.Y."),
   "NC" => State.new("NC", "North Carolina", "N.C."),
   "ND" => State.new("ND", "North Dakota", "N.D."),
   "OH" => State.new("OH", "Ohio", "Ohio"),
   "OK" => State.new("OK", "Oklahoma", "Okla."),
   "OR" => State.new("OR", "Oregon", "Ore."),
   "PA" => State.new("PA", "Pennsylvania", "Pa."),
   "RI" => State.new("RI", "Rhode Island", "R.I."),
   "SC" => State.new("SC", "South Carolina", "S.C."),
   "SD" => State.new("SD", "South Dakota", "S.D."),
   "TN" => State.new("TN", "Tennessee", "Tenn."),
   "TX" => State.new("TX", "Texas", "Tex."),
   "UT" => State.new("UT", "Utah", "Utah"),
   "VT" => State.new("VT", "Vermont", "Vt."),
   "VA" => State.new("VA", "Virginia", "Va."),
   "WA" => State.new("WA", "Washington", "Wash."),
   "WV" => State.new("WV", "West Virginia", "W.Va."),
   "WI" => State.new("WI", "Wisconsin", "Wis."),
   "WY" => State.new("WY", "Wyoming", "Wyo.")
}

def initialize_states_dictionary()
   STATES_BY_CODE.each do |key, value|
      STATES_BY_ABBR[value.abbr] = value
      STATES_BY_NAME[value.name] = value
   end
end

def initialize_geo_resolver()
   CSV.foreach("/opt/logstash/uscities.csv") do |row|
      state_dict = nil
      state = row[2]
      if STATES_GEO_MAP.key?(state)
         state_dict = STATES_GEO_MAP[state]
      else
         state_dict = {}
      end

      # https://www.elastic.co/guide/en/elasticsearch/reference/current/geo-point.html
      # Geo-point expressed as an array with the format: [ lon, lat]
      # lat -90..90, lon -180..180
      state_dict[row[0]] = StateCitiesGeo.new(state, row[0], [row[9].to_f, row[8].to_f])
      STATES_GEO_MAP[state] = state_dict
   end
end

# the value of `params` is the value of the hash passed to `script_params`
# in the logstash configuration
def register(params)
   @user_location_fields = params["user_location_fields"]

   initialize_states_dictionary()
   initialize_geo_resolver()
end

def is_valid_event(event)
   return true
end

def find_if_state(text)
   user_state = nil
   if STATES_BY_CODE.key?(text)
      user_state = STATES_BY_CODE[text].code
   end
   if user_state.nil? && STATES_BY_NAME.key?(text)
      user_state = STATES_BY_NAME[text].code
   end
   if user_state.nil? && STATES_BY_ABBR.key?(text)
      user_state = STATES_BY_ABBR[text].code
   end

   return user_state
end

# the filter method receives an event and must return a list of events.
# Dropping an event means not including it in the return array,
# while creating new ones only requires you to add a new instance of
# LogStash::Event to the returned array
def filter(event)
   if is_valid_event(event)
      user_state = nil
      user_city = nil
      @user_location_fields.each do |event_field|
         field_path = join_with_prefix(event_field.split("."), "[", "]")
         field_value = event.get(field_path)
         if !field_value.nil?
            # 1. split by space and then remove all punctuations from all parts
            # 2. check against dictioanary of the states
            # 3. if state found, check the position. If the state is the second position, there is big chance the first is city
            # 4. if we have both state and city -> can try to get coordinates:
            # https://www.latlong.net/convert-address-to-lat-long.html
            # https://www.latlong.net/
            # https://gps-coordinates.org/ 
            parts = field_value.split(/[\s,]+/)
            if !parts.nil? && parts.count > 0
               last_part = parts[parts.count - 1]
               user_state = find_if_state(last_part)
               if !user_state.nil?
                  if parts.count > 1
                     user_city = ""
                     idx = 0
                     parts.each do |part|
                        if user_city.length > 0
                           user_city += " "
                        end
                        user_city += part
                        idx += 1
                        if idx >= parts.count - 1
                           break
                        end
                     end
                  end
               else
                  parts.each do |part|
                     user_state = find_if_state(part)
                     if !user_state.nil?
                        break
                     end
                  end
               end
            end

            # if !user_state.nil?
            #    logger.info("parsing user location:", "value" => "#{field_value}, State: #{user_state}, Place: #{user_city}")
            # end
            event.set("user_state", user_state) if !user_state.nil?
            event.set("user_address", user_city) if !user_city.nil?
            
            tweet_coordinates = event.get("tweet_coordinates")
            if tweet_coordinates.nil? && !user_state.nil? && !user_city.nil? && STATES_GEO_MAP.key?(user_state)
               city_dict = STATES_GEO_MAP[user_state]
               if city_dict.key?(user_city)
                  tweet_coordinates = city_dict[user_city].geo_point
               end

               if !tweet_coordinates.nil?
                  tweet_coordinates = geo_point_to_elasticsearch(tweet_coordinates)
                  event.set("tweet_coordinates", tweet_coordinates)
                  logger.info("user.location geo:", "value" => "State: '#{user_state}', City: '#{user_city}', geo: " + geo_point_to_str(tweet_coordinates))
               end
            end
         end
      end

      return [event]
   else
      return [] # return empty array to cancel event
   end
end
