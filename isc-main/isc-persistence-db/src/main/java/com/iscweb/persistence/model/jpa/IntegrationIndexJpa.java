package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.IIntegrationIndex;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.persistence.model.BaseJpaTrackedEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@ToString
@Table(name = "integration_index_joins")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "inij_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "inij_id")),
                     @AttributeOverride(name = "created", column = @Column(name = "inij_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "inij_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "inij_guid")),
                    })
public class IntegrationIndexJpa extends BaseJpaTrackedEntity implements IIntegrationIndex {

    private IntegrationJpa integration;
    private IndexJpa index;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.UNSUPPORTED;
    }

    @Override
    @ManyToOne
    @JoinColumn(name = "inij_in_id", nullable = false, updatable = false)
    public IntegrationJpa getIntegration() {
        return integration;
    }

    public void setIntegration(IntegrationJpa integration) {
        this.integration = integration;
    }

    @Override
    @ManyToOne
    @JoinColumn(name = "inij_i_id", nullable = false, updatable = false)
    public IndexJpa getIndex() {
        return index;
    }

    public void setIndex(IndexJpa index) {
        this.index = index;
    }
}
