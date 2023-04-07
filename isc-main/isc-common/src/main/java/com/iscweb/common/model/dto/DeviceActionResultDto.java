package com.iscweb.common.model.dto;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Device action execution result Dto.
 */
@Builder
@Data
public class DeviceActionResultDto implements IDto {

    public enum ActionResult {
        SUCCESS,
        WARNING,
        FAILURE
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DeviceActionError {
        private String code;
        private String message;

        public DeviceActionError(String code) {
            this.code = code;
        }
    }

    private ActionResult status;
    private List<DeviceActionError> errors;

    /**
     * Factory method to combine new errors into existing device action execution result.
     *
     * @param result Existing device action execution result.
     * @param errors New errors to add
     * @return New updated device action execution result.
     */
    public static DeviceActionResultDto fromResult(DeviceActionResultDto result, DeviceActionError ... errors) {
        return DeviceActionResultDto.fromResult(result, Arrays.asList(errors));
    }

    /**
     * Factory method to combine new errors into existing device action execution result.
     *
     * @param result Existing device action execution result.
     * @param errors New errors to add
     * @return New updated device action execution result.
     */
    public static DeviceActionResultDto fromResult(DeviceActionResultDto result, Collection<DeviceActionError> errors) {
        ActionResult status = result.status == ActionResult.FAILURE ? ActionResult.FAILURE : ActionResult.WARNING;
        List<DeviceActionError> allErrors = result.errors != null ? result.errors : Lists.newArrayList();
        allErrors.addAll(errors);

        return DeviceActionResultDto.builder()
                .status(status)
                .errors(allErrors)
                .build();
    }
}
