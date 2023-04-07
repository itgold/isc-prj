package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.IEmbeddedEntity;
import com.iscweb.common.model.entity.IDeviceStateItem;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Embeddable
public class DeviceStateItemJpa implements IDeviceStateItem, IEmbeddedEntity {

    private String name;

    private String value;

    private ZonedDateTime updated;

    public DeviceStateItemJpa() {}

    public DeviceStateItemJpa(String name, String value, ZonedDateTime updated) {
        this.name = name;
        this.value = value;
        this.updated = updated;
    }

    @Column(name = "ds_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ds_value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "ds_updated")
    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceStateItemJpa that = (DeviceStateItemJpa) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
