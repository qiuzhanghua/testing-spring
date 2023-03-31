package com.example;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;

public class InlineMockTest {
    @Test
    void testInlineMock() {
        Map mockMap = mock(Map.class);
        Assertions.assertThat(mockMap.size()).isEqualTo(0);
    }
}
