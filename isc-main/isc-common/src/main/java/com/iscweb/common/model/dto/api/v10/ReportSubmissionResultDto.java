package com.iscweb.common.model.dto.api.v10;

import com.google.common.base.MoreObjects;
import com.iscweb.common.model.dto.BaseDto;

import java.util.Objects;

public class ReportSubmissionResultDto extends BaseDto {

    private String reportId;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportSubmissionResultDto that = (ReportSubmissionResultDto) o;
        return Objects.equals(reportId, that.reportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId);
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("reportId", reportId)
                          .toString();
    }
}

