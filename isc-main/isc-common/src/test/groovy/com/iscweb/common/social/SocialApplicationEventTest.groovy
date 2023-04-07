package com.iscweb.common.social

import com.iscweb.common.events.social.SocialApplicationEvent
import spock.lang.Specification

class SocialApplicationEventTest extends Specification {

    def "basic SocialApplicationEvent generation"() {
        given:
            SocialApplicationEvent e = new SocialApplicationEvent()

        expect:
            assert e.getEventId() != null
            assert e.getCorrelationId() != null
    }
}
