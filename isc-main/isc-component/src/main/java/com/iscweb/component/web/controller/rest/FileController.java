package com.iscweb.component.web.controller.rest;

import com.iscweb.common.exception.UnableToConnectException;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.service.integration.camera.ICameraService;
import com.iscweb.common.service.integration.camera.ICameraStream;
import com.iscweb.common.util.IoUtils;
import com.iscweb.component.web.controller.BaseInternalApiController;
import com.iscweb.service.CameraService;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Controller that supports portal interactions with files.
 */
@Slf4j
@RestController
@Api("Endpoints for file-related operations")
@RequestMapping("/rest")
public class FileController extends BaseInternalApiController<CameraService> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ICameraService cameraService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CameraService cameraBusinessService;

    @GetMapping(value = "/camera/{cameraId}/stream/{streamId}")
    @ResponseBody
    public void streamResource(
            @PathVariable(value = "cameraId") String cameraId,
            @DefaultValue("") @PathVariable(value = "streamId") String streamId,
            HttpServletResponse response) throws Exception {
        streamCamera(cameraId, streamId, response);
    }

    @GetMapping(value = "/camera/{cameraId}/stream")
    @ResponseBody
    public void streamResource(
            @PathVariable(value = "cameraId") String cameraId,
            HttpServletResponse response) throws Exception {
        streamCamera(cameraId, null, response);
    }

    private void streamCamera(String cameraId, String streamId, HttpServletResponse response) throws Exception {
        int numberOfBytesToWrite;
        byte[] data = new byte[IoUtils.BUFFER_SIZE];

        CameraDto camera = getCameraBusinessService().findByGuid(cameraId, List.of());
        String cameraDeviceId = camera != null ? camera.getExternalId() : null;
        if (cameraDeviceId != null) {
            try (final ICameraStream cameraStream = cameraService.streamVideo(cameraDeviceId, streamId); OutputStream outputStream = response.getOutputStream()) {
                if (cameraStream != null) {
                    // set the proper content type for MJPG
                    response.setContentType("multipart/x-mixed-replace; boundary=--BoundaryString");

                    final InputStream inputStream = cameraStream.stream();
                    while ((numberOfBytesToWrite = inputStream.read(data, 0, data.length)) != -1) {
                        outputStream.write(data, 0, numberOfBytesToWrite);
                        outputStream.flush();
                    }
                } else {
                    response.setContentType("image/jpeg");
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (IOException e) {
                log.trace("Client for a video stream got disconnected, Camera id: {}", cameraId, e);
            } catch (UnableToConnectException e) {
                response.setContentType("image/jpeg");
                if (log.isTraceEnabled()) {
                    log.trace("Unable to connect to a camera {}", cameraId, e);
                } else {
                    log.info("Unable to connect to a camera {}", cameraId);
                }
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } catch (Exception e) {
                response.setContentType("image/jpeg");
                log.error("Unable to stream video from camera {}", cameraId, e);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setContentType("image/jpeg");
            log.warn("Unable to find a camera with id {}", cameraId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        log.debug("Video streaming for camera {} has stopped.", cameraId);
    }

    /*
    private static final AtomicInteger STREAMS_COUNTER = new AtomicInteger(0);

    @GetMapping(value = "/camera/{cameraId}/stream")
    public ResponseEntity<StreamingResponseBody> streamResource(@PathVariable(value = "cameraId") String cameraId) throws Exception {
        String streamId = null;
        ICamera camera = getCameraBusinessService().findByGuid(cameraId);
        String cameraDeviceId = camera != null ? camera.getExternalId() : null;
        if (cameraDeviceId != null) {
            try {
                final ICameraStream cameraStream = cameraService.streamVideo(cameraDeviceId, streamId);
                StreamingResponseBody responseBody = out -> {
                    try (cameraStream; final InputStream inputStream = cameraStream.stream()) {
                        if (cameraStream != null) {
                            log.debug("!!! START STREAM {}, camera: {} !!!", STREAMS_COUNTER.incrementAndGet(), cameraId);

                            int numberOfBytesToWrite;
                            byte[] data = new byte[IoUtils.BUFFER_SIZE];

                            while ((numberOfBytesToWrite = inputStream.read(data, 0, data.length)) != -1) {
                                out.write(data, 0, numberOfBytesToWrite);
                                out.flush();
                            }
                        }
                    } catch (Exception e) {
                        if (log.isTraceEnabled()) {
                            log.trace("Client for a video stream got disconnected, Camera id: {}", cameraId, e);
                        } else {
                            log.info("Client for a video stream got disconnected, Camera id: {}", cameraId);
                        }
                    } finally {
                        log.debug("!!! STOP STREAM {}, camera: {} !!!", STREAMS_COUNTER.decrementAndGet(), cameraId);
                    }
                };

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("multipart/x-mixed-replace; boundary=--BoundaryString"))
                        .body(responseBody);
            } catch (UnableToConnectException e) {
                if (log.isTraceEnabled()) {
                    log.trace("Unable to connect to a camera {}", cameraId, e);
                } else {
                    log.info("Unable to connect to a camera {}", cameraId);
                }
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                log.error("Unable to stream video from camera {}", cameraId, e);
                return ResponseEntity.internalServerError().build();
            }
        }

        return ResponseEntity.notFound().build();
    }
    */

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
