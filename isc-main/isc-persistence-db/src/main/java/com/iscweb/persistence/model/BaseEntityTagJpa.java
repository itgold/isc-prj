package com.iscweb.persistence.model;

import com.iscweb.common.model.entity.IEntityTag;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.persistence.model.jpa.TagJpa;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Associates entities with tags.
 */
@Entity
@ToString
@Table(name = "tag_entity_joins")
@EqualsAndHashCode(callSuper = true)
@DiscriminatorColumn(name = "tej_entity_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SequenceGenerator(name = "seq_gen", sequenceName = "tej_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "tej_id")),
                     @AttributeOverride(name = "created", column = @Column(name = "tej_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "tej_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "tej_guid")),
                    })
public abstract class BaseEntityTagJpa extends BaseJpaTrackedEntity implements IEntityTag {

    private TagJpa tag;
    private String type;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.ENTITY_ALERT;
    }

    @Override
    @ManyToOne
    @JoinColumn(name = "tej_t_id")
    public TagJpa getTag() {
        return tag;
    }

    public void setTag(TagJpa tag) {
        this.tag = tag;
    }

    @Column(name = "tej_entity_type", insertable = false, updatable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
