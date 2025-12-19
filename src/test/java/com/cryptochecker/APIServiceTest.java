package com.cryptochecker;

import com.cryptochecker.Integration.APIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("APIService - Enhanced Mockito Tests")
public class APIServiceTest {

    @Mock
    private APIService mockApiService;

    private APIService realApiService;

    @BeforeEach
    void setUp() {
        realApiService = new APIService();
    }

    @Test
    @DisplayName("TC-API-MOCK-001: Stub multiple sequential calls with different behaviors")
    void testStubSequentialCalls() throws APIException {
        doNothing()
                .doThrow(new APIException("Rate Limit Reached"))
                .when(mockApiService).fetchData("coins");

        assertDoesNotThrow(() -> mockApiService.fetchData("coins"));
        assertThrows(APIException.class, () -> mockApiService.fetchData("coins"));

        verify(mockApiService, times(2)).fetchData("coins");
    }

    @Test
    @DisplayName("TC-API-MOCK-002: Verify method never called for invalid scenarios")
    void testVerifyNeverCalled() throws APIException {
        doNothing().when(mockApiService).fetchData("valid");

        mockApiService.fetchData("valid");

        verify(mockApiService).fetchData("valid");
        verify(mockApiService, never()).fetchData("invalid");
        verify(mockApiService, never()).fetchData("simulateTimeout");
    }

    @Test
    @DisplayName("TC-API-MOCK-003: Verify exact number of invocations")
    void testVerifyInvocationCount() throws APIException {
        doNothing().when(mockApiService).fetchData(anyString());

        mockApiService.fetchData("coins");
        mockApiService.fetchData("coins");
        mockApiService.fetchData("markets");

        verify(mockApiService, times(2)).fetchData("coins");
        verify(mockApiService, times(1)).fetchData("markets");
        verify(mockApiService, times(3)).fetchData(anyString());
    }

    @Test
    @DisplayName("TC-API-MOCK-004: Stub with argument matchers")
    void testStubWithArgumentMatchers() throws APIException {
        doThrow(new APIException("Simulated Error"))
                .when(mockApiService).fetchData(contains("simulate"));

        assertThrows(APIException.class, () -> mockApiService.fetchData("simulateTimeout"));
        assertThrows(APIException.class, () -> mockApiService.fetchData("simulateRateLimit"));
    }

    @Test
    @DisplayName("TC-API-MOCK-005: Verify interaction order")
    void testVerifyInteractionOrder() throws APIException {
        doNothing().when(mockApiService).fetchData(anyString());

        mockApiService.fetchData("coins");
        mockApiService.fetchData("markets");
        mockApiService.fetchData("exchanges");

        var inOrder = inOrder(mockApiService);
        inOrder.verify(mockApiService).fetchData("coins");
        inOrder.verify(mockApiService).fetchData("markets");
        inOrder.verify(mockApiService).fetchData("exchanges");
    }

    @Test
    @DisplayName("TC-API-MOCK-006: Stub with custom exception messages")
    void testCustomExceptionMessages() throws APIException {
        doThrow(new APIException("Timeout: Server not responding"))
                .when(mockApiService).fetchData("timeout");
        doThrow(new APIException("Rate Limit: 429 Too Many Requests"))
                .when(mockApiService).fetchData("rateLimit");
        doThrow(new APIException("Not Found: 404 Resource Missing"))
                .when(mockApiService).fetchData("notFound");

        APIException timeoutEx = assertThrows(APIException.class,
                () -> mockApiService.fetchData("timeout"));
        assertEquals("Timeout: Server not responding", timeoutEx.getMessage());

        APIException rateLimitEx = assertThrows(APIException.class,
                () -> mockApiService.fetchData("rateLimit"));
        assertEquals("Rate Limit: 429 Too Many Requests", rateLimitEx.getMessage());

        APIException notFoundEx = assertThrows(APIException.class,
                () -> mockApiService.fetchData("notFound"));
        assertEquals("Not Found: 404 Resource Missing", notFoundEx.getMessage());
    }

    @Test
    @DisplayName("TC-API-MOCK-007: Spy on real object to verify partial mocking")
    void testSpyOnRealObject() throws APIException {
        APIService spyApiService = spy(realApiService);

        doThrow(new APIException("Mocked Timeout"))
                .when(spyApiService).fetchData("mockTimeout");

        assertDoesNotThrow(() -> spyApiService.fetchData("coins"));
        assertThrows(APIException.class, () -> spyApiService.fetchData("mockTimeout"));

        verify(spyApiService).fetchData("coins");
        verify(spyApiService).fetchData("mockTimeout");
    }

    @Test
    @DisplayName("TC-API-MOCK-008: Test timeout with delay simulation")
    void testTimeoutScenario() throws APIException {
        doAnswer(invocation -> {
            Thread.sleep(100);
            throw new APIException("API Timeout");
        }).when(mockApiService).fetchData("slowEndpoint");

        APIException exception = assertThrows(APIException.class,
                () -> mockApiService.fetchData("slowEndpoint"));
        assertEquals("API Timeout", exception.getMessage());
    }
}
