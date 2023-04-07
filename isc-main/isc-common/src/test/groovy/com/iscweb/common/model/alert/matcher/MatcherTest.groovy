package com.iscweb.common.model.alert.matcher

import com.fasterxml.jackson.databind.ObjectMapper
import com.iscweb.common.events.alerts.AlertMatchingContext
import com.iscweb.common.events.alerts.CustomAlertTriggerMatcher
import com.iscweb.common.events.alerts.DateTimeAlertTriggerMatcher
import com.iscweb.common.model.dto.entity.core.CameraDto
import com.iscweb.common.model.dto.entity.core.CameraGroupDto
import com.iscweb.common.model.dto.entity.core.DoorDto
import com.iscweb.common.model.metadata.DoorStatus
import com.iscweb.common.util.ObjectMapperUtility
import spock.lang.Specification

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MatcherTest extends Specification {

    private static final String FULL_MATCHER = """
     {
        "and":
        {
            "or":
            {
                "items":
                [
                    {
                        "property": "property3",
                        "operator": ">=",
                        "value": "value3"
                    },
                    {
                        "property": "property4",
                        "operator": ">",
                        "value": "value4"
                    }
                ]
            },
            "items":
            [
                {
                    "property": "property1",
                    "operator": "==",
                    "value": "value1"
                },
                {
                    "property": "property2",
                    "operator": "<=",
                    "value": "value2"
                }
            ]
        }
    }
    """

    private static final String DATE_TIME_MATCHER = """
     {
        "and":
        {
            "items":
            [
                {
                    "property": "dateTime.date",
                    "operator": ">",
                    "value": "2022-05-01"
                },
                {
                    "property": "dateTime.date",
                    "operator": "<",
                    "value": "2022-05-30"
                },
                {
                    "property": "dateTime.time",
                    "operator": ">",
                    "value": "10:00:00"
                },
                {
                    "property": "dateTime.time",
                    "operator": "<",
                    "value": "15:30:00"
                }
            ]
        }
    }
    """

    private static final String IN_MATCHER = """
     {
        "or":
        {
            "items":
            [
                {
                    "property": "deviceId",
                    "operator": "in",
                    "value": "100,101,102,103"
                },
                {
                    "property": "deviceId",
                    "operator": "in",
                    "value": "200,201,202,203"
                }
            ]
        }
    }
    """

    private static final String DEVICE_MATCHER = """
     {
        "and":
        {
            "items":
            [
                {
                    "property": "device.entityType",
                    "operator": "==",
                    "value": "CAMERA"
                },
                {
                    "property": "device.cameraGroup.name",
                    "operator": "==",
                    "value": "Very Special Group"
                }
            ]
        }
    }
    """

    def "parse matcher rule"() {
        given:
            ObjectMapper objectMapper = new ObjectMapper()
            ObjectMapperUtility.init(objectMapper)

        when:
            RootClause result = objectMapper.readValue(FULL_MATCHER, RootClause)

        then:
            result?.and
            result.and.size() == 2
            result.and.or
            result.and.or.size() == 2
    }

    def "generate matcher rule"() {
        given:
            ObjectMapper objectMapper = new ObjectMapper()
            ObjectMapperUtility.init(objectMapper)

        when:
            def root = new RootClause()
            root.and = new MatcherClause()
            root.and.add(new ItemClause('property1', Operator.E, 'value1'))
            root.and.add(new ItemClause('property2', Operator.LE, 'value2'))
            root.and.or = new MatcherClause()
            root.and.or.add(new ItemClause('property3', Operator.GE, 'value3'))
            root.and.or.add(new ItemClause('property4', Operator.G, 'value4'))
            def json = objectMapper.writeValueAsString(root)

        then:
            json
    }

    def "check Date/Time matcher rule"() {
        given:
            ObjectMapper objectMapper = new ObjectMapper()
            ObjectMapperUtility.init(objectMapper)

            def context1 = new AlertMatchingContext()
            context1.dateTime = ZonedDateTime.parse("2022-05-25T10:15:30-08:00", DateTimeFormatter.ISO_DATE_TIME)
            def context2 = new AlertMatchingContext()
            context2.dateTime = ZonedDateTime.parse("2022-05-15T09:30:00-08:00", DateTimeFormatter.ISO_DATE_TIME)
            def context3 = new AlertMatchingContext()
            context3.dateTime = ZonedDateTime.parse("2022-04-15T10:15:30-08:00", DateTimeFormatter.ISO_DATE_TIME)

        when:
            def matcher = new DateTimeAlertTriggerMatcher(DATE_TIME_MATCHER, objectMapper)
            def match1 = matcher.isMatching(context1)
            def match2 = matcher.isMatching(context2)
            def match3 = matcher.isMatching(context3)

        then:
            match1
            !match2
            !match3
    }

    def "check IN matcher rule"() {
        given:
            ObjectMapper objectMapper = new ObjectMapper()
            ObjectMapperUtility.init(objectMapper)

            def context1 = new AlertMatchingContext()
            context1.deviceId = "102"
            def context2 = new AlertMatchingContext()
            context2.deviceId = "300"
            def context3 = new AlertMatchingContext()
            context3.deviceId = "201"

        when:
            def matcher = new DateTimeAlertTriggerMatcher(IN_MATCHER, objectMapper)
            def match1 = matcher.isMatching(context1)
            def match2 = matcher.isMatching(context2)
            def match3 = matcher.isMatching(context3)

        then:
            match1
            !match2
            match3
    }

    def "check complex matcher rule"() {
        given:
            ObjectMapper objectMapper = new ObjectMapper()
            ObjectMapperUtility.init(objectMapper)

            def context1 = new AlertMatchingContext()
            context1.device = new CameraDto()
            context1.device.cameraGroup = new CameraGroupDto()
            context1.device.cameraGroup.name = 'Very Special Group'
            def context2 = new AlertMatchingContext()
            context2.device = new CameraDto()
            context2.device.cameraGroup = new CameraGroupDto()
            context2.device.cameraGroup.name = 'Very Special Group #2'
            def context3 = new AlertMatchingContext()
            context3.device = new DoorDto()
            context3.device.status = DoorStatus.ACTIVATED

        when:
            def matcher = new CustomAlertTriggerMatcher(DEVICE_MATCHER, objectMapper)
            def match1 = matcher.isMatching(context1)
            def match2 = matcher.isMatching(context2)
            def match3 = matcher.isMatching(context3)

        then:
            match1
            !match2
            !match3
    }
}
