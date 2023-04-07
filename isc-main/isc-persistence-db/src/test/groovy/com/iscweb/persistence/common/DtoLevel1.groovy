package com.iscweb.persistence.common

class DtoLevel1 implements TestDtoWithChildren {
    private String name
    private DtoLevel2 level2

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    DtoLevel2 getLevel2() {
        return level2
    }

    void setLevel2(DtoLevel2 level2) {
        this.level2 = level2
    }
}
