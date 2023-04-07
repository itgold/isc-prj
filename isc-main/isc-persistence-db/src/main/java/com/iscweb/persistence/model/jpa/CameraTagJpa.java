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

@ToString
@Entity(name = "CameraTag")
@DiscriminatorValue("Camera")
@EqualsAndHashCode(callSuper = true)
public class CameraTagJpa extends BaseEntityTagJpa {

  @Getter(onMethod = @__({@Override, @ManyToOne, @JoinColumn(name = "tej_entity_id")}))
  @Setter
  private CameraJpa entity;
}
