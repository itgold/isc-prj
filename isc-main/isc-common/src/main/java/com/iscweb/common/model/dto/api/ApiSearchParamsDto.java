package com.iscweb.common.model.dto.api;

import com.google.common.base.MoreObjects;
import com.iscweb.common.model.dto.IDto;

import java.util.List;

/**
 * A DTO that represents API search parameters.
 */
public class ApiSearchParamsDto implements IDto {

    private Long from;
    private Long to;
    private List<String> enclaveIds;
    private String distributionType;
    private String submittedBy;
    private String tag;

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public List<String> getEnclaveIds() {
        return enclaveIds;
    }

    public void setEnclaveIds(List<String> enclaveIds) {
        this.enclaveIds = enclaveIds;
    }

    public String getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(String distributionType) {
        this.distributionType = distributionType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tagName) {
        this.tag = tagName;
    }

    @Deprecated
    public String getSubmittedBy() {
        return submittedBy;
    }

    @Deprecated
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("from", from)
                          .add("to", to)
                          .add("enclaveIds", enclaveIds)
                          .add("distributionType", distributionType)
                          .add("tag", tag)
                          .toString();
    }
}
