package com.iscweb.sputnik.uitest.config;

public class Config {

    private static String host = "https://dev.iscweb.io"; //default value

    private static String userName = "admin@usd.io";
    private static String password = "pass";

    private static String remoteDriver = "http://localhost:4444/";
    private static boolean isRemoteDriver = false;

    public static void init(String hostValue, String remoteFlag) {
        host = hostValue;
        if (remoteFlag.length() > 0) {
            isRemoteDriver = true;
        }
    }

    public static void init(String hostValue) {
        host = hostValue;
    }

    public static String getHost() {
        return host;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getRemoteDriver() { return remoteDriver; }

    public static boolean getIsRemoteDriver() { return isRemoteDriver; }
}
