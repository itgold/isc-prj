/**
 * User: skurenkov
 * Date: 12/6/19
 */
module iscweb.app.main {

    requires spring.boot;
    requires org.aspectj.weaver;
    requires com.google.common;
    requires org.slf4j;
    requires spring.context;
    requires spring.security.core;
    requires spring.core;
    requires spring.web;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires spring.data.commons;
    requires spring.security.oauth2;
    requires spring.data.jpa;
    requires spring.jdbc;
    requires spring.orm;
    requires spring.tx;
    requires java.annotation;
    requires java.sql;
    requires org.apache.commons.io;
    requires spring.aop;
    requires spring.beans;
    requires spring.security.web;
    requires spring.webmvc;
    requires springfox.all;
    requires springfox.spring.web;
    requires springfox.swagger2;
    requires org.apache.tomcat.embed.core;
    requires com.zaxxer.hikari;
    requires spring.boot.autoconfigure;
    requires spring.security.config;
    requires static org.mapstruct.processor;
    requires lombok;

    requires iscweb.common;
    requires iscweb.web;
    requires iscweb.service;

    opens com.iscweb.app.main.util to spring.beans;
    opens com.iscweb.app.main.config to spring.core, spring.beans, spring.context;
    opens com.iscweb.app to spring.core;
    exports com.iscweb.app;
}