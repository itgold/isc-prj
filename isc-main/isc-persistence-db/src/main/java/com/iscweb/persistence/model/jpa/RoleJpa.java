package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.entity.IRole;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.persistence.model.BaseJpaTrackedEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Roles are associated with permissions for operations that can be performed in the system.
 * They mimic the roles that Spring as a framework uses to secure calls.
 */
@Entity
@ToString
@Table(name = "roles")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "ro_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "ro_id")),
                     @AttributeOverride(name = "created", column = @Column(name = "ro_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "ro_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "ro_guid")),
                    })
public class RoleJpa extends BaseJpaTrackedEntity implements IRole {

    private String name;

    public RoleJpa() {
    }

    public RoleJpa(String name) {
        this.name = name;
    }

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.ROLE;
    }

    @Override
    @Column(name = "ro_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
