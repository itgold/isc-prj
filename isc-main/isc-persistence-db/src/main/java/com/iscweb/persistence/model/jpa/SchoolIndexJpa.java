package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.ISchoolIndex;
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
@Table(name = "school_index_joins")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "sij_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "sij_id")),
                     @AttributeOverride(name = "created", column = @Column(name = "sij_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "sij_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "sij_guid")),
                    })
public class SchoolIndexJpa extends BaseJpaTrackedEntity implements ISchoolIndex {

    private SchoolJpa school;
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
    @JoinColumn(name = "sij_s_id", nullable = false, updatable = false)
    public SchoolJpa getSchool() {
        return school;
    }

    public void setSchool(SchoolJpa integration) {
        this.school = integration;
    }

    @Override
    @ManyToOne
    @JoinColumn(name = "sij_i_id", nullable = false, updatable = false)
    public IndexJpa getIndex() {
        return index;
    }

    public void setIndex(IndexJpa index) {
        this.index = index;
    }
}
