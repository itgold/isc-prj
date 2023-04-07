package com.iscweb.common.events.payload;

import com.iscweb.common.model.event.ITypedPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringPayload implements ITypedPayload {

    private String type;

    private String value;
}
