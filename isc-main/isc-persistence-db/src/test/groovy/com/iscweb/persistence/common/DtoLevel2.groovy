package com.iscweb.persistence.common

class DtoLevel2 implements TestDtoWithChildren {
    private String name
    private DtoLevel3 level3

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    DtoLevel3 getLevel3() {
        return level3
    }

    void setLevel3(DtoLevel3 level3) {
        this.level3 = level3
    }
}
