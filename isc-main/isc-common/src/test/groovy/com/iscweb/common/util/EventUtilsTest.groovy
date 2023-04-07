package com.iscweb.common.util

import com.iscweb.common.events.BaseApplicationEvent
import com.iscweb.common.model.EntityType
import com.iscweb.common.model.event.IEvent
import com.iscweb.common.model.event.camera.CameraStatusEvent
import spock.lang.Specification

class EventUtilsTest extends Specification {

    def "generatePath"() {
        given:
            String result
            String param1 = "test"
            String param2 = "apple"

        when:
            result = EventUtils.generatePath(param1)

        then:
            result
            result == "test"

        when:
            result = EventUtils.generatePath(param1, param2)

        then:
            result
            result == "apple.test"

        when:
            result = EventUtils.generatePath(param1, null, param2, null)

        then:
            result
            result == "apple.test"

//        when:
//        result = null
//        //todo fail: failed when passing null
//        result = EventUtils.generatePath(null)
//
//        then:
//        result
//        result == "apple.test"
    }

    def "eventPath"() {
        given:
            String result
            Class<? extends IEvent> baseClassEventApp = BaseApplicationEvent.class
            Class<? extends IEvent> baseClassCameraStatus = CameraStatusEvent.class
            //Class<? extends IEvent> baseClassNull = null
            EntityType deviceTypeCamera = EntityType.CAMERA
            EntityType deviceTypeSpeaker = EntityType.SPEAKER
            String eventTypeOff = "Off"
            String eventTypeOn = "On"

        when:
            result = EventUtils.eventPath(baseClassCameraStatus, deviceTypeCamera, eventTypeOff)

        then:
            result
            result == "cameraStatus.camera.Off"

        when:
            result = EventUtils.eventPath(baseClassEventApp, deviceTypeSpeaker, eventTypeOn)

        then:
            result
            result == "event.app.speaker.On"

        when:
            result = EventUtils.eventPath(baseClassCameraStatus, null, eventTypeOn)

        then:
            result
            result == "cameraStatus.*.On"

//        when:
//        //todo fail: failed when passing eventType = null
//        result = EventUtils.eventPath(baseClassEventApp, deviceTypeSpeaker, null)
//
//        then:
//        result
//        result == "event.app.speaker.null"

//        when:
//        //todo fail: should return null, not NullPointerException
//        EventUtils.eventPath(baseClassNull, deviceTypeSpeaker, eventTypeOff)
//
//        then:
//        final NullPointerException exception = thrown()
    }

    def "generateEventPath"() {
        given:
            String result
            CameraStatusEvent cameraStatusEvent = new CameraStatusEvent()
            Object object = new Object()

        when:
            result = EventUtils.generateEventPath(cameraStatusEvent)

        then:
            result
            result == "event.app.camera.cameraStatus*"

        when:
            result = EventUtils.generateEventPath(object)

        then:
            result
            result == "*"

//        when:
//        //todo fail: event.getClass() throws NullPointerException on line 47, so check on line 48 never happens
//        result = EventUtils.generateEventPath(null)
//
//        then:
//        result
//        result == "*"
    }

    def "eventPathPattern"() {
        given:
            String result
            String param1 = "\\."
            String param2 = "\\*"

        when:
            result = EventUtils.eventPathPattern(param1)

        then:
            result
            result == "\\\\."

        when:
            result = EventUtils.eventPathPattern(param2)

        then:
            result
            result == "\\.*"

        when:
            result = EventUtils.eventPathPattern("event.raw.native")

        then:
            result
            result == "event\\.raw\\.native"

//        when:
//       //todo fail: should gracefully handle null parameter
//        result = EventUtils.eventPathPattern(null)
//
//        then:
//        result
    }
}
