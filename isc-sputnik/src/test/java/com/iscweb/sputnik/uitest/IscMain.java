package com.iscweb.sputnik.uitest;

import com.iscweb.sputnik.uitest.config.Config;
import com.iscweb.sputnik.uitest.tests.EntityCrudTest;
import com.iscweb.sputnik.uitest.tests.LoginPageTest;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class IscMain {
    /**
     * Running all tests outside of IDE (ex, when .jar is created)
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args.length == 1) {
                Config.init(args[0]);
            } else {
                if (args.length == 2) {
                    Config.init(args[0], args[1]);
                }
            }
        } else {
            System.out.println("Host value is required");
            System.exit(1);
        }

        TestListenerAdapter listenerAdapter = new TestListenerAdapter();
        TestNG testNg = new TestNG();
        testNg.setTestClasses(new Class[]{
                EntityCrudTest.class,
                LoginPageTest.class
        });
        testNg.addListener(listenerAdapter);
        testNg.run();
    }
}
