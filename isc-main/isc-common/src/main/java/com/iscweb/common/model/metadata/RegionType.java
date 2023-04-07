package com.iscweb.common.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration represent regions types (hierarchy).
 *
 * \ Root
 *  \ School District
 *   \ School
 *     \ Door (Gate)
 *     \ Camera
 *     \ Regions (BUILDING)
 *       \ Regions (ELEVATOR)
 *       \ Regions (STAIRS)
 *       \ Regions (FLOOR)
 *         \ Regions (ROOM)
 *           \ Camera
 *           \ Door
 *         \ Regions (WALL)
 *         \ Regions (STAIRS)
 *         \ Camera
 *         \ Door
 *     \ Regions (ZONE)
 *     \ Regions (UNKNOWN)
 **/
@AllArgsConstructor
public enum RegionType {
    ROOT(0),
    SCHOOL_DISTRICT(5),
    SCHOOL(10),
    BUILDING(15),
    FLOOR(20),
    ROOM(25),
    WALL(30),
    STAIRS(35),
    ELEVATOR(40),
    ZONE(45),
    POINT_REGION(49),
    UNKNOWN(50);

    /**
     * Used for comparison and ordering operations on regions.
     */
    @Getter
    private int order;

    /**
     * Checks if current region type order is greater than to a given region type order.
     * @param type region type to do a check against.
     * @return true if current region type order level is greater than provided region type order.
     */
    public boolean lt(RegionType type) {
        boolean result = false;
        if (type != null) {
            result = getOrder() < type.getOrder();
        }
        return result;
    }

    /**
     * Checks if current region type order is equal to a given region type order.
     * @param type region type to do a check against.
     * @return true if current region type order level is equal to provided region type order.
     */
    public boolean eq(RegionType type) {
        boolean result = false;
        if (type != null) {
            result = getOrder() == type.getOrder();
        }
        return result;
    }

    /**
     * Is this region represents static construction region like a building, district, or any building part?
     * @return if it is a static region.
     */
    public boolean isStatic() {
        return getOrder() < ZONE.getOrder();
    }
}
