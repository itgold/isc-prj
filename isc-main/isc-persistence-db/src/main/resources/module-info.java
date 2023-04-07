/**
 * User: skurenkov
 * Date: 12/6/19
 */
module iscweb.persistence {

    requires iscweb.common;

    requires lombok;
    requires spring.boot;
    requires com.google.common;
    requires org.slf4j;
    requires spring.context;
    requires spring.core;
    requires spring.data.commons;
    requires org.aspectj.weaver;
    requires spring.security.core;
    requires com.fasterxml.jackson.core;
    requires java.persistence;
    requires com.fasterxml.jackson.annotation;
    requires org.hibernate.orm.core;
    requires spring.data.jpa;
    requires spring.tx;
    requires com.zaxxer.hikari;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.jdbc;
    requires spring.orm;
    requires java.sql;
    requires java.annotation;

    exports com.iscweb.persistence.cache;
    exports com.iscweb.persistence.model.jpa;
    exports com.iscweb.persistence.repositories.impl;
    exports com.iscweb.persistence.repositories;

}