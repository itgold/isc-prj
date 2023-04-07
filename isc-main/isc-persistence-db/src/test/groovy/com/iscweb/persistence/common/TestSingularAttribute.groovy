package com.iscweb.persistence.common

import javax.persistence.metamodel.ManagedType
import javax.persistence.metamodel.SingularAttribute
import javax.persistence.metamodel.Type
import java.lang.reflect.Member

class TestSingularAttribute implements SingularAttribute {

    final String name;

    TestSingularAttribute(String name) {
        this.name = name
    }

    @Override
    boolean isId() {
        return false
    }

    @Override
    boolean isVersion() {
        return false
    }

    @Override
    boolean isOptional() {
        return false
    }

    @Override
    Type getType() {
        return null
    }

    @Override
    String getName() {
        return name
    }

    @Override
    PersistentAttributeType getPersistentAttributeType() {
        return null
    }

    @Override
    ManagedType getDeclaringType() {
        return null
    }

    @Override
    Class getJavaType() {
        return null
    }

    @Override
    Member getJavaMember() {
        return null
    }

    @Override
    boolean isAssociation() {
        return false
    }

    @Override
    boolean isCollection() {
        return false
    }

    @Override
    BindableType getBindableType() {
        return null
    }

    @Override
    Class getBindableJavaType() {
        return null
    }
}