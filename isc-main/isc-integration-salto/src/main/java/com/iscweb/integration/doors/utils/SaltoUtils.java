package com.iscweb.integration.doors.utils;

import com.iscweb.common.model.dto.DeviceStateItemDto;
import org.apache.commons.lang3.StringUtils;

public final class SaltoUtils {

    public static boolean isEmpty(DeviceStateItemDto state) {
        return state == null || StringUtils.isEmpty(state.getValue());
    }

}
