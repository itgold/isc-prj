package com.isc.integration.mip;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.integration.cameras.mip.dto.CameraInfoDto;
import com.iscweb.integration.cameras.mip.dto.ProxyParamsDto;
import com.iscweb.integration.cameras.mip.services.MipCameraService;
import com.iscweb.integration.cameras.mip.services.MipCameraServiceFactory;
import com.iscweb.integration.cameras.mip.services.MipTokenService;
import com.iscweb.integration.cameras.mip.services.mip.IServerCommandService;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestMipCameraService extends MipCameraService {
    private final List<IEvent<ITypedPayload>> events = com.google.common.collect.Lists.newArrayList();

    public TestMipCameraService(MipCameraServiceFactory cameraServiceFactory, MipTokenService tokenService, String serverHost, String username, String password) {
        IServerCommandService serverCommandService = cameraServiceFactory.createJaxWsProxy(
                new ProxyParamsDto(true, serverHost, 443, "/ManagementServer/ServerCommandService.svc", username, password),
                IServerCommandService.class);
        setServerCommandService(serverCommandService);
        setTokenService(tokenService);
    }

    List<String> resolveCameraIds() throws ServiceException {
        final List<String> ids = Lists.newArrayList();
        Map<String, CameraInfoDto> cameras = listCamerasInternal();
        if (cameras != null && cameras.size() > 0) {
            ids.addAll(cameras.values().stream().map(camera -> camera.getCameraId()).collect(Collectors.toList()));
        }

        return ids;
    }

//    @Override
//    protected void postEvents(List<IEvent<ITypedPayload>> events) throws ServiceException {
//        this.events.addAll(events);
//    }
}
