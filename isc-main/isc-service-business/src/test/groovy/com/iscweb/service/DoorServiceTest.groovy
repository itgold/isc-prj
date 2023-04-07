package com.iscweb.service

import com.iscweb.common.model.EntityType
import com.iscweb.common.model.metadata.RegionType
import com.iscweb.persistence.model.jpa.RegionJpa
import com.iscweb.service.entity.RegionEntityService
import spock.lang.Specification

class DoorServiceTest extends Specification {

    def "resolveParentRegion"() {
        given:
            def doorName
            def parentId
            def doorDeviceProvider = new DoorService()

            def floor = new RegionJpa(
                    guid: '555',
                    name: '555',
                    type: RegionType.FLOOR
            )

            def room1 = new RegionJpa(
                    guid: '100',
                    name: '100',
                    type: RegionType.ROOM
            )
            def room2 = new RegionJpa(
                    guid: '100.1',
                    name: '100.1',
                    type: RegionType.ROOM
            )
            def room3 = new RegionJpa(
                    guid: '100A',
                    name: '100A',
                    type: RegionType.ROOM
            )
            RegionEntityService regionService = Mock(RegionEntityService.class)
            regionService.findByName(_ as String) >> Set.of(floor)
            regionService.findByName(_ as String, _ as RegionJpa) >> Set.of(floor)
            regionService.findByRegion(_ as RegionJpa) >> Set.of(room1, room2, room3)

            doorDeviceProvider.setRegionEntityService(regionService)
        when: "typical door naming"
            doorName = "HS-B1-1-100.1-SE"
            parentId = doorDeviceProvider.resolveParentRegion(EntityType.DOOR, doorName)
        then:
            parentId
            parentId == '100.1'
        when: "another typical door naming"
            doorName = "HS-B1-1-100-SE"
            parentId = doorDeviceProvider.resolveParentRegion(EntityType.DOOR, doorName)
        then:
            parentId
            parentId == '100'
        when: "one more typical door naming"
            doorName = "HS-B1-1-100A-SE"
            parentId = doorDeviceProvider.resolveParentRegion(EntityType.DOOR, doorName)
        then:
            parentId
            parentId == '100A'
        when: "one more typical door naming, no match"
            doorName = "HS-B1-1-200-SE"
            parentId = doorDeviceProvider.resolveParentRegion(EntityType.DOOR, doorName)
        then:
            parentId
            parentId == '555'
    }

    def "minPrefix"() {
        given:
            def deviceName
            def prefixLength
            def doorDeviceProvider = new DoorService()
        when: "full match"
            deviceName = "100"
            prefixLength = doorDeviceProvider.minPrefix(deviceName)
        then:
            prefixLength
            prefixLength == 3
        when: "partial match"
            deviceName = "100.1"
            prefixLength = doorDeviceProvider.minPrefix(deviceName)
        then:
            prefixLength
            prefixLength == 3
        when: "partial match with letter"
            deviceName = "10A"
            prefixLength = doorDeviceProvider.minPrefix(deviceName)
        then:
            prefixLength
            prefixLength == 2
    }

    def "greatestCommonPrefixLength"() {
        given:
            def deviceName
            def roomName
            def prefixLength
            def doorDeviceProvider = new DoorService()
        when: "full match"
            deviceName = "100"
            roomName = "100"
            prefixLength = doorDeviceProvider.greatestCommonPrefixLength(deviceName, roomName)
        then:
            prefixLength
            prefixLength == 3
        when: "partial match"
            deviceName = "100"
            roomName = "1000"
            prefixLength = doorDeviceProvider.greatestCommonPrefixLength(deviceName, roomName)
        then:
            prefixLength
            prefixLength == 3
        when: "partial match with letter"
            deviceName = "100B"
            roomName = "100A"
            prefixLength = doorDeviceProvider.greatestCommonPrefixLength(deviceName, roomName)
        then:
            prefixLength
            prefixLength == 3
    }
}
