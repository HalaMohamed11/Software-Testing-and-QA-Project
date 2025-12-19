package com.cryptochecker.Integration;

import com.cryptochecker.WebData;
import com.cryptochecker.ConversionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integration Test: WebData -> ConversionUtil")
public class DataToConverterIntegrationTest {
    
    private WebData webData;
    private ConversionUtil converter;
    
    @BeforeEach
    void setUp() throws Exception {
        webData = new WebData();
        converter = new ConversionUtil();
    }
    @Test
    @DisplayName("INT-005: Data retrieval and conversion pipeline")
    void testDataToConversionPipeline() {
        double amount = 1.5;
        
        double result = converter.cryptoToCrypto(amount, "BTC", "ETH");
        
        assertTrue(result > 0);
        assertEquals(3.0, result);
    }
    
    @Test
    @DisplayName("INT-006: Invalid data handling in conversion")
    void testInvalidDataHandling() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.cryptoToCrypto(-1.0, "BTC", "ETH");
        });
    }
    
    @Test
    @DisplayName("INT-007: Precision maintained through data-conversion flow")
    void testPrecisionMaintenance() {
        double preciseAmount = 0.00000001;
        
        double result = converter.cryptoToCrypto(preciseAmount, "BTC", "ETH");
        
        assertEquals(0.00000002, result, 0.00000001);
    }
    
    @Test
    @DisplayName("INT-008: Multiple conversions with data consistency")
    void testMultipleConversionsConsistency() {
        double result1 = converter.cryptoToCrypto(1.0, "BTC", "ETH");
        double result2 = converter.cryptoToCrypto(1.0, "BTC", "ETH");
        double result3 = converter.cryptoToCrypto(2.0, "BTC", "ETH");
        
        assertEquals(result1, result2);
        assertEquals(result3, result1 * 2);
    }
    
    @Test
    @DisplayName("INT-009: Large value conversion handling")
    void testLargeValueConversion() {
        double largeAmount = 1000000.0;
        
        double result = converter.cryptoToCrypto(largeAmount, "BTC", "ETH");
        
        assertEquals(2000000.0, result);
    }
}
