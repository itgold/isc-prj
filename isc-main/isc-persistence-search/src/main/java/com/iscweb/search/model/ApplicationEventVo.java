package com.iscweb.search.model;

import com.iscweb.common.model.event.ITypedPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;

@Data
@TypeAlias(ApplicationEventVo.TYPE)
@Document(indexName = ApplicationEventVo.INDEX)
@EqualsAndHashCode(callSuper = true)
public class ApplicationEventVo extends BaseSearchEntityVo {

    public static final String INDEX = "events";
    public static final String TYPE = "event";

    @Field(type = FieldType.Keyword)
    String deviceId;

    @Field(type = FieldType.Auto)
    ITypedPayload payload;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    ZonedDateTime receivedTime;

    public ApplicationEventVo() {
        super(ApplicationEventVo.TYPE);
    }

    public ApplicationEventVo(String eventClass) {
        super(eventClass);
    }
}
