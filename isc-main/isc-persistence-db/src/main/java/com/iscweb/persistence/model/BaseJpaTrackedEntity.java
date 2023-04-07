package com.iscweb.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.metadata.ConverterType;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.ZonedDateTime;

/**
 * Base class for entities which persist DateTime tracking information.
 * Subclasses of this class must override column names since the provided column names
 * will probably not match.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseJpaTrackedEntity implements IApplicationEntity {

    private Long id;
    private String guid;
    private ZonedDateTime created;
    private ZonedDateTime updated;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.UNSUPPORTED;
    }

    @Id
    @Override
    @JsonProperty(value = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long rowId) {
        this.id = rowId;
    }

    @Override
    @CreatedDate
    @Type(type = "org.hibernate.type.ZonedDateTimeType")
    @Column(name = "created", columnDefinition = "timestamptz")
    public ZonedDateTime getCreated() {
        return created;
    }

    @Override
    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    @Override
    @LastModifiedDate
    @Type(type = "org.hibernate.type.ZonedDateTimeType")
    @Column(name = "updated", columnDefinition = "timestamptz")
    public ZonedDateTime getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    @Override
    @Column(name = "guid")
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!o.getClass().equals(this.getClass())) {
            return false;
        }
        BaseJpaTrackedEntity that = (BaseJpaTrackedEntity) o;

        if (id == null && that.id == null) {
            return false;
        }

        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : Objects.hashCode(id);
    }

}
