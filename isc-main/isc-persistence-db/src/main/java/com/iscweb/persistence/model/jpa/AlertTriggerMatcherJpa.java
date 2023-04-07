package com.iscweb.persistence.model.jpa;

import com.iscweb.common.model.IEmbeddedEntity;
import com.iscweb.common.model.alert.AlertTriggerMatcherType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

/**
 * Alert trigger matcher entity.
 *
 * @author dmorozov
 * Date: 5/10/22
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AlertTriggerMatcherJpa implements IEmbeddedEntity {

    @NotNull
    @Size(max = 50)
    @Enumerated(EnumType.STRING)
    private AlertTriggerMatcherType type;

    @NotNull
    @Size(max = 1024)
    private String body;

    @CreatedDate
    @Type(type = "org.hibernate.type.ZonedDateTimeType")
    @Column(name = "created", columnDefinition = "timestamptz")
    private ZonedDateTime created;

    @LastModifiedDate
    @Type(type = "org.hibernate.type.ZonedDateTimeType")
    @Column(name = "updated", columnDefinition = "timestamptz")
    private ZonedDateTime updated;
}
