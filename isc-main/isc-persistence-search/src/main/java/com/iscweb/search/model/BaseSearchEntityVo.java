package com.iscweb.search.model;

import com.iscweb.common.model.ISearchEntityVo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public abstract class BaseSearchEntityVo implements ISearchEntityVo {

    @Id
    String eventId;

    @Field(type = FieldType.Keyword)
    String correlationId;

    @Field(type = FieldType.Keyword)
    String referenceId;

    @Field(type = FieldType.Keyword)
    String eventClass;

    @Field(type = FieldType.Keyword)
    String type;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    ZonedDateTime time;

    @Field(type = FieldType.Keyword)
    String origin;

    @Field(type = FieldType.Keyword)
    String schoolId;

    @Field(type = FieldType.Keyword)
    String districtId;

    public BaseSearchEntityVo(String eventClass) {
        this.eventClass = eventClass;
    }
}
