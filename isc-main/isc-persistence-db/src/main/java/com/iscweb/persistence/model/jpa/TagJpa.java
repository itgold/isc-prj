package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.ITag;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.persistence.model.BaseJpaTrackedEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@ToString
@Table(name = "tags")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "t_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "t_id")),
                     @AttributeOverride(name = "created", column = @Column(name = "t_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "t_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "t_guid")),
                    })
@NoArgsConstructor
@AllArgsConstructor
public class TagJpa extends BaseJpaTrackedEntity implements ITag {

    private String name;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.TAG;
    }

    @Override
    @Column(name = "t_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
