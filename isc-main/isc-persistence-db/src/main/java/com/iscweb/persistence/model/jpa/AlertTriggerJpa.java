package com.iscweb.persistence.model.jpa;

import com.google.common.collect.Sets;
import com.iscweb.common.model.entity.IAlertTrigger;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.persistence.model.BaseJpaTrackedEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * Alert entity.
 *
 * @author dmorozov
 * Date: 5/10/22
 */
@Entity
@ToString
@Table(name = "alert_triggers")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "at_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "at_id")),
                     @AttributeOverride(name = "created", column = @Column(name = "at_created", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "updated", column = @Column(name = "at_updated", columnDefinition = "timestamptz")),
                     @AttributeOverride(name = "guid", column = @Column(name = "at_guid")),
                    })
public class AlertTriggerJpa extends BaseJpaTrackedEntity implements IAlertTrigger {

    private String name;

    private String processorType;

    private boolean active = Boolean.TRUE;

    private Set<AlertTriggerMatcherJpa> matchers = Sets.newHashSet();

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.ALERT_TRIGGER;
    }

    @Column(name = "at_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "at_active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "alert_trigger_matchers", joinColumns = @JoinColumn(name = "atm_at_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "body", column = @Column(name = "atm_body")),
            @AttributeOverride(name = "type", column = @Column(name = "atm_type")),
            @AttributeOverride(name = "created", column = @Column(name = "atm_created", columnDefinition = "timestamptz")),
            @AttributeOverride(name = "updated", column = @Column(name = "atm_updated", columnDefinition = "timestamptz"))
    })
    public Set<AlertTriggerMatcherJpa> getMatchers() {
        return matchers;
    }

    public void setMatchers(Set<AlertTriggerMatcherJpa> matchers) {
        this.matchers = matchers;
    }

    @Column(name = "at_processor_type")
    public String getProcessorType() {
        return processorType;
    }

    public void setProcessorType(String processorType) {
        this.processorType = processorType;
    }
}
