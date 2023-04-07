package com.iscweb.service.util


import com.iscweb.service.util.meta.DoorDeviceMeta
import spock.lang.Specification

class DoorDeviceMetaTest extends Specification {

    def "valueOf"() {
        given:
            def doorName
            def deviceMeta
        when: "typical door naming"
            doorName = "HS-B1-1-100.1-SE"
            deviceMeta = new DoorDeviceMeta().valueOf(doorName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "High School"
            deviceMeta.getBuilding() == "B1"
            deviceMeta.getRoom() == "100.1"
            deviceMeta.getDirection() == "SE"
        when: "invalid door name format"
            doorName = "dfj;ajflkd;ajflkd"
            deviceMeta = new DoorDeviceMeta().valueOf(doorName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "dfj;ajflkd;ajflkd"
            !deviceMeta.getBuilding()
            !deviceMeta.getFloor()
            !deviceMeta.getRoom()
            !deviceMeta.getDirection()
        when: "the door is on the basement level"
            doorName = "HS-B2-BL-125A-W"
            deviceMeta = new DoorDeviceMeta().valueOf(doorName)
        then:
            deviceMeta
            deviceMeta.getDistrict() == "Unified School District"
            deviceMeta.getSchool() == "High School"
            deviceMeta.getBuilding() == "B2"
            deviceMeta.getFloor() == "BL"
            deviceMeta.getRoom() == "125A"
            deviceMeta.getDirection() == "W"
    }
}
