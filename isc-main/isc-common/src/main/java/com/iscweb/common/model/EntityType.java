package com.iscweb.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Defines supported by ISC system device types.
 */
@AllArgsConstructor
public enum EntityType {
    DRONE(10),
    RADIO(20),
    CAMERA(30),
    DOOR(40),
    SPEAKER(50),
    INTERNET(60),
    DB(70),
    ES(80),
    TPS(90),
    USER(100),
    UTILITY(110),
    SAFETY(120);

    /**
     * Used for comparison and ordering operations on regions.
     */
    @Getter
    private int order;

    /**
     * Checks if current device type order is less than to a given device type order.
     * @param type device type to do a check against.
     * @return true if current device type order level is less than provided device type order.
     */
    public boolean lt(EntityType type) {
        boolean result = false;
        if (type != null) {
            result = getOrder() < type.getOrder();
        }
        return result;
    }

    /**
     * Checks if current device type order is equal to a given device type order.
     * @param type device type to do a check against.
     * @return true if current device type order level is equal to provided device type order.
     */
    public boolean eq(EntityType type) {
        boolean result = false;
        if (type != null) {
            result = getOrder() == type.getOrder();
        }
        return result;
    }
}
