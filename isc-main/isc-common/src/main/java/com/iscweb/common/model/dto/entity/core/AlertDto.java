package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.dto.BaseEntityDto;
import com.iscweb.common.model.metadata.AlertSeverity;
import com.iscweb.common.model.metadata.AlertStatus;
import com.iscweb.common.model.metadata.ConverterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

/**
 * Alert DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AlertDto extends BaseEntityDto {

  private String triggerId;

  private String deviceId;

  private EntityType deviceType;

  private AlertSeverity severity = AlertSeverity.MINOR;

  private int count;

  private AlertStatus status;

  private String eventId;

  private String schoolId;

  private String districtId;

  private String code;

  private String description;

  /**
   * @see IApplicationModel#getConverterType()
   */
  @Override
  @Transient
  @JsonIgnore
  public ConverterType getConverterType() {
    return ConverterType.ALERT;
  }

  @Transient
  @JsonIgnore
  public void incrementCount() {
    count += 1;
  }
}
