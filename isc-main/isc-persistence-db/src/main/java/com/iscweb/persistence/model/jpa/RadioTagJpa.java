package com.iscweb.persistence.model.jpa;

import com.iscweb.persistence.model.BaseEntityTagJpa;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * An entity representing a system-managed radio device tag.
 *
 * @author dmorozov
 * Date: 2/15/22
 */
@ToString
@Entity(name = "RadioTag")
@DiscriminatorValue("Radio")
@EqualsAndHashCode(callSuper = true)
public class RadioTagJpa extends BaseEntityTagJpa {

  @Getter(onMethod = @__({@Override, @ManyToOne, @JoinColumn(name = "tej_entity_id")}))
  @Setter
  private RadioJpa entity;
}
