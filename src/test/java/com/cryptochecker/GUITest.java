package com.cryptochecker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GUI - White Box Testing")
public class GUITest {
    private GUI gui = new GUI();

    
    @Test
    @DisplayName("Should return 'Green' for positive trend")
    void testPositiveTrend() {
        String result = gui.getTrendColor(1);
        assertEquals("Green", result);
    }

    @Test
    @DisplayName("Should return 'Green' for zero trend")
    void testZeroTrend() {
        String result = gui.getTrendColor(0);
        assertEquals("Green", result);
    }

    @Test
    @DisplayName("Should return 'Red' for negative trend")
    void testNegativeTrend() {
        String result = gui.getTrendColor(-1);
        assertEquals("Red", result);
    }

    @Test
    @DisplayName("Should return 'Green' for large positive trend")
    void testLargePositiveTrend() {
        String result = gui.getTrendColor(100);
        assertEquals("Green", result);
    }

    @Test
    @DisplayName("Should return 'Red' for large negative trend")
    void testLargeNegativeTrend() {
        String result = gui.getTrendColor(-100);
        assertEquals("Red", result);
    }

    @Test
    @DisplayName("Should return 'Green' for boundary zero value")
    void testBoundaryZero() {
        String result = gui.getTrendColor(0);
        assertEquals("Green", result);
    }

    @Test
    @DisplayName("Should return correct color for minimum integer")
    void testMinimumInteger() {
        String result = gui.getTrendColor(Integer.MIN_VALUE);
        assertEquals("Red", result);
    }

    @Test
    @DisplayName("Should return correct color for maximum integer")
    void testMaximumInteger() {
        String result = gui.getTrendColor(Integer.MAX_VALUE);
        assertEquals("Green", result);
    }
}
