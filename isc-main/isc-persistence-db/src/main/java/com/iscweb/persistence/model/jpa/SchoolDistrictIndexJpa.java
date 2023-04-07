package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.ISchoolDistrictIndex;
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
@Table(name = "school_district_index_joins")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "sdij_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "sdij_id")),
                     @AttributeOverride(name = "created", column = @Column(name = "sdij_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "sdij_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "sdij_guid")),
                    })
public class SchoolDistrictIndexJpa extends BaseJpaTrackedEntity implements ISchoolDistrictIndex {

    private SchoolDistrictJpa schoolDistrict;
    private IndexJpa index;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.UNSUPPORTED;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "sdij_i_id", referencedColumnName = "i_id", nullable = false)
    public IndexJpa getIndex() {
        return index;
    }

    public void setIndex(IndexJpa file) {
        this.index = file;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "sdij_sd_id", referencedColumnName = "sd_id", nullable = false)
    public SchoolDistrictJpa getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setSchoolDistrict(SchoolDistrictJpa message) {
        this.schoolDistrict = message;
    }
}
