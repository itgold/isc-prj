package com.isc.integration.mip

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import com.google.common.collect.Lists
import com.iscweb.integration.cameras.mip.dto.CameraInfoDto
import com.iscweb.integration.cameras.mip.dto.ProxyParamsDto
import com.iscweb.integration.cameras.mip.events.BuiltInEventTypes
import com.iscweb.integration.cameras.mip.services.MipCameraServiceFactory
import com.iscweb.integration.cameras.mip.services.MipEventsListener
import com.iscweb.integration.cameras.mip.services.MipTokenService
import com.iscweb.integration.cameras.mip.services.mip.IRecorderStatusService
import com.iscweb.integration.cameras.mip.services.mip.IServerCommandService
import com.iscweb.integration.cameras.mip.services.streaming.ImageServerConnection
import com.iscweb.integration.cameras.mip.services.streaming.commands.ConnectCommand
import com.iscweb.integration.cameras.mip.services.streaming.commands.ConnectResponse
import com.iscweb.integration.config.Config20MipIntegration
import com.mip.recorderStatus.ArrayOfCameraDeviceStatus
import com.mip.recorderStatus.CameraDeviceStatus
import com.mip.recorderStatus.ConfigurationChangedStatus
import com.mip.recorderStatus.EventStatus
import com.mip.recorderStatus.InputDeviceStatus
import com.mip.recorderStatus.KeyValue
import com.mip.recorderStatus.MicrophoneDeviceStatus
import com.mip.recorderStatus.OutputDeviceStatus
import com.mip.recorderStatus.SpeakerDeviceStatus
import com.mip.recorderStatus.Status
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.CollectionUtils

import java.util.concurrent.atomic.AtomicInteger

/**
 * This class is used for experiments when researching Milestone API and the way to deal with it.
 */
class NotATest {
    static final String TEST_MIPS_SERVER = '192.168.1.199'
    static final String TEST_MIPS_SERVER_USER = 'denis'
    static final String TEST_MIPS_SERVER_PWD = 'Welcome1!'

    static final int BUFFER_SIZE = 16384

    static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.apache.cxf");
        rootLogger.setLevel(Level.ERROR);

        // tryImageService();
        // tryStatusService();
        // testMipEventsListenerService()
        listCameras()
    }

    private static void tryImageService() {
        def cameraServiceFactory = new MipCameraServiceFactory()
        cameraServiceFactory.address = TEST_MIPS_SERVER
        cameraServiceFactory.port = 8081

        def tokenService = new MipTokenService()
        tokenService.address = TEST_MIPS_SERVER
        tokenService.port = 80
        tokenService.username = TEST_MIPS_SERVER_USER
        tokenService.password = TEST_MIPS_SERVER_PWD
        tokenService.serviceFactory = cameraServiceFactory
        tokenService.refreshLoginToken()

        ImageServerConnection connection = new ImageServerConnection()
        connection.connect(TEST_MIPS_SERVER, 7563, false)

        ConnectCommand connectCommand = new ConnectCommand()
        connectCommand.setConnectParameters('c540bd14-5380-477e-bb50-de237110642d',
                '28dc44c3-079e-4c94-8ec9-60363451eb40', tokenService.currentToken())
        connectCommand.setAlwaysStdJpeg(true)
        ConnectResponse response = connection.sendCommand(connectCommand, ConnectResponse.class)
        if (response.isConnected()) {
            InputStream is
            try {
                is = connection.startStreaming()

                byte[] buffer = new byte[BUFFER_SIZE]
                int readBytesCount
                while ((readBytesCount = is.read(buffer)) != -1) {
                    System.out.println("Read $readBytesCount bytes ...")
                }
            } finally {
                if (is) {
                    is.close()
                }
            }
        } else {
            System.out.println("Unable to connect to the Milestone Image Service.")
        }
    }

    private static void tryStatusService() {
        // https://www.milestonesys.com/globalassets/techcomm/2018-r3/advvms/english-united-states/index.htm?toc.htm?9697.htm
        def cameraServiceFactory = new MipCameraServiceFactory()
        cameraServiceFactory.address = TEST_MIPS_SERVER
        cameraServiceFactory.port = 8081

        def tokenService = new MipTokenService()
        tokenService.address = TEST_MIPS_SERVER
        tokenService.port = 80
        tokenService.username = TEST_MIPS_SERVER_USER
        tokenService.password = TEST_MIPS_SERVER_PWD
        tokenService.serviceFactory = cameraServiceFactory
        tokenService.refreshLoginToken()

        final IRecorderStatusService service = cameraServiceFactory.createJaxWsProxy(
                new ProxyParamsDto(false, TEST_MIPS_SERVER, 7563, "/RecorderStatusService/RecorderStatusService2.asmx", TEST_MIPS_SERVER_USER, TEST_MIPS_SERVER_PWD),
                IRecorderStatusService.class)

        final TestMipCameraService cameraService = new TestMipCameraService(cameraServiceFactory, tokenService, TEST_MIPS_SERVER, TEST_MIPS_SERVER_USER, TEST_MIPS_SERVER_PWD)

        String sessionId = service.startStatusSession(tokenService.currentToken())
        System.out.println("")
        System.out.println("======================================")
        System.out.println("New status listening session id: " + sessionId)
        System.out.println("======================================")

        // Set up to subscribe to all the user defined events on that recording server
        List<String> subscribeTheseEventIds = Lists.newArrayList()
        List<String> subscribeTheseDeviceIds = Lists.newArrayList()

        /*
        The code below is missing. I can't implement it using Soap because of conflict with Soap 1.1 or Soap 1.2
        It seems that IConfigurationService is requiring Soap 1.2 but it still doesn't work when enforced to use by CXF

        final IConfigurationService configService = cameraServiceFactory.createJaxWsProxySoap12(
        new ProxyParamsDto(false, TEST_MIPS_SERVER, 80, "/ManagementServer/ConfigurationApiService.svc", TEST_MIPS_SERVER_USER, TEST_MIPS_SERVER_PWD),
        IConfigurationService.class)

        ConfigurationItem item = configService.getItem(MipConfigItemTypes.getRootPath())
        item = configService.getItem(MipConfigItemTypes.RoleFolder.getPath())
        item = configService.getItem(MipConfigItemTypes.LayoutGroupFolder.getPath())
        item = configService.getItem(MipConfigItemTypes.VideoWallFolder.getPath())
        item = configService.getItem(MipConfigItemTypes.MIPKindFolder.getPath())
        item = configService.getItem(MipConfigItemTypes.RecordingServerFolder.getPath())
        item = configService.getItem(MipConfigItemTypes.CameraFolder.getPath())

        // ================================
        The original sample .NET code:
        Item serverItem = Configuration.Instance.GetItem(EnvironmentManager.Instance.CurrentSite);
        List<Item> serverItems = serverItem.GetChildren();
        Item recorder = null;
        foreach (Item item in serverItems)
        {
            if (item.FQID.Kind == Kind.Server && item.FQID.ServerId.ServerType == ServerId.CorporateRecordingServerType)
            {
                recorder = item;
            }
        }
        List<Item> allItems = recorder.GetChildren();

        List<Item> allEvents = FindAllEvents(allItems, serverItem.FQID.ObjectId);
        foreach (Item item in allEvents)
        {
            subscribeTheseEventIds.Add(item.FQID.ObjectId);
            subscribeTheseDeviceIds.Add(item.FQID.ObjectId);
        }
        allEvents = FindAllEvents(serverItems, serverItem.FQID.ObjectId);
        foreach (Item item in allEvents)
        {
            subscribeTheseEventIds.Add(item.FQID.ObjectId);
            subscribeTheseDeviceIds.Add(item.FQID.ObjectId);
        }
        */

        // Set up to subscribe to almost all built-in events
        subscribeTheseEventIds.addAll(Lists.newArrayList(
                // BuiltInEventTypes.ArchiveDiskAvailable.getGuid(),
                // BuiltInEventTypes.ArchiveDiskUnavailable.getGuid(),
                BuiltInEventTypes.CommunicationError.getGuid(),
                BuiltInEventTypes.CommunicationStarted.getGuid(),
                BuiltInEventTypes.CommunicationStopped.getGuid(),
                // BuiltInEventTypes.DatabaseDiskAvailable.getGuid(),
                // BuiltInEventTypes.DatabaseDiskFull.getGuid(),
                // BuiltInEventTypes.DatabaseDiskUnavailable.getGuid(),
                // BuiltInEventTypes.DatabaseRepair.getGuid(),
                BuiltInEventTypes.DeviceSettingsChanged.getGuid(),
                BuiltInEventTypes.DeviceSettingsChangedError.getGuid(),
                // BuiltInEventTypes.FeedOverflowStarted.getGuid(),
                // BuiltInEventTypes.FeedOverflowStopped.getGuid(),
                BuiltInEventTypes.HardwareSettingsChanged.getGuid(),
                BuiltInEventTypes.HardwareSettingsChangedError.getGuid(),
                // BuiltInEventTypes.InputActivated.getGuid(),
                // BuiltInEventTypes.InputChanged.getGuid(),
                // BuiltInEventTypes.InputDeactivated.getGuid(),
                BuiltInEventTypes.LiveClientFeedRequested.getGuid(),
                BuiltInEventTypes.LiveClientFeedTerminated.getGuid(),
                BuiltInEventTypes.MotionStarted.getGuid(),
                BuiltInEventTypes.MotionStopped.getGuid(),
                // BuiltInEventTypes.OutputActivated.getGuid(),
                // BuiltInEventTypes.OutputChanged.getGuid(),
                // BuiltInEventTypes.OutputDeactivated.getGuid(),
                // BuiltInEventTypes.PTZManualSessionStarted.getGuid(),
                // BuiltInEventTypes.PTZManualSessionStopped.getGuid(),
                BuiltInEventTypes.RecordingStarted.getGuid(),
                BuiltInEventTypes.RecordingStopped.getGuid()
        ))

        com.mip.recorderStatus.ArrayOfGuid ids = new com.mip.recorderStatus.ArrayOfGuid()
        ids.getGuid().addAll(subscribeTheseEventIds)
        service.subscribeEventStatus(tokenService.currentToken(), sessionId, ids)

        // Subscribe to configuration changes
        // Every time a change occurs, you will be informed next time you query the status
        service.subscribeConfigurationStatus(tokenService.currentToken(), sessionId, true)

        // Set up to subscribe to device descriptions
        // First time you query the status, you will get information on all devices subscribed to
        // Subsequent queries will return changed values only
        // You may subscribe to a device once again to force information to be returned on that device
        List<String> cameraIds = cameraService.resolveCameraIds()
        if (!CollectionUtils.isEmpty(cameraIds)) {
            subscribeTheseDeviceIds.addAll(cameraIds);
            com.mip.recorderStatus.ArrayOfGuid guidList = new com.mip.recorderStatus.ArrayOfGuid()
            guidList.getGuid().addAll(subscribeTheseDeviceIds)
            service.subscribeDeviceStatus(tokenService.currentToken(), sessionId, guidList)
        }

        // Now we are ready to enter a loop querying for status information with 2 seconds sleep after each
        final long queryPeriod = 2000;
        final AtomicInteger counter = new AtomicInteger(30)
        Thread processingThread = new Thread(new Runnable() {
            @Override
            void run() {
                while(counter.getAndDecrement() > 0) {
                    Status stats = service.getStatus(tokenService.currentToken(), sessionId, 5000)
                    if (stats.getEventStatusArray() != null && !CollectionUtils.isEmpty(stats.getEventStatusArray().getEventStatus())) {
                        for (EventStatus event : stats.getEventStatusArray().getEventStatus()) {
                            System.out.println("MIP EVENT: " + event.getTime().toString() + " Event " + event.getEventId() + " guid " + event.getEventId() + " Source " + event.getSourceId())
                            if (event.getMetadata() != null && !CollectionUtils.isEmpty(event.getMetadata().getKeyValue())) {
                                for (KeyValue keyValue : event.getMetadata().getKeyValue()) {
                                    System.out.println("MIP EVENT: " + "    ---> Metadata:  " + keyValue.getKey() + " = " + keyValue.getValue())
                                }
                            }
                        }
                    }

                    ConfigurationChangedStatus confChangeEvent = stats.getConfigurationChangedStatus()
                    if (confChangeEvent != null) {
                        System.out.println("MIP EVENT: " + confChangeEvent.getTime().toString() + " Configuration change ");
                        confChangeEvent.get
                    }

                    if (stats.getSpeakerDeviceStatusArray() != null && !CollectionUtils.isEmpty(stats.getSpeakerDeviceStatusArray().getSpeakerDeviceStatus())) {
                        for (SpeakerDeviceStatus event : stats.getSpeakerDeviceStatusArray().getSpeakerDeviceStatus()) {
                            System.out.println("MIP EVENT: " + event.getTime().toString() + " Speaker " + event.getDeviceId() + " Started " + event.isStarted())

                            if (!CollectionUtils.isEmpty(event.getMetaPropertyValues())) {
                                for (PropertyValue keyValue : event.getMetaPropertyValues()) {
                                    System.out.println("MIP EVENT: " + "    ---> Metadata:  " + keyValue.getName() + " = " + keyValue.getValue())
                                }
                            }
                        }
                    }

                    if (stats.getOutputDeviceStatusArray() != null && !CollectionUtils.isEmpty(stats.getOutputDeviceStatusArray().getOutputDeviceStatus())) {
                        for (OutputDeviceStatus event : stats.getOutputDeviceStatusArray().getOutputDeviceStatus()) {
                            System.out.println("MIP EVENT: " + event.getTime().toString() + " Output " + event.getDeviceId() +
                                    " Started " + event.isStarted() +
                                    " State " + (event.getState() != null ? event.getState().value() : "UNKNOWN"))
                        }
                    }

                    if (stats.getCameraDeviceStatusArray() != null && !CollectionUtils.isEmpty(stats.getCameraDeviceStatusArray().getCameraDeviceStatus())) {
                        for (CameraDeviceStatus event : stats.getCameraDeviceStatusArray().getCameraDeviceStatus()) {
                            System.out.println("MIP EVENT: " + event.getTime().toString() + " Camera " + event.getDeviceId() +
                                    "\n    Started " + event.isStarted() +
                                    "\n    Recording " + event.isRecording() +
                                    "\n    Motion " + event.isMotion() +
                                    "\n    No connection " + event.isErrorNoConnection() +
                                    "\n    Error " + event.isError() +
                                    "\n    isChange " + event.isIsChange())

//                            motion = false
//                            recording = false
//                            dbMoveInProgress = false
//                            errorOverflow = false
//                            errorWritingGop = false
//                            dbRepairInProgress = false
//                            deviceId = "c5db0ac9-bbf4-4764-9257-1c2b1cec812c"
//                            isChange = true
//                            enabled = true
//                            started = true
//                            error = true
//                            errorNotLicensed = false
//                            errorNoConnection = true
                        }
                    }

                    if (stats.getMicrophoneDeviceStatusArray() != null && !CollectionUtils.isEmpty(stats.getMicrophoneDeviceStatusArray().getMicrophoneDeviceStatus())) {
                        for (MicrophoneDeviceStatus event : stats.getMicrophoneDeviceStatusArray().getMicrophoneDeviceStatus()) {
                            System.out.println("MIP EVENT: " + event.getTime().toString() + " Microphone " + event.getDeviceId() +
                                    " Started " + event.isStarted() +
                                    " Error " + event.isError())
                        }
                    }

                    if (stats.getInputDeviceStatusArray() != null && !CollectionUtils.isEmpty(stats.getInputDeviceStatusArray().getInputDeviceStatus())) {
                        for (InputDeviceStatus event : stats.getInputDeviceStatusArray().getInputDeviceStatus()) {
                            System.out.println("MIP EVENT: " + event.getTime().toString() + " Input " + event.getDeviceId() +
                                    " Started " + event.isStarted())
                        }
                    }

                    System.out.println("MIP EVENT: " + String.format("%d queries remaining", counter.get()))
                    Thread.sleep(queryPeriod)
                }
            }
        })

        processingThread.start()
        processingThread.join()

        service.stopStatusSession(tokenService.currentToken(), sessionId)
    }

    private static void testMipEventsListenerService() {
        // https://www.milestonesys.com/globalassets/techcomm/2018-r3/advvms/english-united-states/index.htm?toc.htm?9697.htm
        def cameraServiceFactory = new MipCameraServiceFactory()
        cameraServiceFactory.address = TEST_MIPS_SERVER
        cameraServiceFactory.port = 8081

        def tokenService = new MipTokenService()
        tokenService.address = TEST_MIPS_SERVER
        tokenService.port = 80
        tokenService.username = TEST_MIPS_SERVER_USER
        tokenService.password = TEST_MIPS_SERVER_PWD
        tokenService.serviceFactory = cameraServiceFactory
        tokenService.refreshLoginToken()

        final TestMipCameraService cameraService = new TestMipCameraService(cameraServiceFactory, tokenService, TEST_MIPS_SERVER, TEST_MIPS_SERVER_USER, TEST_MIPS_SERVER_PWD)
        final IRecorderStatusService service = cameraServiceFactory.createJaxWsProxy(
                new ProxyParamsDto(false, TEST_MIPS_SERVER, 7563, "/RecorderStatusService/RecorderStatusService2.asmx", TEST_MIPS_SERVER_USER, TEST_MIPS_SERVER_PWD),
                IRecorderStatusService.class)

        final MipEventsListener listenerService = new MipEventsListener()
        listenerService.setCameraService(cameraService)

        final long queryPeriod = 2000;
        final AtomicInteger counter = new AtomicInteger(30)
        Thread processingThread = new Thread(new Runnable() {
            @Override
            void run() {
                while(counter.getAndDecrement() > 0) {
                    listenerService.checkStats()

                    System.out.println("MIP EVENT: " + String.format("%d queries remaining", counter.get()))
                    Thread.sleep(queryPeriod)
                }
            }
        })

        processingThread.start()
        processingThread.join()
    }

    private static void listCameras() {
        // https://www.milestonesys.com/globalassets/techcomm/2018-r3/advvms/english-united-states/index.htm?toc.htm?9697.htm
        Config20MipIntegration.disableCertificateVerification()

        def cameraServiceFactory = new MipCameraServiceFactory()
        cameraServiceFactory.address = TEST_MIPS_SERVER
        cameraServiceFactory.port = 8081

        IServerCommandService serverCommandService = cameraServiceFactory.createJaxWsProxy(
                new ProxyParamsDto(true, TEST_MIPS_SERVER, 443, "/ManagementServer/ServerCommandService.svc", TEST_MIPS_SERVER_USER, TEST_MIPS_SERVER_PWD),
                IServerCommandService.class)
        IRecorderStatusService statusService = cameraServiceFactory.createJaxWsProxy(
                new ProxyParamsDto(false, TEST_MIPS_SERVER, 7563, "/RecorderStatusService/RecorderStatusService2.asmx", TEST_MIPS_SERVER_USER, TEST_MIPS_SERVER_PWD),
                IRecorderStatusService.class)

        def tokenService = new MipTokenService()
        tokenService.setServerCommandService(serverCommandService)
        tokenService.refreshLoginToken()

        final TestMipCameraService cameraService = new TestMipCameraService(cameraServiceFactory, tokenService, TEST_MIPS_SERVER, TEST_MIPS_SERVER_USER, TEST_MIPS_SERVER_PWD)
        List<CameraInfoDto> cameras = cameraService.listCameras()

        com.mip.recorderStatus.ArrayOfGuid deviceIds = new com.mip.recorderStatus.ArrayOfGuid()
        deviceIds.guid = []
        cameras.forEach {
            deviceIds.guid.add(it.cameraId)
        }

        com.mip.recorderStatus.Status status = statusService.getCurrentDeviceStatus(tokenService.currentToken(),deviceIds )

        System.out.println("Cameras count: " + cameras.size())
        ArrayOfCameraDeviceStatus statuses = status.getCameraDeviceStatusArray()
        statuses.getCameraDeviceStatus().forEach {
            System.out.println("Camera: " + it.deviceId)
            System.out.println("    Disconnected: " + it.error)
            System.out.println("    Motion: " + it.motion)
            System.out.println("    Recording: " + it.recording)
        }
    }
}
