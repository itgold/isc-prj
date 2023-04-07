/**
 * User: skurenkov
 * Date: 12/6/19
 */
module iscweb.web {

    requires iscweb.common;
    requires iscweb.persistence;
    requires iscweb.service;

    requires spring.beans;
    requires spring.core;
    requires spring.context;
    requires spring.web;
    requires spring.webmvc;
    requires spring.data.commons;
    requires spring.security.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.security.core;
    requires spring.aop;
    requires spring.security.config;
    requires java.annotation;
    requires java.sql;
    requires java.scripting;
    requires lombok;
    requires com.google.common;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;
    requires org.aspectj.weaver;
    requires org.apache.tomcat.embed.core;
    requires org.apache.commons.lang3;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires swagger.annotations;
    requires spring.security.oauth2;
    requires json;
    requires google.api.client;
    requires com.google.api.client.auth;
    requires com.google.api.client.json.jackson2;
    requires com.google.api.client;
    requires google.api.services.gmail.v1.rev105;

    exports com.iscweb.component.api;
    exports com.iscweb.component.web.util;
    exports com.iscweb.component.web;
    exports com.iscweb.component.web.handler;
    exports com.iscweb.component.api.util;
}