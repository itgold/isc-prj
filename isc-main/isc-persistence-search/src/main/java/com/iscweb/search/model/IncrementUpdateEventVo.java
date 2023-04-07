package com.iscweb.search.model;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

@Getter
@TypeAlias(IncrementUpdateEventVo.TYPE)
public class IncrementUpdateEventVo extends ApplicationEventVo {
    public static final String TYPE = "increment";

    public IncrementUpdateEventVo() {
        super(IncrementUpdateEventVo.TYPE);
    }
}
