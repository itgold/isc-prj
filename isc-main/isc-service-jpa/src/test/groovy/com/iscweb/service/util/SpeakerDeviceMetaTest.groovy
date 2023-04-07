package com.iscweb.service.util


import com.iscweb.service.util.meta.SpeakerDeviceMeta
import spock.lang.Specification

class SpeakerDeviceMetaTest extends Specification {

    def "valueOf"() {
        given:
            def speakerName
            def deviceMeta

        when: "typical speaker naming"
            speakerName = "HS-A-2-251"
            deviceMeta = new SpeakerDeviceMeta().valueOf(speakerName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "High School"
            deviceMeta.getBuilding() == "A"
            deviceMeta.getFloor() == "2"
            deviceMeta.getRoom() == "251"
            !deviceMeta.getIndex()

        when: "typical speaker naming"
            speakerName = "HS-A-2-251-#1"
            deviceMeta = new SpeakerDeviceMeta().valueOf(speakerName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "High School"
            deviceMeta.getBuilding() == "A"
            deviceMeta.getFloor() == "2"
            deviceMeta.getRoom() == "251"
            deviceMeta.getIndex() == "1"

        when: "invalid speaker name format"
            speakerName = "adsfjkldas jjldsafja"
            deviceMeta = new SpeakerDeviceMeta().valueOf(speakerName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "adsfjkldas jjldsafja"
            !deviceMeta.getBuilding()
            !deviceMeta.getFloor()
            !deviceMeta.getRoom()
            !deviceMeta.getIndex()

        when: "no index"
            speakerName = "HS-B1-BL-30"
            deviceMeta = new SpeakerDeviceMeta().valueOf(speakerName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "High School"
            deviceMeta.getBuilding() == "B1"
            deviceMeta.getFloor() == "BL"
            deviceMeta.getRoom() == "30"
            !deviceMeta.getIndex()

        when: "belongs to the floor"
            speakerName = "HS-B4-3"
            deviceMeta = new SpeakerDeviceMeta().valueOf(speakerName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "High School"
            deviceMeta.getBuilding() == "B4"
            deviceMeta.getFloor() == "3"
            !deviceMeta.getRoom()
            !deviceMeta.getIndex()

        when: "floor and index"
            speakerName = "HS-B2-2-#5"
            deviceMeta = new SpeakerDeviceMeta().valueOf(speakerName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "High School"
            deviceMeta.getBuilding() == "B2"
            deviceMeta.getFloor() == "2"
            !deviceMeta.getRoom()
            deviceMeta.getIndex() == "5"

        when: "floor and index"
            speakerName = "HS-B2-2-#44"
            deviceMeta = new SpeakerDeviceMeta().valueOf(speakerName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "High School"
            deviceMeta.getBuilding() == "B2"
            deviceMeta.getFloor() == "2"
            !deviceMeta.getRoom()
            deviceMeta.getIndex() == "44"

        when: "floor and index"
            speakerName = "HS-B1-1-132"
            deviceMeta = new SpeakerDeviceMeta().valueOf(speakerName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "High School"
            deviceMeta.getBuilding() == "B1"
            deviceMeta.getFloor() == "1"
            deviceMeta.getRoom() == "132"

        when: "external with direction"
            speakerName = "HS-B1-EXT-#33-W"
            deviceMeta = new SpeakerDeviceMeta().valueOf(speakerName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "High School"
            deviceMeta.getBuilding() == "B1"
            !deviceMeta.getFloor()
            !deviceMeta.getRoom()
            deviceMeta.getIndex() == "33"
            deviceMeta.getDirection() == "W"
        when: "hall speaker"
            speakerName = "HS-A-1-HAL-#3"
            deviceMeta = new SpeakerDeviceMeta().valueOf(speakerName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "High School"
            deviceMeta.getBuilding() == "A"
            deviceMeta.getFloor() == "1"
            !deviceMeta.getRoom()
            deviceMeta.getIndex() == "3"
            !deviceMeta.getDirection()
    }
}
