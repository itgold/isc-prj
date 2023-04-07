/**
 * User: skurenkov
 * Date: 12/6/19
 */
module iscweb.service {

    requires iscweb.common;
    requires iscweb.persistence;

    requires java.sql;
    requires java.annotation;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires spring.data.commons;
    requires spring.security.core;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires lombok;
    requires com.google.common;
    requires org.slf4j;
    requires org.aspectj.weaver;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires spring.web;
    requires org.apache.commons.lang3;
    requires spring.security.oauth2;
    requires spring.aop;
    requires spring.tx;
    requires passay;
    requires java.scripting;

    exports com.iscweb.service.entity;
    exports com.iscweb.service.converter;
    exports com.iscweb.service.util;
    exports com.iscweb.service.api.v1.v11;
    exports com.iscweb.service.api;
    exports com.iscweb.service.security;
    exports com.iscweb.service;
    exports com.iscweb.service.api.v1.v10;
}