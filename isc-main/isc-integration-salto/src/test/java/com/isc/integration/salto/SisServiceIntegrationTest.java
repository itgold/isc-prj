package com.isc.integration.salto;

import com.iscweb.common.sis.ISisServiceFactory;
import com.iscweb.common.sis.ISisServiceTransport;
import com.iscweb.common.sis.ITransportFactory;
import com.iscweb.common.sis.impl.SisServiceFactory;
import com.iscweb.common.sis.impl.tcp.BaseTcpConnectionPoolFactory;
import com.iscweb.common.sis.impl.tcp.TcpConnectionPoolConfig;
import com.iscweb.common.sis.model.SisFieldMetadata;
import com.iscweb.common.sis.model.SisMethodMetadata;
import com.iscweb.common.sis.model.SisResponseMessageDto;
import com.iscweb.integration.doors.ISaltoSisService;
import com.iscweb.integration.doors.SaltoSisServiceMapper;
import com.iscweb.integration.doors.SaltoSisServiceTransport;
import com.iscweb.integration.doors.exceptions.SaltoRequestException;
import com.iscweb.integration.doors.models.SaltoDbAuditTrailDto;
import com.iscweb.integration.doors.models.SaltoGetInfoDto;
import com.iscweb.integration.doors.models.doors.SaltoExtDoorIdListDto;
import com.iscweb.integration.doors.models.request.SaltoDbAuditTrailRequestDto;
import com.iscweb.integration.doors.models.request.SaltoDbDoorsRequestDto;
import com.iscweb.integration.doors.models.request.SaltoDbUsersRequestDto;
import com.iscweb.integration.doors.models.request.SaltoEmergencyOpenRequestDto;
import com.iscweb.integration.doors.models.response.SaltoDbDoorsResponseDto;
import com.iscweb.integration.doors.models.response.SaltoDbUsersResponseDto;
import com.iscweb.integration.doors.models.response.SaltoOnlineDoorActionResponseDto;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;

/**
 * This is supposed to be an integration test. However, we need real Salto server to call so all tests are
 * disabled. I still want to keep them in code base because they are good when performing test integration
 * with real server.
 */
public class SisServiceIntegrationTest {

    private static TcpConnectionPoolConfig config;

    @BeforeClass
    public static void setup() {
        config = new TcpConnectionPoolConfig();
        config.setMaxTotal(2);
        config.setServerHostname("192.168.1.199");
        config.setServerPort(9100);
    }

    @Test
    @Ignore
    public void testGetInfoCall() throws Exception {
        final String getInfoRequest = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<RequestCall>" +
                "<RequestName>GetInfo</RequestName>" +
                "<Params/>" +
                "</RequestCall>";

        final SaltoSisServiceMapper parser = new SaltoSisServiceMapper();
        try (final ISisServiceTransport transport = SaltoSisServiceTransport.buildWith(SisServiceIntegrationTest.config)) {
            transport.connect();

            final String response = transport.send(getInfoRequest).toString();
            System.out.println(System.lineSeparator() + "Response:");
            System.out.println(response);

            SaltoGetInfoDto info = parser.readFromString(response, SaltoGetInfoDto.class);
            Assert.assertNotNull(info);
        }
    }

    @Test
    @Ignore
    public void testListDoorsCall() throws Exception {
        final SaltoSisServiceMapper parser = new SaltoSisServiceMapper();
        try (final ISisServiceTransport transport = SaltoSisServiceTransport.buildWith(SisServiceIntegrationTest.config)) {
            transport.connect();

            // This part will be generated when the framework is done.
            SisMethodMetadata metadata = new SisMethodMetadata();
            metadata.setName("SaltoDBDoorList.Read");
            metadata.setArgs(Collections.singletonList(new SisFieldMetadata("", SisFieldMetadata.Types.OBJECT)));

            // Marshalling DTO to new Salto request payload
            String request = parser.writeAsString(metadata, new Object[] { new SaltoDbDoorsRequestDto() });
            System.out.println(System.lineSeparator() + "Request:");
            System.out.println(request);

            // Send to Salto server
            final String response = transport.send(request).toString();
            System.out.println(System.lineSeparator() + "Response:");
            System.out.println(response);

            // Unmarshalling response into DTO
            SaltoDbDoorsResponseDto doorsList = parser.readFromString(response, SaltoDbDoorsResponseDto.class);
            Assert.assertNotNull(doorsList);
        }
    }

    @Test
    @Ignore
    public void testFactoryReal() throws Exception {
        final SaltoSisServiceMapper mapper = new SaltoSisServiceMapper();
        final ITransportFactory transportFactory = new BaseTcpConnectionPoolFactory(SisServiceIntegrationTest.config) {
            @Override
            protected ISisServiceTransport createTransport() {
                return SaltoSisServiceTransport.buildWith(getConfig());
            }
        };
        final ISisServiceFactory<ISaltoSisService> factory = new SisServiceFactory<>(transportFactory, mapper);
        final ISaltoSisService service = factory.build(ISaltoSisService.class);

        SaltoDbDoorsRequestDto request = new SaltoDbDoorsRequestDto();
        SaltoDbDoorsResponseDto response = service.listDoors(request);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getDoorsList());
        Assert.assertNotNull(response.getDoorsList().getDoors());
        Assert.assertTrue(response.getDoorsList().getDoors().size() > 0);

        SaltoGetInfoDto serverInfo = service.getInfo();
        Assert.assertNotNull(serverInfo);
        Assert.assertNotNull(serverInfo.getProtocolId());
        Assert.assertNotNull(serverInfo.getProtocolVersion());

        final SaltoEmergencyOpenRequestDto doorRequest = new SaltoEmergencyOpenRequestDto();
        doorRequest.setDoorIds(new SaltoExtDoorIdListDto(Arrays.asList("20001", "20002")));
        SaltoOnlineDoorActionResponseDto results = service.emergencyOpenDoor(doorRequest);
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getResultsList());
        Assert.assertFalse(CollectionUtils.isEmpty(results.getResultsList().getResults()));

        results = service.emergencyCloseDoor(doorRequest);
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getResultsList());
        Assert.assertFalse(CollectionUtils.isEmpty(results.getResultsList().getResults()));

        results = service.emergencyEnd(doorRequest);
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getResultsList());
        Assert.assertFalse(CollectionUtils.isEmpty(results.getResultsList().getResults()));

        try (final ISisServiceTransport transport = transportFactory.getConnection()) {
            SisResponseMessageDto msg = transport.send("HELLO");
            Assert.assertNotNull(msg.toString());

            try {
                mapper.readFromString(msg.toString(), String.class);
                Assert.fail("Should throw exception");
            } catch (SaltoRequestException e) {
                Assert.assertTrue(true);
            } catch (Exception e) {
                Assert.fail("Should be specific SaltoRequestException exception");
            }
        }
    }

    @Test
    @Ignore
    public void testPollAuditTrail() throws Exception {
        final SaltoSisServiceMapper mapper = new SaltoSisServiceMapper();
        final ITransportFactory transportFactory = new BaseTcpConnectionPoolFactory(SisServiceIntegrationTest.config) {
            @Override
            protected ISisServiceTransport createTransport() {
                return SaltoSisServiceTransport.buildWith(getConfig());
            }
        };
        final ISisServiceFactory<ISaltoSisService> factory = new SisServiceFactory<>(transportFactory, mapper);
        final ISaltoSisService service = factory.build(ISaltoSisService.class);

        SaltoDbAuditTrailRequestDto request = new SaltoDbAuditTrailRequestDto();
        SaltoDbAuditTrailDto response = service.getAuditTrail(request);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getEventsList());
        Assert.assertNotNull(response.getEventsList().getEvents());
        Assert.assertTrue(response.getEventsList().getEvents().size() > 2);

        SaltoDbAuditTrailRequestDto request2 = new SaltoDbAuditTrailRequestDto();
        request2.setStartingFromEventId(response.getEventsList().getEvents().get(2).getEventId());
        SaltoDbAuditTrailDto response2 = service.getAuditTrail(request2);
        Assert.assertNotNull(response2);
        Assert.assertNotNull(response2.getEventsList());
        Assert.assertNotNull(response2.getEventsList().getEvents());
        Assert.assertTrue(response2.getEventsList().getEvents().size() > 2);
        Assert.assertEquals(response.getEventsList().getEvents().get(2).getEventId(), response2.getEventsList().getEvents().get(0).getEventId());
    }

    @Test
    @Ignore
    public void testListUsers() throws Exception {
        final SaltoSisServiceMapper mapper = new SaltoSisServiceMapper();
        final ITransportFactory transportFactory = new BaseTcpConnectionPoolFactory(SisServiceIntegrationTest.config) {
            @Override
            protected ISisServiceTransport createTransport() {
                return SaltoSisServiceTransport.buildWith(getConfig());
            }
        };
        final ISisServiceFactory<ISaltoSisService> factory = new SisServiceFactory<>(transportFactory, mapper);
        final ISaltoSisService service = factory.build(ISaltoSisService.class);

        SaltoDbUsersRequestDto request = new SaltoDbUsersRequestDto();
        SaltoDbUsersResponseDto response = service.listUsers(request);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getUsersList());
        Assert.assertNotNull(response.getUsersList().getUsers());
        Assert.assertTrue(response.getUsersList().getUsers().size() > 0);

        SaltoDbUsersRequestDto request2 = new SaltoDbUsersRequestDto();
        request2.setReturnGroupPermissions(Boolean.TRUE);
        request2.setReturnDoorPermissions(Boolean.TRUE);
        request2.setReturnZonePermissions(Boolean.TRUE);

        SaltoDbUsersResponseDto response2 = service.listUsers(request2);
        Assert.assertNotNull(response2);
        Assert.assertNotNull(response2.getUsersList());
        Assert.assertNotNull(response2.getUsersList().getUsers());
        Assert.assertTrue(response2.getUsersList().getUsers().size() > 0);
    }
}
