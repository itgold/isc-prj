package com.iscweb.simulator

import com.iscweb.integration.doors.models.enums.BatteryStatus
import com.iscweb.simulator.dto.SimSaltoSyncEventDto
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class SimSaltoSyncEventDtoTest extends Specification {

    def "test batteryStatus #batteryLevel -- #batteryStatus"() {
        given:
            SimSaltoSyncEventDto dto = new SimSaltoSyncEventDto()

        when:
            dto.setBatteryLevel(batteryLevel)

        then:
            dto.getBatteryStatus() == batteryStatus

        where:
            batteryLevel | batteryStatus
            99           | BatteryStatus.NORMAL
            65           | BatteryStatus.NORMAL
            64           | BatteryStatus.LOW
            50           | BatteryStatus.LOW
            21           | BatteryStatus.LOW
            20           | BatteryStatus.VERY_LOW
            19           | BatteryStatus.VERY_LOW
            1            | BatteryStatus.VERY_LOW
    }

}
