package com.iscweb.common.model.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Helper class to accumulate nodes a specific action should be applied to when
 * applying action in composite tree.
 *
 * Please use <code>DeviceActionAccumulator::build()</code> static method to pass to the root composite node
 * which the action is applied to. Use instance method <code>childAccumulator</code> to pass to children nodes.
 * See <code>com.iscweb.service.composite.impl.RegionComposite#lockDown</code> as example.
 */
@Data
public class DeviceActionAccumulator {

    private boolean root;
    private List<String> deviceIds;

    private DeviceActionAccumulator(boolean isRoot, List<String> deviceIds) {
        this.root = isRoot;
        this.deviceIds = deviceIds;
    }

    private DeviceActionAccumulator(List<String> deviceIds) {
        this.root = false;
        this.deviceIds = deviceIds;
    }

    /**
     * Factory method for default composite device action accumulator instance.
     * @return a new root device action accumulator object.
     */
    public static DeviceActionAccumulator build() {
        return new DeviceActionAccumulator(true, Lists.newArrayList());
    }

    public void withDevice(String id) {
        deviceIds.add(id);
    }

    /**
     * Pass to the children composite nodes to collect all nodes to apply a device action.
     * @return Returns non-root accumulator instance.
     */
    public DeviceActionAccumulator childAccumulator() {
        return new DeviceActionAccumulator(this.deviceIds);
    }
}
