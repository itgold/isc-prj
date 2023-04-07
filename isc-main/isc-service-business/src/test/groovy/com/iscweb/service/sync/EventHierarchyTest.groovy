package com.iscweb.service.sync

import com.iscweb.common.annotations.EventPath
import com.iscweb.common.events.BaseApplicationEvent
import com.iscweb.common.events.CommonEventTypes
import com.iscweb.common.exception.ServiceException
import com.iscweb.common.model.event.IEvent
import com.iscweb.common.model.event.camera.BaseCameraEvent
import com.iscweb.common.model.event.camera.CameraEventPayload
import com.iscweb.common.service.eventhub.BaseEventHub
import com.iscweb.common.util.EventUtils
import spock.lang.Specification

/**
 *
 * Date: 1/7/16
 * @author skurenkov
 */
class EventHierarchyTest extends Specification {

    def "generate Event Path #1"() {
        when:
            def cameraEvent = new CameraConnectedEvent('TEST')
            def eventPath = cameraEvent.eventPath
            def enetSubscribePath = EventUtils.generateEventPath(CameraConnectedEvent)
        then:
            eventPath != null
            enetSubscribePath != null
            eventPath == 'event.app.camera.connected'
            enetSubscribePath == 'event.app.camera.connected*'
    }

    def "test subscriptions"() {
        when:
            def events1 = []
            def events3 = []
            def eventHub = new TestEventHub()
            eventHub.register(CameraConnectedEvent.class, { event -> events1 << event })
            eventHub.register(BaseApplicationEvent.class, { event -> events3 << event })

            eventHub.post(new CameraConnectedEvent('TEST1'))
            eventHub.post(new CameraDisconnectedEvent('TEST2'))

        then:
            events1.size() == 1
            events3.size() == 2
            events1.get(0) instanceof CameraConnectedEvent
    }

    private static class TestEventHub extends BaseEventHub {
        @Override
        void post(IEvent<?> event) throws ServiceException {
            notifySubscribers(event);
        }
    }

    @EventPath(path = CameraConnectedEvent.PATH)
    class CameraConnectedEvent extends BaseCameraEvent<CameraEventPayload> {

        public static final String PATH = "connected"

        CameraConnectedEvent() {
            super(null)
        }

        CameraConnectedEvent(String cameraId) {
            setDeviceId(cameraId)
            setPayload(new CameraEventPayload(cameraId, null, CommonEventTypes.CAMERA_CONNECTED.code(), System.currentTimeMillis(), null))
        }

        @Override
        String getEventPath() {
            return EventUtils.generatePath(CameraConnectedEvent.PATH, super.getEventPath())
        }
    }

    @EventPath(path = CameraDisconnectedEvent.PATH)
    class CameraDisconnectedEvent extends BaseCameraEvent<CameraEventPayload> {

        public static final String PATH = "disconnected";

        CameraDisconnectedEvent() {
            super(null);
        }

        CameraDisconnectedEvent(String cameraId) {
            setDeviceId(cameraId);
            setPayload(new CameraEventPayload(cameraId, null, CommonEventTypes.CAMERA_DISCONNECTED.code(), System.currentTimeMillis(), null));
        }

        @Override
        String getEventPath() {
            return EventUtils.generatePath(CameraDisconnectedEvent.PATH, super.getEventPath());
        }
    }
}
