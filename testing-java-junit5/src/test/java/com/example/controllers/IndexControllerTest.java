package com.example.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

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

}
