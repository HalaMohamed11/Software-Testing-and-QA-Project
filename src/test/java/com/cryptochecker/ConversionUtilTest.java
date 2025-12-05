package com.cryptochecker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConversionUtil - White Box Testing")
public class ConversionUtilTest {
    private ConversionUtil converter = new ConversionUtil();

    
    @Test
    @DisplayName("Should throw exception for negative amount")
    void testNegativeAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            converter.cryptoToCrypto(-1.5, "BTC", "ETH");
        });
        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for zero amount")
    void testZeroAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            converter.cryptoToCrypto(0, "BTC", "ETH");
        });
        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for amount below minimum precision")
    void testBelowMinimumPrecision() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            converter.cryptoToCrypto(0.000000001, "BTC", "ETH");
        });
        assertEquals("Amount below minimum precision", exception.getMessage());
    }

    @Test
    @DisplayName("Should accept amount at minimum precision boundary")
    void testAtMinimumPrecisionBoundary() {
        double result = converter.cryptoToCrypto(0.00000001, "BTC", "ETH");
        assertEquals(0.00000002, result, 0.00000001);
    }

    @Test
    @DisplayName("Should convert amounts correctly above minimum precision")
    void testValidConversion() {
        double result = converter.cryptoToCrypto(1.0, "BTC", "ETH");
        assertEquals(2.0, result);
    }

    @Test
    @DisplayName("Should convert large amounts correctly")
    void testLargeAmountConversion() {
        double result = converter.cryptoToCrypto(1000000.0, "BTC", "ETH");
        assertEquals(2000000.0, result);
    }

    @Test
    @DisplayName("Should handle decimal precision correctly")
    void testDecimalPrecision() {
        double result = converter.cryptoToCrypto(0.123456789, "BTC", "ETH");
        assertEquals(0.246913578, result, 0.00000001);
    }

    @Test
    @DisplayName("Should work with different currency pairs")
    void testDifferentCurrencyPairs() {
        double result = converter.cryptoToCrypto(5.5, "ETH", "LTC");
        assertEquals(11.0, result);
    }
}
