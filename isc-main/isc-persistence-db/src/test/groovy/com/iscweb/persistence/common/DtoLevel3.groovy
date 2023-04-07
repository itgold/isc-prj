package com.iscweb.persistence.common

class DtoLevel3 implements TestDtoWithChildren {
    private String name
    private DtoLevel4 level4

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    DtoLevel4 getLevel4() {
        return level4
    }

    void setLevel4(DtoLevel4 level4) {
        this.level4 = level4
    }
}
