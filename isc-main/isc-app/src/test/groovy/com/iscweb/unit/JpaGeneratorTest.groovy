package com.iscweb.unit

import com.iscweb.persistence.JpaGenerator
import com.iscweb.persistence.model.jpa.*
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.CoordinateXY
import org.springframework.util.CollectionUtils
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.*

class JpaGeneratorTest extends Specification {

    def "test randomRegion generation"() {
        when:
            RegionJpa region = JpaGenerator.randomRegion()

        then:
            assertNotNull(region.name)
            assertNotNull(region.geoBoundaries)
            assertNotNull(region.status)
            assertNotNull(region.guid)
            assertNotNull(region.created)
            assertNotNull(region.updated)
            assertNotNull(region.type)

            assertNull(region.id)
            assertTrue(CollectionUtils.isEmpty(region.regions))
            assertNull(region.geoLocation)
    }

    def "test randomCoords generation"() {
        when:
            Coordinate coords = JpaGenerator.randomCoords()

        then:
            assertTrue(coords instanceof CoordinateXY)
    }

    def "test randomAlert generation"(){
        when:
            AlertJpa alert = JpaGenerator.randomAlert()

        then:
            assertNotNull(alert.deviceId)
            assertNotNull(alert.deviceType)
            assertNotNull(alert.severity)
            assertNotNull(alert.created)
            assertNotNull(alert.updated)
            assertNotNull(alert.guid)

            assertNull(alert.id)
    }

    def "test randomCamera generation" () {
        when:
            CameraJpa camera = JpaGenerator.randomCamera()

        then:
            assertNotNull(camera.name)
            assertNotNull(camera.externalId)
            assertNotNull(camera.status)
            assertNotNull(camera.type)
            assertNotNull(camera.regions)

            assertNull(camera.id)
    }

    def "test randomCameraTag generation" () {
        when:
            CameraTagJpa cameraTag = JpaGenerator.randomCameraTag()

        then:
            assertNotNull(cameraTag.entity)
            assertNotNull(cameraTag.tag)
            assertNotNull(cameraTag.guid)
            assertNotNull(cameraTag.created)
            assertNotNull(cameraTag.updated)

            assertNull(cameraTag.type)
            assertNull(cameraTag.id)
    }

    def "test randomSpeaker generation" () {
        when:
            SpeakerJpa speaker = JpaGenerator.randomSpeaker()

        then:
            assertNotNull(speaker.name)
            assertNotNull(speaker.externalId)
            assertNotNull(speaker.status)
            assertNotNull(speaker.type)
            assertNotNull(speaker.regions)

            assertNull(speaker.id)
    }

    def "test randomSpeakerTag generation" () {
        when:
            SpeakerTagJpa speakerTag = JpaGenerator.randomSpeakerTag()

        then:
            assertNotNull(speakerTag.entity)

            assertNotNull(speakerTag.tag)
            assertNotNull(speakerTag.guid)
            assertNotNull(speakerTag.created)
            assertNotNull(speakerTag.updated)

            assertNull(speakerTag.type)
            assertNull(speakerTag.id)
    }

    def "test randomDoor generation" () {
        when:
            DoorJpa door = JpaGenerator.randomDoor()

        then:
            assertNotNull(door.name)
            assertNotNull(door.externalId)
            assertNotNull(door.status)
            assertNotNull(door.type)
            assertNotNull(door.geoLocation)
            assertNotNull(door.regions)
            assertNotNull(door.status)
            assertNotNull(door.connectionStatus)
            assertNotNull(door.onlineStatus)
            assertNotNull(door.tamperStatus)
            assertNotNull(door.batteryStatus)
            assertNotNull(door.batteryLevel)
            assertNotNull(door.openingMode)

            assertNull(door.id)
    }
}
