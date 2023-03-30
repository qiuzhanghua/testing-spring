package com.example.controllers;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;

import static java.time.Duration.ofMillis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.condition.JRE.JAVA_17;
import static org.junit.jupiter.api.condition.JRE.JAVA_8;
import static org.junit.jupiter.api.condition.OS.*;

@Tag("controller")
class IndexControllerTest {

    IndexController controller;

    @BeforeEach
    void setUp() {
        controller = new IndexController();
    }

    @DisplayName("Test Proper View name is returned for index page")
    @Test
    void index() {
        assertEquals("index", controller.index());
        assertEquals("index", controller.index(), "Wrong View Returned");

        assertEquals("index", controller.index(), () -> "Another Expensive Message " +
                "Make me only if you have to");

        // AssertJ Uasge
        assertThat(controller.index()).isEqualTo("index").hasSize(5);
    }

    @Test
    @DisplayName("Test Exception")
    void oupsHandler() {
        assertThrows(ValueNotFoundException.class, controller::oopsHandler);
//        assertTrue("notimplemented".equals(controller.oopsHandler()), () -> "This is some expensive " +
//                "Message to build" +
//                "for my test");
    }

    @Test
    @Disabled("Demo of timout")
    void testTimeout() {
        assertTimeout(ofMillis(100), () -> {
            Thread.sleep(2000);
            System.out.println("I got here");
        });
    }

    @Disabled("Demo of timout")
    @Test
    void testTimeoutPreemptively() {
        assertTimeoutPreemptively(ofMillis(100), () -> {
            Thread.sleep(5000);
            System.out.println("I got here 234");
        });
    }


    @Test
    void testAssumptions() {
        assumeTrue("lib".equalsIgnoreCase(System.getenv("TDP_LIB")), "TDP not installed");
        System.out.println("TDP is installed");
    }

    @Test
    void testAssumptions2() {
        // actually skipped
        assumeTrue("".equalsIgnoreCase(System.getenv("TDP_LIB")), "TDP installed?");
        System.out.println("TDP not installed");
    }


    @Test
    @EnabledOnOs(MAC)
    void testOnMac() {
        assumeTrue("Mac OS X".equals(System.getProperty("os.name")), "Test is only on Mac");
        System.out.println("Running on Mac");
    }

    @Test
    @EnabledOnOs(WINDOWS)
    void testOnWindows() {
        assumeTrue("Windows 10".equals(System.getProperty("os.name")), "Test is only on Windows");
        System.out.println("Running on Windows 10");
    }

    @Test
    @EnabledOnJre(JAVA_8)
    void testOnJava8() {
        System.out.println("Running on Java 8");
    }

    @Test
    @EnabledOnJre(JAVA_17)
    void testOnJava17() {
        System.out.println("Running on Java 17");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "USER", matches = "q")
    void testIfUserQ() {
        System.out.println("Running this as user is q");
    }
}
