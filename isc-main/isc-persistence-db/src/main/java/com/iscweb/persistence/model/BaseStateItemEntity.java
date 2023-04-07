package com.iscweb.persistence.model;

import com.iscweb.common.model.entity.IDeviceStateEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Base class for state item entities implementations.
 * @author skurenkov
 */
@MappedSuperclass
public abstract class BaseStateItemEntity extends BaseJpaTrackedEntity implements IDeviceStateEntity {

    /**
     * Type of the state object.
     */
    private String type;

    /**
     * State object value.
     */
    private String value;

    /**
     * Default constructor initializing guid with UUID random value.
     */
    public BaseStateItemEntity() {
        setGuid(UUID.randomUUID().toString());
    }

    @Column(name = "base_type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "base_value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
