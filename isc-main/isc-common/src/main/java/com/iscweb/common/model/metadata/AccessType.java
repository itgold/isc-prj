package com.iscweb.common.model.metadata;

public enum AccessType {
    NONE(0, false, false, "None"),
    READ_ONLY(1, true, false, "View Only"),
    READ_WRITE(2, true, true, "Full Access"),
    ;

    private int accessLevelIndex;
    private boolean canRead;
    private boolean canWrite;
    private String label;

    AccessType(int accessLevelIndex, boolean canRead, boolean canWrite, String label) {
        this.accessLevelIndex = accessLevelIndex;
        this.canRead = canRead;
        this.canWrite = canWrite;
        this.label = label;
    }

    public boolean canRead() {
        return canRead;
    }

    public boolean canWrite() {
        return canWrite;
    }

    public int getAccessLevelIndex() {
        return accessLevelIndex;
    }

    public String getLabel() {
        return label;
    }
}
