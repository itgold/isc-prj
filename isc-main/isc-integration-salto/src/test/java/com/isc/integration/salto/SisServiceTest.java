package com.isc.integration.salto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.collect.Lists;
import com.iscweb.common.sis.ISisServiceFactory;
import com.iscweb.common.sis.ISisServiceMapper;
import com.iscweb.common.sis.ISisServiceTransport;
import com.iscweb.common.sis.exceptions.SisBusinessException;
import com.iscweb.common.sis.exceptions.SisConnectionException;
import com.iscweb.common.sis.exceptions.SisParsingException;
import com.iscweb.common.sis.impl.SisServiceFactory;
import com.iscweb.common.sis.impl.tcp.BaseTcpConnectionPoolFactory;
import com.iscweb.common.sis.impl.tcp.TcpConnectionPoolConfig;
import com.iscweb.common.sis.model.SisFieldMetadata;
import com.iscweb.common.sis.model.SisMethodMetadata;
import com.iscweb.common.sis.model.SisResponseMessageDto;
import com.iscweb.common.util.StringUtils;
import com.iscweb.integration.doors.ISaltoSisService;
import com.iscweb.integration.doors.SaltoSisServiceMapper;
import com.iscweb.integration.doors.events.DoorSyncEvent;
import com.iscweb.integration.doors.events.DoorSyncPayload;
import com.iscweb.integration.doors.exceptions.SaltoRequestException;
import com.iscweb.integration.doors.models.SaltoDbAuditTrailDto;
import com.iscweb.integration.doors.models.SaltoGetInfoDto;
import com.iscweb.integration.doors.models.SaltoStreamEventDto;
import com.iscweb.integration.doors.models.doors.OnlineDoorStatusDto;
import com.iscweb.integration.doors.models.doors.SaltoDbDoorDto;
import com.iscweb.integration.doors.models.doors.SaltoDoorNameListDto;
import com.iscweb.integration.doors.models.doors.SaltoExtDoorIdListDto;
import com.iscweb.integration.doors.models.enums.BatteryStatus;
import com.iscweb.integration.doors.models.enums.CommStatus;
import com.iscweb.integration.doors.models.enums.DoorOpeningMode;
import com.iscweb.integration.doors.models.enums.DoorStatus;
import com.iscweb.integration.doors.models.enums.DoorType;
import com.iscweb.integration.doors.models.enums.Operation;
import com.iscweb.integration.doors.models.enums.TamperStatus;
import com.iscweb.integration.doors.models.request.SaltoDbDoorsRequestDto;
import com.iscweb.integration.doors.models.request.SaltoEmergencyOpenRequestDto;
import com.iscweb.integration.doors.models.response.OnlineDoorStatusListResponseDto;
import com.iscweb.integration.doors.models.response.SaltoDbDoorsResponseDto;
import com.iscweb.integration.doors.models.response.SaltoDbUsersResponseDto;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import spock.lang.Ignore;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

@Ignore
public class SisServiceTest {

    private static final String TEST_RESPONSE1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                 "<RequestResponse>\n" +
                                                 "   <RequestName> TransferMoney </RequestName>\n" +
                                                 "   <Exception>\n" +
                                                 "       <Code> 502 </Code>\n" +
                                                 "       <Message> Unknown request </Message>\n" +
                                                 "   </Exception>\n" +
                                                 "</RequestResponse>";

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
            "</RequestResponse>";

    private static final String TEST_RESPONSE3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<RequestResponse>\n" +
            "   <RequestName>GetInfo</RequestName>\n" +
            "   <Params>\n" +
            "       <ProtocolID>SHIP</ProtocolID>\n" +
            "       <ProtocolVersion>1.0</ProtocolVersion>\n" +
            "       <DateTime>2004-9-2T12:30:20</DateTime>\n" +
            "       <DefaultLanguage>eng</DefaultLanguage>\n" +
            "   </Params>\n" +
            "</RequestResponse>";

    private static final String TEST_USERS_RESPONSE = "<?xml version='1.0' encoding='utf-8'?>\n" +
            "<RequestResponse>\n" +
            "   <RequestName>SaltoDBUserList.Read</RequestName>\n" +
            "   <Params>\n" +
            "      <SaltoDBUserList>\n" +
            "         <SaltoDBUser>\n" +
            "            <ExtUserID>11111</ExtUserID>\n" +
            "            <Title>BLE</Title>\n" +
            "            <FirstName>John</FirstName>\n" +
            "            <LastName>Doe</LastName>\n" +
            "            <Office>0</Office>\n" +
            "            <AuditOpenings>1</AuditOpenings>\n" +
            "            <UseADA>0</UseADA>\n" +
            "            <UseAntipassback>0</UseAntipassback>\n" +
            "            <CalendarID>1</CalendarID>\n" +
            "            <UseLockCalendar>0</UseLockCalendar>\n" +
            "            <GPF1/>\n" +
            "            <GPF2/>\n" +
            "            <GPF3/>\n" +
            "            <GPF4/>\n" +
            "            <GPF5/>\n" +
            "            <AutoKeyEdit.ROMCode/>\n" +
            "            <CurrentROMCode>00000000000000</CurrentROMCode>\n" +
            "            <UserActivation>2020-05-29T09:55:23</UserActivation>\n" +
            "            <UserExpiration.Enabled>0</UserExpiration.Enabled>\n" +
            "            <UserExpiration>2000-01-01T00:00:00</UserExpiration>\n" +
            "            <KeyExpirationDifferentFromUserExpiration>1</KeyExpirationDifferentFromUserExpiration>\n" +
            "            <KeyRevalidation.UpdatePeriod>7</KeyRevalidation.UpdatePeriod>\n" +
            "            <KeyRevalidation.UnitOfUpdatePeriod>0</KeyRevalidation.UnitOfUpdatePeriod>\n" +
            "            <CurrentKeyExists>1</CurrentKeyExists>\n" +
            "            <CurrentKeyPeriod>\n" +
            "               <StartDate>2000-01-01T00:00:00</StartDate>\n" +
            "               <EndDate>2020-08-05T24:00:00</EndDate>\n" +
            "            </CurrentKeyPeriod>\n" +
            "            <CurrentKeyStatus>1</CurrentKeyStatus>\n" +
            "            <PINEnabled>0</PINEnabled>\n" +
            "            <PINCode/>\n" +
            "            <WiegandCode/>\n" +
            "            <IsBanned>0</IsBanned>\n" +
            "            <KeyIsCancellableThroughBlacklist>1</KeyIsCancellableThroughBlacklist>\n" +
            "            <LockdownEnabled>0</LockdownEnabled>\n" +
            "            <OverrideLockdown>0</OverrideLockdown>\n" +
            "            <OverridePrivacy>0</OverridePrivacy>\n" +
            "            <LocationFunctionTimezoneTableID>0</LocationFunctionTimezoneTableID>\n" +
            "            <PhoneNumber>+19496908067</PhoneNumber>\n" +
            "            <MobileAppType>2</MobileAppType>\n" +
            "            <ExtLimitedEntryGroupID/>\n" +
            "            <PictureFileName/>\n" +
            "            <AuthorizationCodeList/>\n" +
            "            <SaltoDB.AccessPermissionList.User_Door>\n" +
            "               <SaltoDB.AccessPermission.User_Door>\n" +
            "                  <ExtUserID>11111</ExtUserID>\n" +
            "                  <ExtDoorID>mulion</ExtDoorID>\n" +
            "                  <TimezoneTableID>0</TimezoneTableID>\n" +
            "                  <UsePeriod>0</UsePeriod>\n" +
            "                  <Period>\n" +
            "                     <StartDate>1900-01-01T00:00:00</StartDate>\n" +
            "                     <EndDate>1900-01-01T00:00:00</EndDate>\n" +
            "                  </Period>\n" +
            "               </SaltoDB.AccessPermission.User_Door>\n" +
            "            </SaltoDB.AccessPermissionList.User_Door>\n" +
            "            <SaltoDB.AccessPermissionList.User_Zone/>\n" +
            "            <SaltoDB.MembershipList.User_Group/>\n" +
            "         </SaltoDBUser>\n" +
            "         <SaltoDBUser>\n" +
            "            <ExtUserID>11112</ExtUserID>\n" +
            "            <Title/>\n" +
            "            <FirstName>Marry</FirstName>\n" +
            "            <LastName>Anne</LastName>\n" +
            "            <Office>0</Office>\n" +
            "            <AuditOpenings>0</AuditOpenings>\n" +
            "            <UseADA>0</UseADA>\n" +
            "            <UseAntipassback>0</UseAntipassback>\n" +
            "            <CalendarID>1</CalendarID>\n" +
            "            <UseLockCalendar>0</UseLockCalendar>\n" +
            "            <GPF1/>\n" +
            "            <GPF2/>\n" +
            "            <GPF3/>\n" +
            "            <GPF4/>\n" +
            "            <GPF5/>\n" +
            "            <AutoKeyEdit.ROMCode/>\n" +
            "            <UserActivation>2020-05-29T09:55:23</UserActivation>\n" +
            "            <UserExpiration.Enabled>0</UserExpiration.Enabled>\n" +
            "            <UserExpiration>2000-01-01T00:00:00</UserExpiration>\n" +
            "            <KeyExpirationDifferentFromUserExpiration>1</KeyExpirationDifferentFromUserExpiration>\n" +
            "            <KeyRevalidation.UpdatePeriod>30</KeyRevalidation.UpdatePeriod>\n" +
            "            <KeyRevalidation.UnitOfUpdatePeriod>0</KeyRevalidation.UnitOfUpdatePeriod>\n" +
            "            <CurrentKeyExists>0</CurrentKeyExists>\n" +
            "            <CurrentKeyStatus>0</CurrentKeyStatus>\n" +
            "            <PINEnabled>0</PINEnabled>\n" +
            "            <PINCode/>\n" +
            "            <WiegandCode/>\n" +
            "            <IsBanned>0</IsBanned>\n" +
            "            <KeyIsCancellableThroughBlacklist>1</KeyIsCancellableThroughBlacklist>\n" +
            "            <LockdownEnabled>0</LockdownEnabled>\n" +
            "            <OverrideLockdown>0</OverrideLockdown>\n" +
            "            <OverridePrivacy>0</OverridePrivacy>\n" +
            "            <LocationFunctionTimezoneTableID>0</LocationFunctionTimezoneTableID>\n" +
            "            <PhoneNumber/>\n" +
            "            <MobileAppType>0</MobileAppType>\n" +
            "            <ExtLimitedEntryGroupID/>\n" +
            "            <PictureFileName/>\n" +
            "            <AuthorizationCodeList/>\n" +
            "            <SaltoDB.AccessPermissionList.User_Door>\n" +
            "               <SaltoDB.AccessPermission.User_Door>\n" +
            "                  <ExtUserID>11112</ExtUserID>\n" +
            "                  <ExtDoorID>20001</ExtDoorID>\n" +
            "                  <TimezoneTableID>0</TimezoneTableID>\n" +
            "                  <UsePeriod>0</UsePeriod>\n" +
            "                  <Period>\n" +
            "                     <StartDate>1900-01-01T00:00:00</StartDate>\n" +
            "                     <EndDate>1900-01-01T00:00:00</EndDate>\n" +
            "                  </Period>\n" +
            "               </SaltoDB.AccessPermission.User_Door>\n" +
            "            </SaltoDB.AccessPermissionList.User_Door>\n" +
            "            <SaltoDB.AccessPermissionList.User_Zone/>\n" +
            "            <SaltoDB.MembershipList.User_Group/>\n" +
            "         </SaltoDBUser>\n" +
            "         <SaltoDBUser>\n" +
            "            <ExtUserID>dmorozov</ExtUserID>\n" +
            "            <Title/>\n" +
            "            <FirstName>Denis</FirstName>\n" +
            "            <LastName>Morozov</LastName>\n" +
            "            <Office>0</Office>\n" +
            "            <AuditOpenings>1</AuditOpenings>\n" +
            "            <UseADA>0</UseADA>\n" +
            "            <UseAntipassback>0</UseAntipassback>\n" +
            "            <CalendarID>1</CalendarID>\n" +
            "            <UseLockCalendar>0</UseLockCalendar>\n" +
            "            <GPF1/>\n" +
            "            <GPF2/>\n" +
            "            <GPF3/>\n" +
            "            <GPF4/>\n" +
            "            <GPF5/>\n" +
            "            <AutoKeyEdit.ROMCode/>\n" +
            "            <CurrentROMCode>04BD04229B6684</CurrentROMCode>\n" +
            "            <UserActivation>2020-07-29T11:51:40</UserActivation>\n" +
            "            <UserExpiration.Enabled>0</UserExpiration.Enabled>\n" +
            "            <UserExpiration>2000-01-01T00:00:00</UserExpiration>\n" +
            "            <KeyExpirationDifferentFromUserExpiration>1</KeyExpirationDifferentFromUserExpiration>\n" +
            "            <KeyRevalidation.UpdatePeriod>30</KeyRevalidation.UpdatePeriod>\n" +
            "            <KeyRevalidation.UnitOfUpdatePeriod>0</KeyRevalidation.UnitOfUpdatePeriod>\n" +
            "            <CurrentKeyExists>1</CurrentKeyExists>\n" +
            "            <CurrentKeyPeriod>\n" +
            "               <StartDate>2000-01-01T00:00:00</StartDate>\n" +
            "               <EndDate>2020-08-29T24:00:00</EndDate>\n" +
            "            </CurrentKeyPeriod>\n" +
            "            <CurrentKeyStatus>1</CurrentKeyStatus>\n" +
            "            <PINEnabled>0</PINEnabled>\n" +
            "            <PINCode/>\n" +
            "            <WiegandCode/>\n" +
            "            <IsBanned>0</IsBanned>\n" +
            "            <KeyIsCancellableThroughBlacklist>1</KeyIsCancellableThroughBlacklist>\n" +
            "            <LockdownEnabled>0</LockdownEnabled>\n" +
            "            <OverrideLockdown>0</OverrideLockdown>\n" +
            "            <OverridePrivacy>0</OverridePrivacy>\n" +
            "            <LocationFunctionTimezoneTableID>0</LocationFunctionTimezoneTableID>\n" +
            "            <PhoneNumber/>\n" +
            "            <MobileAppType>0</MobileAppType>\n" +
            "            <ExtLimitedEntryGroupID/>\n" +
            "            <PictureFileName/>\n" +
            "            <AuthorizationCodeList/>\n" +
            "            <SaltoDB.AccessPermissionList.User_Door>\n" +
            "               <SaltoDB.AccessPermission.User_Door>\n" +
            "                  <ExtUserID>dmorozov</ExtUserID>\n" +
            "                  <ExtDoorID>mulion</ExtDoorID>\n" +
            "                  <TimezoneTableID>0</TimezoneTableID>\n" +
            "                  <UsePeriod>0</UsePeriod>\n" +
            "                  <Period>\n" +
            "                     <StartDate>1900-01-01T00:00:00</StartDate>\n" +
            "                     <EndDate>1900-01-01T00:00:00</EndDate>\n" +
            "                  </Period>\n" +
            "               </SaltoDB.AccessPermission.User_Door>\n" +
            "               <SaltoDB.AccessPermission.User_Door>\n" +
            "                  <ExtUserID>dmorozov</ExtUserID>\n" +
            "                  <ExtDoorID>keypad1</ExtDoorID>\n" +
            "                  <TimezoneTableID>0</TimezoneTableID>\n" +
            "                  <UsePeriod>0</UsePeriod>\n" +
            "                  <Period>\n" +
            "                     <StartDate>1900-01-01T00:00:00</StartDate>\n" +
            "                     <EndDate>1900-01-01T00:00:00</EndDate>\n" +
            "                  </Period>\n" +
            "               </SaltoDB.AccessPermission.User_Door>\n" +
            "            </SaltoDB.AccessPermissionList.User_Door>\n" +
            "            <SaltoDB.AccessPermissionList.User_Zone/>\n" +
            "            <SaltoDB.MembershipList.User_Group/>\n" +
            "         </SaltoDBUser>\n" +
            "      </SaltoDBUserList>\n" +
            "      <EOF>1</EOF>\n" +
            "   </Params>\n" +
            "</RequestResponse>";

    private static final String TEST_RESPONSE = "<?xml version='1.0' encoding='utf-8'?>\n" +
                                           "<RequestResponse>\n" +
                                           "   <RequestName>SaltoDBAuditTrail.Read</RequestName>\n" +
                                           "   <Params>\n" +
                                           "       <SaltoDBAuditTrail>\n" +
                                           "           <SaltoDBAuditTrailEvent>\n" +
                                           "               <EventID>12</EventID>\n" +
                                           "               <EventDateTime>2020-07-29T18:24:12</EventDateTime>\n" +
                                           "               <Operation>72</Operation>\n" +
                                           "               <IsExit>0</IsExit>\n" +
                                           "               <DoorID>Black keypad</DoorID>\n" +
                                           "               <SubjectType>2</SubjectType>\n" +
                                           "               <SubjectID>_SALTO</SubjectID>\n" +
                                           "           </SaltoDBAuditTrailEvent>\n" +
                                           "       </SaltoDBAuditTrail>\n" +
                                           "   </Params>\n" +
                                           "</RequestResponse>";

    private static final String TEST_EVENT1 = "[\n" +
            "  {\n" +
            "    \"EventID\": \"11224444556677889900\",\n" +
            "    \"EventDateTime\": \"2012-04-14T15:07:23\",\n" +
            "    \"EventTime\": \"15:07:23\",\n" +
            "    \"EventDateTimeUTC\": \"2012-04-14T15:07:23Z\",\n" +
            "    \"OperationID\": 82,\n" +
            "    \"OperationDescription\": \"Opening not allowed: key expired\",\n" +
            "    \"IsExit\": false,\n" +
            "    \"UserType\": 0,\n" +
            "    \"UserName\": \"Mathew Adams\",\n" +
            "    \"UserGPF3\": \"R&D department\",\n" +
            "    \"DoorName\": \"Office\",\n" +
            "    \"DoorGPF1\": \"First floor\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"EventID\": \"11224444556677889989\",\n" +
            "    \"EventDateTime\": \"2012-04-14T15:07:23\",\n" +
            "    \"EventTime\": \"15:07:23\",\n" +
            "    \"EventDateTimeUTC\": \"2012-04-14T15:07:23Z\",\n" +
            "    \"OperationID\": 17,\n" +
            "    \"OperationDescription\": \"Door opened: key\",\n" +
            "    \"IsExit\": false,\n" +
            "    \"UserType\": 0,\n" +
            "    \"UserName\": \"John Smith\",\n" +
            "    \"UserGPF3\": \"Marketing department\",\n" +
            "    \"DoorName\": \"Gym\",\n" +
            "    \"DoorGPF1\": \"Leisure area\"\n" +
            "  }\n" +
            "]";

    private static TcpConnectionPoolConfig config;

    @BeforeClass
    public static void setup() {
        config = new TcpConnectionPoolConfig();
        config.setMaxTotal(2);
        config.setServerHostname("192.168.1.199");
        config.setServerPort(9100);
    }

    @Test
    public void testParseXml() throws Exception {
        final SaltoSisServiceMapper parser = new SaltoSisServiceMapper();

        try {
            parser.readFromString(TEST_RESPONSE1, String.class);
            Assert.fail("Should throw exception");
        } catch (SaltoRequestException e) {
            Assert.assertNotNull("Must be of type SaltoRequestException", e.getError());
        } catch (Exception e) {
            Assert.fail("Should throw SaltoRequestException exception");
        }

        SaltoGetInfoDto response = parser.readFromString(TEST_RESPONSE3, SaltoGetInfoDto.class);
        Assert.assertNotNull(response);

        SaltoDbDoorsResponseDto response2 = parser.readFromString(TEST_RESPONSE2, SaltoDbDoorsResponseDto.class);
        Assert.assertNotNull(response2);
    }

    @Test
    public void testFactoryStub() throws SisConnectionException {
        final TestTransportFactory transportFactory = new TestTransportFactory(SisServiceTest.config);
        final ISisServiceMapper mapper = new SaltoSisServiceMapper();
        final ISisServiceFactory<ISaltoSisService> factory = new SisServiceFactory<>(transportFactory, mapper);

        final ISaltoSisService service = factory.build(ISaltoSisService.class);

        SaltoDbDoorsRequestDto request = new SaltoDbDoorsRequestDto();
        SaltoDbDoorsResponseDto response = service.listDoors(request);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getDoorsList());
        Assert.assertNotNull(response.getDoorsList().getDoors());
        Assert.assertTrue(response.getDoorsList().getDoors().size() > 0);
    }

    @Test
    public void testParseAuditTrailXml() throws Exception {
        final SaltoSisServiceMapper parser = new SaltoSisServiceMapper();

        SaltoDbAuditTrailDto response = parser.readFromString(TEST_RESPONSE, SaltoDbAuditTrailDto.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testEmergencyDoorSerialization() throws SisParsingException {
        final SaltoSisServiceMapper mapper = new SaltoSisServiceMapper();

        SisMethodMetadata meta = new SisMethodMetadata();
        meta.setArgs(Collections.singletonList(new SisFieldMetadata("SomeName", SisFieldMetadata.Types.OBJECT)));

        final SaltoEmergencyOpenRequestDto doorRequest = new SaltoEmergencyOpenRequestDto();
        doorRequest.setDoorIds(new SaltoExtDoorIdListDto(Arrays.asList("20001", "20002")));
        doorRequest.setDoorNames(new SaltoDoorNameListDto(Arrays.asList("Front Door", "Back Door")));
        String result = mapper.writeAsString(meta, new Object[] { doorRequest });
        System.out.println(result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testParseUsersResponseXml() throws Exception {
        final SaltoSisServiceMapper parser = new SaltoSisServiceMapper();

        SaltoDbUsersResponseDto response = parser.readFromString(TEST_USERS_RESPONSE, SaltoDbUsersResponseDto.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testBinaryTypeConvert() {
        byte[] data = new byte[] { (byte) 0xFA, 17 };
        String rez = StringUtils.bytesToHex(data);
        Assert.assertNotNull(rez);

        byte[] converted = StringUtils.hexStringToBytes(rez);
        Assert.assertNotNull(converted);
        Assert.assertEquals(data.length, converted.length);
        for (int i = 0; i < data.length; i++) {
            Assert.assertEquals(data[i], converted[i]);
        }

        data = new byte[]{4, -67, 4, 34, -101, 102, -124};
        rez = StringUtils.bytesToHex(data);
        Assert.assertNotNull(rez);
        Assert.assertEquals(rez, "04BD04229B6684");
    }

    @Test
    public void testDoorStatusDeserialization() throws SisBusinessException {
        final String TEST_RESPONSE = "<?xml version='1.0' encoding='utf-8'?>\n" +
                "<RequestResponse>\n" +
                "    <RequestName>OnlineDoor.GetOnlineStatusList</RequestName>\n" +
                "    <Params>\n" +
                "        <OnlineDoorStatusList>\n" +
                "            <OnlineDoorStatus>\n" +
                "                <DoorID>20001</DoorID>\n" +
                "                <DoorType>-1</DoorType>\n" +
                "                <CommStatus>-1</CommStatus>\n" +
                "                <DoorStatus>-1</DoorStatus>\n" +
                "                <BatteryStatus>-1</BatteryStatus>\n" +
                "                <TamperStatus>-1</TamperStatus>\n" +
                "            </OnlineDoorStatus>\n" +
                "            <OnlineDoorStatus>\n" +
                "                <DoorID>20002</DoorID>\n" +
                "                <DoorType>-1</DoorType>\n" +
                "                <CommStatus>-1</CommStatus>\n" +
                "                <DoorStatus>-1</DoorStatus>\n" +
                "                <BatteryStatus>-1</BatteryStatus>\n" +
                "                <TamperStatus>-1</TamperStatus>\n" +
                "            </OnlineDoorStatus>\n" +
                "            <OnlineDoorStatus>\n" +
                "                <DoorID>keypad1</DoorID>\n" +
                "                <DoorType>0</DoorType>\n" +
                "                <CommStatus>1</CommStatus>\n" +
                "                <DoorStatus>1</DoorStatus>\n" +
                "                <BatteryStatus>0</BatteryStatus>\n" +
                "                <TamperStatus>0</TamperStatus>\n" +
                "            </OnlineDoorStatus>\n" +
                "            <OnlineDoorStatus>\n" +
                "                <DoorID>mulion</DoorID>\n" +
                "                <DoorType>0</DoorType>\n" +
                "                <CommStatus>1</CommStatus>\n" +
                "                <DoorStatus>1</DoorStatus>\n" +
                "                <BatteryStatus>0</BatteryStatus>\n" +
                "                <TamperStatus>0</TamperStatus>\n" +
                "            </OnlineDoorStatus>\n" +
                "        </OnlineDoorStatusList>\n" +
                "    </Params>\n" +
                "</RequestResponse>";

        final SaltoSisServiceMapper parser = new SaltoSisServiceMapper();
        OnlineDoorStatusListResponseDto response = parser.readFromString(TEST_RESPONSE, OnlineDoorStatusListResponseDto.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testParseEventStreamJson() throws Exception {
        final ObjectMapper parser = primaryObjectMapper();

        CollectionType listItemClass = parser.getTypeFactory().constructCollectionType(List.class, SaltoStreamEventDto.class);
        List<SaltoStreamEventDto> event = parser.readValue(TEST_EVENT1, listItemClass);
        Assert.assertNotNull(event);
        Assert.assertEquals(event.size(), 2);
        Assert.assertEquals(event.get(0).getOperation(), Operation.OPENING_NOT_ALLOWED_KEY_EXPIRED);
        Assert.assertEquals(event.get(1).getOperation(), Operation.DOOR_OPENED_KEY);
        // "EventDateTime": "2012-04-14T15:07:23"
        // "EventTime": "15:07:23"
        // "EventDateTimeUTC": "2012-04-14T15:07:23Z"
    }

    public void testSyncEvent() {
        SaltoDbDoorDto saltoDoor = new SaltoDbDoorDto();
        saltoDoor.setId("TESTDOOR#1");
        saltoDoor.setName("TESTDOOR#1 Name");
        saltoDoor.setDescription("TESTDOOR#1 Description");
        saltoDoor.setOpeningMode(DoorOpeningMode.STANDARD);
        saltoDoor.setBatteryStatus(75);

        OnlineDoorStatusDto status = new OnlineDoorStatusDto();
        status.setDoorId(saltoDoor.getId());
        status.setDoorType(DoorType.RF_WIRELESS);
        status.setCommStatus(CommStatus.ONLINE);
        status.setDoorStatus(DoorStatus.OPENED);
        status.setBatteryStatus(BatteryStatus.NORMAL);
        status.setTamperStatus(TamperStatus.NORMAL);

        DoorSyncEvent event = new DoorSyncEvent();
        event.setExternalEntityId(saltoDoor.getId());
        event.setEventTime(ZonedDateTime.now());
        event.setPayload(new DoorSyncPayload(saltoDoor, status));
        // getEventHub().post(event);
    }

    public ObjectMapper primaryObjectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .serializers()
                .featuresToEnable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                .featuresToDisable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .featuresToDisable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
                // to avoid problem with marshaling proxied beans which do not have it's own properties
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .annotationIntrospector(new JacksonAnnotationIntrospector())
                // Set internal datetime representation to System Default to work with instants
                // at all times. When saving objects, the timezone will be updated accordingly
                .timeZone(TimeZone.getDefault())
                .build();
    }

    private static class TestSisServiceTransport implements ISisServiceTransport {
        private final List<String> messages = Collections.synchronizedList(Lists.newArrayList());

        private int callNumber = 0;

        @Override
        public void close() {
        }

        @Override
        public SisResponseMessageDto send(String payload) {
            callNumber++;
            messages.add(payload);

            SisResponseMessageDto result;
            if (callNumber == 1) {
                result = new SisResponseMessageDto(TEST_RESPONSE2.getBytes(SisResponseMessageDto.ENCODING));
            } else {
                result = new SisResponseMessageDto(TEST_RESPONSE1.getBytes(SisResponseMessageDto.ENCODING));
            }
            return result;
        }

        @Override
        public void connect() {
        }

        @Override
        public boolean isConnected() {
            return true;
        }

        public List<String> getMessages() {
            return messages;
        }
    }

    private static class TestTransportFactory extends BaseTcpConnectionPoolFactory {

        public TestTransportFactory(TcpConnectionPoolConfig config) {
            super(config);
        }

        @Override
        protected ISisServiceTransport createTransport() {
            return new TestSisServiceTransport();
        }
    }
}
