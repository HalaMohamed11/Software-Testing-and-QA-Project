package com.cryptochecker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Currency Converter Unit Tests")
class CurrencyConverterTest {

    private CurrencyConverter converter;
    private static final double DELTA = 0.00000001;

    @BeforeEach
    void setUp() {
        converter = new CurrencyConverter();
    }

    @Test
    @DisplayName("EP-Valid Positive: Should convert standard transaction amount")
    void testStandardConversion() {
        double result = converter.convert(2500.75, 50000.0, 3000.0);
        double expected = 2500.75 * (50000.0 / 3000.0);
        
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("BVA-Min Positive: Should handle minimum boundary (0.00000001)")
    void testMinimumBoundary() {
        double amount = 0.00000001;
        double result = converter.convert(amount, 50000.0, 3000.0);
        
        assertTrue(result > 0);
        assertTrue(result >= amount);
    }

    @Test
    @DisplayName("BVA-Zero: Should handle zero amount correctly")
    void testZeroAmount() {
        double result = converter.convert(0.0, 50000.0, 3000.0);
        assertEquals(0.0, result, DELTA);
    }

    @Test
    @DisplayName("EP-Large Integer: Should handle large amounts without precision loss")
    void testLargeAmount() {
        double amount = 9999999999.0;
        double result = converter.convert(amount, 50000.0, 3000.0);
        
        assertTrue(result > 0);
        assertFalse(Double.isInfinite(result));
    }

    @Test
    @DisplayName("EP-Invalid Negative: Should reject negative amounts")
    void testNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(-1.0, 50000.0, 3000.0);
        });
    }

    @ParameterizedTest
    @CsvSource({
        "100.0, 50000.0, 3000.0, 1666.666667",
        "1.0, 50000.0, 50000.0, 1.0",
        "0.5, 10000.0, 5000.0, 1.0"
    })
    @DisplayName("TC-CONV-008: Should maintain reciprocity in conversions")
    void testConversionReciprocity(double amount, double priceA, double priceB, double expected) {
        double resultAtoB = converter.convert(amount, priceA, priceB);
        assertEquals(expected, resultAtoB, 0.000001);
        
        double resultBtoA = converter.convert(resultAtoB, priceB, priceA);
        assertEquals(amount, resultBtoA, 0.000001);
    }

    @Test
    @DisplayName("FR5: Crypto-to-Crypto conversion accuracy")
    void testCryptoToCryptoConversion() {
        double result = converter.convert(2.0, 50000.0, 3000.0);
        double expected = 2.0 * (50000.0 / 3000.0);
        
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("FR6: Crypto-to-Fiat conversion accuracy")
    void testCryptoToFiatConversion() {
        double result = converter.convertToFiat(1.0, 50000.0);
        
        assertEquals(50000.0, result, DELTA);
    }

    @Test
    @DisplayName("TC-CONV-009: Should handle fiat precision (JPY - no decimals)")
    void testFiatPrecisionJPY() {
        double result = converter.convertToFiat(1.0, 5000000.0); 
        String formatted = converter.formatForFiat(result, "JPY");
        
        assertFalse(formatted.contains("."));
    }

    @Test
    @DisplayName("Should handle division by zero gracefully")
    void testDivisionByZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(100.0, 50000.0, 0.0);
        });
    }

    @Test
    @DisplayName("BVA-Max Value: Should handle overflow gracefully")
    void testMaxValueOverflow() {
        double maxValue = 1E308;
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(maxValue, 50000.0, 1.0);
        });
    }
}
