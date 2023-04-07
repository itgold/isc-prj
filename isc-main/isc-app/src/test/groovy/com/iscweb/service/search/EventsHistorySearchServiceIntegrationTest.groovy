package com.iscweb.service.search

import com.iscweb.app.ControlCenter
import com.iscweb.common.model.event.camera.CameraEventPayload
import com.iscweb.common.model.event.camera.CameraStatusEvent
import com.iscweb.common.security.ApplicationSecurity
import com.iscweb.common.security.ISystemUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import spock.lang.Ignore
import spock.lang.Specification

@SpringBootTest(classes = ControlCenter)
@Ignore
class EventsHistorySearchServiceIntegrationTest extends Specification {

    @Autowired
    private EventsHistorySearchService eventsHistorySearchService

    private ISystemUser testUser

    def setup() {
        testUser = ApplicationSecurity.systemUserLogin()
    }

    def cleanup() {
        testUser.close()
    }

    def "create and search in Elastic Search"() {
        when:
            CameraStatusEvent event1 = new CameraStatusEvent("TEST1")
            event1.setPayload(new CameraEventPayload("TEST1", null, "testType1", System.currentTimeMillis(), Collections.emptyList()))
            eventsHistorySearchService.recordApplicationEvent(event1)

            CameraStatusEvent event2 = new CameraStatusEvent("TEST2")
            event2.setPayload(new CameraEventPayload("TEST2", null, "testType2", System.currentTimeMillis(), Collections.emptyList()))
            event2.setCorrelationId("TestCorrelationId")
            eventsHistorySearchService.recordApplicationEvent(event2)

            List<CameraStatusEvent> rez = eventsHistorySearchService.findAll(PageRequest.of(0, 20))
            List<CameraStatusEvent> rez2 = eventsHistorySearchService.findAllByCorrelationId("TestCorrelationId", PageRequest.of(0, 20))

        then:
            rez.size() > 0
            rez2.size() > 0
    }
}