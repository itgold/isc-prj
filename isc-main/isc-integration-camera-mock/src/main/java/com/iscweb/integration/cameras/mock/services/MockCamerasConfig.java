package com.iscweb.integration.cameras.mock.services;

import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "integration.cameras.mock")
public class MockCamerasConfig {

    private List<MockCamera> cameras = Lists.newArrayList();

    public List<MockCamera> getCameras() {
        return cameras;
    }

    public void setCameras(List<MockCamera> cameras) {
        this.cameras = cameras;
    }

    public static class MockCamera {
        private String id;
        private String name;
        private String description;
        private String video;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }
}
