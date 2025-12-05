package com.cryptochecker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("APIService - White Box Testing")
public class APIServiceTest {
    private APIService apiService = new APIService();

    
    @Test
    @DisplayName("Should throw APIException for simulateTimeout")
    void testFetchDataTimeout() {
        APIException exception = assertThrows(APIException.class, () -> {
            apiService.fetchData("simulateTimeout");
        });
        assertEquals("API Timeout", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw APIException for simulateRateLimit")
    void testFetchDataRateLimit() {
        APIException exception = assertThrows(APIException.class, () -> {
            apiService.fetchData("simulateRateLimit");
        });
        assertEquals("Rate Limit Reached", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle normal API call without exception")
    void testFetchDataSuccess() {
        assertDoesNotThrow(() -> {
            apiService.fetchData("coins");
        });
    }

    @Test
    @DisplayName("Should handle empty string as type parameter")
    void testFetchDataEmptyString() {
        assertDoesNotThrow(() -> {
            apiService.fetchData("");
        });
    }

    @Test
    @DisplayName("Should handle null type parameter gracefully")
    void testFetchDataNullType() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            apiService.fetchData(null);
        });
        assertNotNull(exception);
    }
}
