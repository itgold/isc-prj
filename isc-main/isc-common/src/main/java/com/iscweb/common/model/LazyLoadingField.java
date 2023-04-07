package com.iscweb.common.model;

/**
 * Enumeration of all lazy loading fields used by Jpa entities.
 *
 * We have changed our Jpa one-to-many / many-to-many associations to be lazily loaded.
 * So to avoid lazy initialization exceptions when such associations accessed outside the
 * transaction some methods takes list of fields to be enforced to load.
 */
public enum LazyLoadingField {
    DEVICE_STATE,
    PARENT_REGION,
    METADATA
}
