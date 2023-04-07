package com.iscweb.search.model;

import com.iscweb.common.model.event.ITypedPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;

@Data
@TypeAlias(RawEventVo.TYPE)
@Document(indexName = "raw_events")
@EqualsAndHashCode(callSuper = true)
public class RawEventVo extends BaseSearchEntityVo {

    public static final String TYPE = "raw_event";

    @Field(type = FieldType.Keyword)
    String externalEntityId;

    @Field(type = FieldType.Auto)
    ITypedPayload payload;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    ZonedDateTime receivedTime;

    public RawEventVo() {
        super(RawEventVo.TYPE);
    }
}
