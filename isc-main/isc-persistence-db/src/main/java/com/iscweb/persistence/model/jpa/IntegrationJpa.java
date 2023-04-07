package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.IIntegration;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.IntegrationStatus;
import com.iscweb.persistence.model.BaseJpaTrackedEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@ToString
@Table(name = "integrations")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "in_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "in_id")),
                     @AttributeOverride(name = "created", column = @Column(name = "in_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "in_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "in_guid")),
                    })
public class IntegrationJpa extends BaseJpaTrackedEntity implements IIntegration {

    private String name;
    private String description;
    private String connectionParams;
    private String metaParams;

    private IntegrationStatus status = IntegrationStatus.REGISTERED;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.INTEGRATION;
    }

    @Override
    @Column(name = "in_name")
    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    @Override
    @Column(name = "in_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String providerGuid) {
        this.description = providerGuid;
    }

    @Override
    @Column(name = "in_connection")
    public String getConnectionParams() {
        return connectionParams;
    }

    public void setConnectionParams(String providerThreadGuid) {
        this.connectionParams = providerThreadGuid;
    }

    @Override
    @Column(name = "in_meta")
    public String getMetaParams() {
        return metaParams;
    }

    public void setMetaParams(String metaParams) {
        this.metaParams = metaParams;
    }

    @Override
    @Column(name = "in_status")
    @Enumerated(EnumType.STRING)
    public IntegrationStatus getStatus() {
        return status;
    }

    public void setStatus(IntegrationStatus status) {
        this.status = status;
    }
}
