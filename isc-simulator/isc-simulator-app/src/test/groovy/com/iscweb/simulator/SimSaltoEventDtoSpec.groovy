package com.iscweb.simulator

import com.iscweb.simulator.dto.SimSaltoEventDto
import spock.lang.Specification

class SimSaltoEventDtoSpec extends Specification {

    def "test getNormalSaltoOperation input"() {

        when:
            SimSaltoEventDto simRawSaltoEvent = new SimSaltoEventDto();

        then:
            output == simRawSaltoEvent.getNormalSaltoOperation(input)

        where:
            input | output
            100   | SimSaltoEventDto.SaltoOperation.DOOR_OPENED_KEY
              0   | SimSaltoEventDto.SaltoOperation.DLO_START
    }

}
