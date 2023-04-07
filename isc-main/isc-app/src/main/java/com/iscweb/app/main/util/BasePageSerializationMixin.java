package com.iscweb.app.main.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Used to customize the JSON representation of {@link Page} objects.
 */
@JsonPropertyOrder({"pageNumber", "totalPages", "pageSize", "totalElements", "items"})
public abstract class BasePageSerializationMixin {

    @JsonProperty("pageNumber")
    public abstract int getNumber();

    @JsonProperty("pageSize")
    public abstract int getSize();

    @JsonProperty("items")
    public abstract List<Object> getContent();

    @JsonIgnore
    public abstract Integer getNumberOfElements();

    @JsonIgnore
    public abstract Pageable getPageable();

    @JsonIgnore
    public abstract Sort getSort();

    @JsonIgnore
    public abstract boolean hasContent();

    @JsonIgnore
    public abstract boolean hasNext();

    @JsonIgnore
    public abstract boolean isFirst();

    @JsonIgnore
    public abstract boolean isLast();

    @JsonIgnore
    public abstract boolean hasPrevious();

    @JsonIgnore
    public abstract Pageable nextPageable();

    @JsonIgnore
    public abstract Pageable previousPageable();

}
