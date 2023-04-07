def fix_longitude(longitude)
    # fl = longitude / 360.0
    # rez = longitude - 360.0 * fl.floor(8)
    # if rez.abs() > 180.0
    #    rez = 360.0 - rez
    # end
 
    # return rez
    return longitude
end

def geo_point_to_elasticsearch(tweet_coordinates)
    # if !tweet_coordinates.nil?
    #     if tweet_coordinates[1] > 90.0 || tweet_coordinates[1] < -90.0
    #        logger.info("tweet_coordinates fix lat:", "value" => tweet_coordinates[0])
    #        tweet_coordinates[1] = fix_longitude(tweet_coordinates[1])
    #     end
    #     if tweet_coordinates[0] > 180.0 || tweet_coordinates[0] < -180.0
    #        logger.info("tweet_coordinates fix long:", "value" => tweet_coordinates[0])
    #        tweet_coordinates[0] = fix_longitude(tweet_coordinates[0])
    #     end
    # end

    return tweet_coordinates
end

def extract_geo_point(geo_shape)
    if geo_shape[0].count > 2
       return geo_shape[0][0]
    else
       return geo_shape[0]
    end
end

def join_with_prefix(array, prefix, separator)
    arrayString = ''
    array.each{|x| arrayString += prefix + x + separator}
    return arrayString
end

def geo_point_to_str(geo_point)
    rez_as_str = "[EMPTY, EMPTY]"
    if !geo_point.nil?
        rez_as_str = sprintf("[Lat: %.6f, Lon: %.6f]", geo_point[1], geo_point[0])
    end

    return rez_as_str
end