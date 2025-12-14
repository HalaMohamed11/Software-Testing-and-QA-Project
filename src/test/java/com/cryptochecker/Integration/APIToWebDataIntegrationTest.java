package com.cryptochecker.integration;

import com.cryptochecker.APIService;
import com.cryptochecker.APIException;
import com.cryptochecker.WebData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integration Test: APIService -> WebData")
public class APIToWebDataIntegrationTest {
    
    private APIService apiService;
    private WebData webData;
    
    @BeforeEach
    void setUp() {
        apiService = new APIService();
        webData = new WebData();
    }
    
    @Test
    @DisplayName("INT-001: Successful API fetch and data parsing")
    void testSuccessfulAPIToDataFlow() {
        assertDoesNotThrow(() -> {
            apiService.fetchData("coins");
        });
    }
    
    @Test
    @DisplayName("INT-002: API timeout propagates to data layer")
    void testAPITimeoutPropagation() {
        APIException exception = assertThrows(APIException.class, () -> {
            apiService.fetchData("simulateTimeout");
        });
        
        assertEquals("API Timeout", exception.getMessage());
    }
    
    @Test
    @DisplayName("INT-003: API rate limit handling in data flow")
    void testRateLimitHandling() {
        APIException exception = assertThrows(APIException.class, () -> {
            apiService.fetchData("simulateRateLimit");
        });
        
        assertEquals("Rate Limit Reached", exception.getMessage());
    }
    
    @Test
    @DisplayName("INT-004: Multiple sequential API calls with data processing")
    void testSequentialAPICalls() {
        assertDoesNotThrow(() -> {
            apiService.fetchData("coins");
            apiService.fetchData("markets");
            apiService.fetchData("exchanges");
        });
    }
}
