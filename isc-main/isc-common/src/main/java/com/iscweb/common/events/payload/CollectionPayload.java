package com.iscweb.common.events.payload;

import com.google.common.collect.Lists;
import com.iscweb.common.model.event.ITypedPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Event payload implementation for the case when payload is a collection of items.
 *
 * @param <T> Collection type
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionPayload<T> implements ITypedPayload {

    private String type;

    private List<T> value = Lists.newArrayList();
}
