package com.iscweb.service.event.social

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import java.time.ZonedDateTime

class TwitterSocialEventHandlerTest extends Specification {

    private static ObjectMapper objectMapper = new ObjectMapper();

    def "test setTwitterData extractText"() {
        given:
            def data = [:]
            data.retweeted_status = [:]
            data.retweeted_status.extended_tweet = [:]
            data.retweeted_status.extended_tweet.full_text = "Look around and see"

            JsonNode json = objectMapper.valueToTree(data)

        when:
            TwitterSocialEventHandler handler = new TwitterSocialEventHandler()
            def text = handler.extractText(json)

        then:
            assert text.size() > 0
            assert text == [ data.retweeted_status.extended_tweet.full_text ]
    }

    def "test empty JSON produces null"() {
        given:
            def data = [:]
            JsonNode json = objectMapper.valueToTree(data)

        when:
            TwitterSocialEventHandler handler = new TwitterSocialEventHandler()
            List<String> text = handler.extractText(json)

        then:
            assert text.size() == 0
            assert text instanceof List<String>
    }

    def "test setTwitterData"() {
        given:
        def data = [:]
        data.user = [:]
        data.user.id = 1234
        data.user.name = "George Washington"
        data.user.screen_name = "founding-father"

        data.id_str = "abc-def-hig"
        data.postUrl = "https://..."
        data."@timestamp" = "2020-10-26T02:14:48.000Z"
        data.favorite_count = 56
        data.reply_count = 76

        JsonNode json = objectMapper.valueToTree(data)

        when:
        TwitterSocialEventHandler handler = new TwitterSocialEventHandler()
        def payload = handler.extractPayload(json)

        then:
        assert payload.getAuthorId() == data.user.id
        assert payload.getAuthorName() == data.user.name
        assert payload.getAuthorScreenName() == data.user.screen_name
        assert payload.getPostId() == data.id_str
        assert payload.getPostUrl() == data.tweet_url
        assert payload.getPostViews() == data.favorite_count
        assert payload.getPostComments() == data.reply_count
        assert payload.getEventTime() == ZonedDateTime.parse(data."@timestamp")
    }

    def "test parsing of the empty json"() {
        given:
        def data = [:]
        JsonNode json = objectMapper.valueToTree(data)

        when:
        TwitterSocialEventHandler handler = new TwitterSocialEventHandler()
        def payload = handler.extractPayload(json)

        then:
        noExceptionThrown()
    }
}
