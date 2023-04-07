package com.isc.integration.mip;

import com.iscweb.integration.cameras.mip.dto.CameraInfoDto;
import com.iscweb.integration.cameras.mip.events.CameraSyncEvent;
import com.iscweb.integration.cameras.mip.events.CameraSyncPayload;
import com.mip.command.ArrayOfStreamInfo;
import com.mip.command.CameraGroupInfo;
import com.mip.command.CameraInfo;
import com.mip.command.CameraSecurityInfo;
import com.mip.command.StreamInfo;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;

public class EventsTest {
    @Test
    public void testSyncEvent() throws URISyntaxException {
        URI imageServiceUri = new URI("http://TheHost:9999/context");
        CameraInfo info = new CameraInfo();
        info.setDeviceId("eeeeeeee-eeee-eeee-eeee-b6c15f75adf0");
        info.setName("TEST CAMERA#1 Name");
        info.setDescription("TEST CAMERA#1 Description");
        CameraSecurityInfo securityInfo = new CameraSecurityInfo();
        securityInfo.setLive(Boolean.TRUE);
        info.setCameraSecurity(securityInfo);
        ArrayOfStreamInfo streamInfo = new ArrayOfStreamInfo();
        StreamInfo stream = new StreamInfo();
        stream.setStreamId("Stream Id");
        stream.setName("Test Stream");
        stream.setDefault(true);
        streamInfo.getStreamInfo().add(stream);
        info.setStreams(streamInfo);

        CameraGroupInfo group = new CameraGroupInfo();
        group.setGroupId("GroupID");
        group.setName("Group Name");
        group.setDescription("Group Description");

        CameraInfoDto camera = new CameraInfoDto(info.getDeviceId(), info, group, null, imageServiceUri);
        CameraSyncEvent event = new CameraSyncEvent();
        event.setExternalEntityId(camera.getCameraId());
        event.setEventTime(ZonedDateTime.now());
        event.setPayload(new CameraSyncPayload(camera));
        // getEventHub().post(event);
    }
}
