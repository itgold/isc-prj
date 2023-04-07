package com.iscweb.component.web.controller.rest;

import com.google.common.collect.Lists;
import com.iscweb.common.events.integration.OnDemandDeviceSyncEvent;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.dto.entity.core.SpeakerDto;
import com.iscweb.common.service.IEventHub;
import com.iscweb.component.web.controller.BaseInternalApiController;
import com.iscweb.component.web.util.WebUtil;
import com.iscweb.service.AdminService;
import com.iscweb.service.CameraService;
import com.iscweb.service.DoorService;
import com.iscweb.service.SpeakerService;
import com.iscweb.service.composite.CompositeService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.iscweb.common.security.ApplicationSecurity.ADMINISTRATORS_PERMISSION;

/**
 * REST API endpoint for tasks that are restricted to administrators.
 */
@SuppressWarnings("checkstyle:LineLength")
@Slf4j
@RestController
@RequestMapping("/rest/admin")
@PreAuthorize(ADMINISTRATORS_PERMISSION)
public class AdminController extends BaseInternalApiController<AdminService> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorService doorService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SpeakerService speakerService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CameraService cameraService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CompositeService compositeService;

    /**
     * Administrative endpoint to do on demand sync with all device integrations.
     *
     * @return OK if everything went well.
     */
    @PostMapping(value = "/integration/sync/all")
    public String triggerAllSync(Principal principal) {
        String result = WebUtil.StringResponseCode.SUCCESS.name();
        try {
            getEventHub().post(new OnDemandDeviceSyncEvent(principal.getName(),
                    Arrays.asList(EntityType.values()),
                    UUID.randomUUID().toString()));
        } catch (ServiceException e) {
            log.error("Unable to trigger new on-demand sync request", e);
            result = WebUtil.StringResponseCode.FAILURE.name();
        }

        return result;
    }

    /**
     * Administrative endpoint to do on demand sync with all door integrations.
     *
     * @return OK if everything went well.
     */
    @PostMapping(value = "/integration/sync/doors")
    public String triggerDoorsSync(Principal principal) {
        String result = WebUtil.StringResponseCode.SUCCESS.name();
        try {
            getEventHub().post(new OnDemandDeviceSyncEvent(principal.getName(),
                    Lists.newArrayList(EntityType.DOOR),
                    UUID.randomUUID().toString()));
        } catch (ServiceException e) {
            log.error("Unable to trigger new on-demand sync request", e);
            result = WebUtil.StringResponseCode.FAILURE.name();
        }

        return result;
    }

    /**
     * Administrative endpoint to do on demand sync with all users integrations.
     *
     * @return OK if everything went well.
     */
    @PostMapping(value = "/integration/sync/users")
    public String triggerUsersSync(Principal principal) {
        String result = WebUtil.StringResponseCode.SUCCESS.name();
        try {
            getEventHub().post(new OnDemandDeviceSyncEvent(principal.getName(),
                    Lists.newArrayList(EntityType.USER),
                    UUID.randomUUID().toString()));
        } catch (ServiceException e) {
            log.error("Unable to trigger new on-demand sync request", e);
            result = WebUtil.StringResponseCode.FAILURE.name();
        }

        return result;
    }

    /**
     * Administrative endpoint to do on demand sync with all camera integrations.
     *
     * @return OK if everything went well.
     */
    @PostMapping(value = "/integration/sync/cameras")
    public String triggerCamerasSync(Principal principal) {
        String result = WebUtil.StringResponseCode.SUCCESS.name();
        try {
            getEventHub().post(new OnDemandDeviceSyncEvent(principal.getName(),
                    Lists.newArrayList(EntityType.CAMERA),
                    UUID.randomUUID().toString()));
        } catch (ServiceException e) {
            log.error("Unable to trigger new on-demand sync request", e);
            result = WebUtil.StringResponseCode.FAILURE.name();
        }
        
        return result;
    }

    /**
     * Administrative endpoint to force a composite tree refresh
     *
     * @return OK if everything went well.
     */
    @PostMapping(value = "/compositeTree/rebuild")
    public String triggerCompositeTreeRebuild(Principal principal) {
        String result = WebUtil.StringResponseCode.SUCCESS.name();
        try {
            compositeService.invalidateCache();
        } catch (Exception e) {
            log.error("Unable to trigger composite tree rebuild", e);
            result = WebUtil.StringResponseCode.FAILURE.name();
        }

        return result;
    }

    /**
     * Administrative endpoint to do on demand sync with all radios integrations.
     *
     * @return OK if everything went well.
     */
    @PostMapping(value = "/integration/sync/radios")
    public String triggerRadiosSync(Principal principal) {
        String result = WebUtil.StringResponseCode.SUCCESS.name();
        try {
            getEventHub().post(new OnDemandDeviceSyncEvent(principal.getName(),
                    Lists.newArrayList(EntityType.RADIO),
                    UUID.randomUUID().toString()));
        } catch (ServiceException e) {
            log.error("Unable to trigger new on-demand sync request", e);
            result = WebUtil.StringResponseCode.FAILURE.name();
        }

        return result;
    }

    /**
     * Administrative endpoint to trigger emails if for any reason emails were not triggered
     * properly (e.g the app being down). The method that is called is asynchronous and its
     * progress should be monitored on the logs.
     *
     * @return confirmation message notifications are underway
     */
    @ApiOperation(value = "Trigger bulk email notifications - administrative endpoint")
    @GetMapping(value = "/users/inactivity/trigger-emails")
    public String processUserInactivityNotifications() {
        getService().asyncEmailInactivity();
        return "Email notifications underway";
    }

    /**
     * Rest service endpoint to assign doors to corresponding static regions automatically based on naming conventions.
     * @return a list of assigned door ids.
     */
    @RequestMapping(value = "/assign-doors", method = RequestMethod.POST)
    public String assignDoors() {
        StringBuilder result = new StringBuilder();

        int page = -1;
        final int pageSize = 50;
        List<DoorDto> pageDoors;
        do {
            pageDoors = getDoorService().findAll(PageRequest.of(++page, pageSize, Sort.unsorted()));
            pageDoors.forEach(door -> {
                try {
                    final DoorDto deviceDto = getDoorService().updateDoorParents(door);
                    result.append(deviceDto.getId()).append(" ");
                } catch (Exception e) {
                    log.error("Device hierarchy resolution error", e);
                    result.append("*").append(door.getId()).append("*").append(" ");
                }
            });
        } while (pageDoors.size() >= pageSize);

        return result.toString();
    }

    /**
     * Rest service endpoint to assign speakers to corresponding static regions automatically based on naming conventions.
     * @return a list of assigned speaker ids.
     */
    @RequestMapping(value = "/assign-speakers", method = RequestMethod.POST)
    public String assignSpeakers() {
        StringBuilder result = new StringBuilder();

        int page = -1;
        final int pageSize = 50;
        List<SpeakerDto> pageSpeakers;

        do {
            pageSpeakers = getSpeakerService().findAll(PageRequest.of(++page, pageSize, Sort.unsorted()));
            pageSpeakers.forEach(speaker -> {
                try {
                    final SpeakerDto deviceDto = getSpeakerService().updateSpeakerParents(speaker);
                    result.append(deviceDto.getId()).append(" ");
                } catch (Exception e) {
                    log.error("Device hierarchy resolution error", e);
                    result.append("*").append(speaker.getId()).append("*").append(" ");
                }
            });
        } while (pageSpeakers.size() >= pageSize);

        return result.toString();
    }

    @SuppressWarnings("CheckStyle")
    // Example: curl -X POST -iX -H 'Content-Type: application/json' -
    // H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJhdWQiOiIxIiwic3ViIjoic3lzdGVtLWFkbWluQGlzY3dlYi5pbyIsInNjb3BlcyI6WyJST0xFX1NZU1RFTV9BRE1JTklTVFJBVE9SIl0sImlzcyI6Im
    // h0dHA6Ly9sb2NhbC5pc2N3ZWIuaW8iLCJpYXQiOjE2NDYwOTE3MDgsImV4cCI6MTY0NjA5MjMwOH0.l00Vc2Of0OC6W_3HTnqzAQ3dn5yp1x2Bv4KbXAmA_nrLkJ1uYzapIiZLdusY7YjbOJstdlUbiV4kJWGIqI001w'
    // -X GET http://localhost:9090/rest/admin/assign-cameras
    @RequestMapping(value = "/assign-cameras", method = RequestMethod.POST)
    public String assignCameras() {
        StringBuilder result = new StringBuilder();

        int page = -1;
        final int pageSize = 50;
        List<CameraDto> pageCameras;

        do {
            pageCameras = getCameraService().findAll(PageRequest.of(++page, pageSize, Sort.unsorted()));
            pageCameras.forEach(cameraDto -> {
                try {
                    final CameraDto deviceDto = getCameraService().updateCameraGroups(cameraDto);
                    result.append(deviceDto.getId()).append(" ");
                } catch (ServiceException e) {
                    log.error("Device hierarchy resolution error", e);
                    result.append("*").append(cameraDto.getId()).append("*").append(" ");
                }
            });
        } while (pageCameras.size() >= pageSize);

        return result.toString();
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
