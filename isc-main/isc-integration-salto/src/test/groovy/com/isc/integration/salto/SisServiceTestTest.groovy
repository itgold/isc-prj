package com.isc.integration.salto

import com.google.common.collect.Lists
import com.iscweb.common.sis.ISisServiceFactory
import com.iscweb.common.sis.ISisServiceMapper
import com.iscweb.common.sis.ISisServiceTransport
import com.iscweb.common.sis.ITransportFactory
import com.iscweb.common.sis.exceptions.SisParsingException
import com.iscweb.common.sis.impl.SisServiceFactory
import com.iscweb.common.sis.impl.tcp.BaseTcpConnectionPoolFactory
import com.iscweb.common.sis.impl.tcp.TcpConnectionPoolConfig
import com.iscweb.common.sis.model.SisFieldMetadata
import com.iscweb.common.sis.model.SisMethodMetadata
import com.iscweb.common.sis.model.SisResponseMessageDto
import com.iscweb.integration.doors.ISaltoSisService
import com.iscweb.integration.doors.SaltoSisServiceMapper
import com.iscweb.integration.doors.SaltoSisServiceTransport
import com.iscweb.integration.doors.exceptions.SaltoRequestException
import com.iscweb.integration.doors.models.SaltoGetInfoDto
import com.iscweb.integration.doors.models.doors.SaltoDoorNameListDto
import com.iscweb.integration.doors.models.doors.SaltoExtDoorIdListDto
import com.iscweb.integration.doors.models.request.SaltoDbDoorsRequestDto
import com.iscweb.integration.doors.models.request.SaltoEmergencyOpenRequestDto
import com.iscweb.integration.doors.models.response.SaltoDbDoorsResponseDto
import com.iscweb.integration.doors.models.response.SaltoOnlineDoorActionResponseDto
import org.junit.Assert
import org.springframework.util.CollectionUtils
import spock.lang.Ignore
import spock.lang.Specification

class SisServiceTestTest extends Specification {

    private static final String TEST_RESPONSE1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<RequestResponse>\n" +
            "   <RequestName> TransferMoney </RequestName>\n" +
            "   <Exception>\n" +
            "       <Code> 502 </Code>\n" +
            "       <Message> Unknown request </Message>\n" +
            "   </Exception>\n" +
            "</RequestResponse>"

    private static final String TEST_RESPONSE2 = "" +
            "<?xml version='1.0' encoding='utf-8'?>" +
            "<RequestResponse>" +
            "   <RequestName>SaltoDBDoorList.Read</RequestName>" +
            "   <Params>\n" +
            "    <SaltoDBDoorList>\n" +
            "        <SaltoDBDoor>\n" +
            "            <ExtDoorID>1</ExtDoorID>\n" +
            "            <Name>Door #1</Name>\n" +
            "            <Description />\n" +
            "            <GPF1 />\n" +
            "            <GPF2 />\n" +
            "            <OpenTime>6</OpenTime>\n" +
            "            <OpenTimeADA>20</OpenTimeADA>\n" +
            "            <OpeningMode>0</OpeningMode>\n" +
            "            <TimedPeriodsTableID>1</TimedPeriodsTableID>\n" +
            "            <AutomaticChangesTableID>1</AutomaticChangesTableID>\n" +
            "            <KeypadCode>0000</KeypadCode>\n" +
            "            <AuditOnKeys>1</AuditOnKeys>\n" +
            "            <AntipassbackEnabled>0</AntipassbackEnabled>\n" +
            "            <OutwardAntipassback>0</OutwardAntipassback>\n" +
            "            <UpdateRequired>1</UpdateRequired>\n" +
            "            <BatteryStatus>-1</BatteryStatus>\n" +
            "        </SaltoDBDoor>\n" +
            "        <SaltoDBDoor>\n" +
            "            <ExtDoorID>2</ExtDoorID>\n" +
            "            <Name>Door #2</Name>\n" +
            "            <Description />\n" +
            "            <GPF1 />\n" +
            "            <GPF2 />\n" +
            "            <OpenTime>6</OpenTime>\n" +
            "            <OpenTimeADA>20</OpenTimeADA>\n" +
            "            <OpeningMode>0</OpeningMode>\n" +
            "            <TimedPeriodsTableID>1</TimedPeriodsTableID>\n" +
            "            <AutomaticChangesTableID>1</AutomaticChangesTableID>\n" +
            "            <KeypadCode>0000</KeypadCode>\n" +
            "            <AuditOnKeys>1</AuditOnKeys>\n" +
            "            <AntipassbackEnabled>0</AntipassbackEnabled>\n" +
            "            <OutwardAntipassback>0</OutwardAntipassback>\n" +
            "            <UpdateRequired>1</UpdateRequired>\n" +
            "            <BatteryStatus>-1</BatteryStatus>\n" +
            "        </SaltoDBDoor>\n" +
            "    </SaltoDBDoorList>\n" +
            "    <EOF>1</EOF>\n" +
            "  </Params>" +
            "</RequestResponse>"

    private static final String TEST_RESPONSE3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<RequestResponse>\n" +
            "   <RequestName>GetInfo</RequestName>\n" +
            "   <Params>\n" +
            "       <ProtocolID>SHIP</ProtocolID>\n" +
            "       <ProtocolVersion>1.0</ProtocolVersion>\n" +
            "       <DateTime>2004-9-2T12:30:20</DateTime>\n" +
            "       <DefaultLanguage>eng</DefaultLanguage>\n" +
            "   </Params>\n" +
            "</RequestResponse>"

    private static TcpConnectionPoolConfig config

    def setupSpec() {
        config = new TcpConnectionPoolConfig()
        config.setMaxTotal(2)
        config.setServerHostname("192.168.1.199")
        config.setServerPort(9100)
    }

    @Ignore
    def "testGetInfoCall"() {
        given:
            final String GET_INFO_REQUEST = "" +
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<RequestCall>" +
                    "<RequestName>GetInfo</RequestName>" +
                    "<Params/>" +
                    "</RequestCall>"

            final SaltoSisServiceMapper parser = new SaltoSisServiceMapper()
            final ISisServiceTransport transport = SaltoSisServiceTransport.buildWith(config)

            SaltoGetInfoDto info = null
        when:
            try {
                transport.connect()

                final String response = transport.send(GET_INFO_REQUEST).toString()
                System.out.println(System.lineSeparator() + "Response:")
                System.out.println(response)

                info = parser.readFromString(response, SaltoGetInfoDto.class)
            } finally {
                transport.close()
            }
        then:
            Assert.assertNotNull(info)
    }

    @Ignore
    def "testListDoorsCall"() {
        given:

            final SaltoSisServiceMapper parser = new SaltoSisServiceMapper()

            final ISisServiceTransport transport = SaltoSisServiceTransport.buildWith(config)

            SaltoDbDoorsResponseDto doorsList = null

        when:
            try {
                transport.connect()

                // This part will be generated when the framework is done
                SisMethodMetadata metadata = new SisMethodMetadata()
                metadata.setName("SaltoDBDoorList.Read")
                def fieldMetadata = new SisFieldMetadata("", SisFieldMetadata.Types.OBJECT)
                metadata.setArgs(Arrays.asList(fieldMetadata))


                // Marshaling DTO to new Salto request payload
                def doorRequestDto = new SaltoDbDoorsRequestDto()
                def requestDtoArray = new Object[1]
                requestDtoArray[0] = doorRequestDto
                String request = parser.writeAsString(metadata, requestDtoArray)
                System.out.println(System.lineSeparator() + "Request:")
                System.out.println(request)

                // Send to Salto server
                final String response = transport.send(request).toString()
                System.out.println(System.lineSeparator() + "Response:")
                System.out.println(response)

                // Unmarshalling response into DTO
                doorsList = parser.readFromString(response, SaltoDbDoorsResponseDto.class)
            } finally {
                transport.close()
            }
        then:
            Assert.assertNotNull(doorsList)
    }

    @Ignore
    def "testParseXml"() {
        given:
            final SaltoSisServiceMapper parser = new SaltoSisServiceMapper()

        when:
            try {
                parser.readFromString(TEST_RESPONSE1, String.class)
                Assert.assertTrue("Should throw exception", false)
            } catch (SaltoRequestException e) {
                Assert.assertNotNull("Must be of type SaltoRequestException", e.getError())
            } catch (Exception ignored) {
                Assert.assertTrue("Should throw SaltoRequestException exception", false)
            }

            SaltoGetInfoDto response = parser.readFromString(TEST_RESPONSE3, SaltoGetInfoDto.class)
        then:
            Assert.assertNotNull(response)

        when:
            SaltoDbDoorsResponseDto response2 = parser.readFromString(TEST_RESPONSE2, SaltoDbDoorsResponseDto.class)
        then:
            Assert.assertNotNull(response2)
    }

    @Ignore
    def "testFactoryStub"() {
        given:
            final TestTransportFactory transportFactory = new TestTransportFactory(config)
            final ISisServiceMapper mapper = new SaltoSisServiceMapper()
            final ISisServiceFactory<ISaltoSisService> factory = new SisServiceFactory<>(transportFactory, mapper)

            final ISaltoSisService service = factory.build(ISaltoSisService.class)

            SaltoDbDoorsRequestDto request = new SaltoDbDoorsRequestDto()

        when:
            SaltoDbDoorsResponseDto response = service.listDoors(request)
        then:
            Assert.assertNotNull(response)
            Assert.assertNotNull(response.getDoorsList())
            Assert.assertNotNull(response.getDoorsList().getDoors())
            Assert.assertTrue(response.getDoorsList().getDoors().size() > 0)
    }

    @Ignore
    def "testFactoryReal"() {
        given:
            final SaltoSisServiceMapper mapper = new SaltoSisServiceMapper()
            final ITransportFactory transportFactory = new BaseTcpConnectionPoolFactory(config) {
                @Override
                protected ISisServiceTransport createTransport() {
                    return SaltoSisServiceTransport.buildWith(getConfig())
                }
            }
            final ISisServiceFactory<ISaltoSisService> factory = new SisServiceFactory<>(transportFactory, mapper)
            final ISaltoSisService service = factory.build(ISaltoSisService.class)

            SaltoDbDoorsRequestDto request = new SaltoDbDoorsRequestDto()
        when:
            SaltoDbDoorsResponseDto response = service.listDoors(request)
        then:
            response
            response.getDoorsList()
            response.getDoorsList().getDoors()
            response.getDoorsList().getDoors().size() > 0

        when:
            SaltoGetInfoDto serverInfo = service.getInfo()
        then:
            serverInfo
            serverInfo.getProtocolId()
            serverInfo.getProtocolVersion()

        when:
            final SaltoEmergencyOpenRequestDto doorRequest = new SaltoEmergencyOpenRequestDto()
            doorRequest.setDoorIds(new SaltoExtDoorIdListDto(Arrays.asList("20001", "20002")))
            SaltoOnlineDoorActionResponseDto results = service.emergencyOpenDoor(doorRequest)
        then:
            results
            results.getResultsList()
            !CollectionUtils.isEmpty(results.getResultsList().getResults())

        when:
            results = service.emergencyCloseDoor(doorRequest)
        then:
            results
            results.getResultsList()
            !CollectionUtils.isEmpty(results.getResultsList().getResults())

        when:
            results = service.emergencyEnd(doorRequest)
        then:
            results
            results.getResultsList()
            !CollectionUtils.isEmpty(results.getResultsList().getResults())

        when:
            final ISisServiceTransport transport = transportFactory.getConnection()
            try {
                SisResponseMessageDto msg = transport.send("HELLO")
                Assert.assertNotNull(msg.toString())

                try {
                    mapper.readFromString(msg.toString(), String.class)
                    Assert.assertTrue("Should throw exception", false)
                }
                catch (SaltoRequestException e) {
                    Assert.assertNotNull(e)
                }
                catch (Exception e) {
                    Assert.assertNotNull(e)
                    Assert.assertTrue("Should be specific SaltoRequestException exception", false)
                }
            } finally {
                transport.close()
            }
        then:
            !transport.isConnected()
    }

    @Ignore
    def "testEmergencyDoorSerialization"() throws SisParsingException {
        given:
            final SaltoSisServiceMapper mapper = new SaltoSisServiceMapper()

            SisMethodMetadata meta = new SisMethodMetadata()
            def fieldMetadata = new SisFieldMetadata("SomeName", SisFieldMetadata.Types.OBJECT)
            meta.setArgs(Arrays.asList(fieldMetadata))

            final SaltoEmergencyOpenRequestDto doorRequest = new SaltoEmergencyOpenRequestDto()
            doorRequest.setDoorIds(new SaltoExtDoorIdListDto(Arrays.asList("20001", "20002")))
            doorRequest.setDoorNames(new SaltoDoorNameListDto(Arrays.asList("Front Door", "Back Door")))
        when:
            String result = mapper.writeAsString(meta, { doorRequest })
        then:
            Assert.assertNotNull(result)
    }

    private static class TestSisServiceTransport implements ISisServiceTransport {
        private final List<String> messages = Collections.synchronizedList(Lists.newArrayList())

        private int callNumber = 0

        @Override
        void close() throws IOException {
        }

        @Override
        SisResponseMessageDto send(String payload) {
            callNumber++
            messages.add(payload)

            if (callNumber == 1) {
                return new SisResponseMessageDto(TEST_RESPONSE2.getBytes(SisResponseMessageDto.ENCODING))
            } else {
                return new SisResponseMessageDto(TEST_RESPONSE1.getBytes(SisResponseMessageDto.ENCODING))
            }
        }

        @Override
        void connect() throws IOException {
        }

        @Override
        boolean isConnected() {
            return true
        }

        List<String> getMessages() {
            return messages
        }
    }

    private static class TestTransportFactory extends BaseTcpConnectionPoolFactory {

        TestTransportFactory(TcpConnectionPoolConfig config) {
            super(config)
        }

        @Override
        protected ISisServiceTransport createTransport() {
            return new TestSisServiceTransport()
        }
    }
}
