package com.cryptochecker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ConversionUtil - Enhanced Mockito Tests")
public class ConversionUtilTestEnhanced {
    
    @Mock
    private ConversionUtil mockConverter;
    
    private ConversionUtil realConverter;
    
    @BeforeEach
    void setUp() {
        realConverter = new ConversionUtil();
    }
        
    @Test
    @DisplayName("TC-CONV-MOCK-001: Mock conversion with fixed exchange rates")
    void testMockFixedExchangeRate() {

        when(mockConverter.cryptoToCrypto(1.0, "BTC", "ETH")).thenReturn(15.5);
        when(mockConverter.cryptoToCrypto(2.0, "BTC", "ETH")).thenReturn(31.0);
        
        assertEquals(15.5, mockConverter.cryptoToCrypto(1.0, "BTC", "ETH"));
        assertEquals(31.0, mockConverter.cryptoToCrypto(2.0, "BTC", "ETH"));
        
        verify(mockConverter, times(2)).cryptoToCrypto(anyDouble(), eq("BTC"), eq("ETH"));
    }
    
    @Test
    @DisplayName("TC-CONV-MOCK-002: Stub exception for invalid amounts")
    void testStubInvalidAmountException() {
        when(mockConverter.cryptoToCrypto(eq(-1.0), anyString(), anyString()))
            .thenThrow(new IllegalArgumentException("Amount must be positive"));
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> mockConverter.cryptoToCrypto(-1.0, "BTC", "ETH"));
        assertEquals("Amount must be positive", exception.getMessage());
    }
    
    @Test
    @DisplayName("TC-CONV-MOCK-003: Verify conversion method called with correct parameters")
    void testVerifyConversionParameters() {
        when(mockConverter.cryptoToCrypto(anyDouble(), anyString(), anyString()))
            .thenReturn(100.0);
        
        mockConverter.cryptoToCrypto(50.0, "BTC", "ETH");
        mockConverter.cryptoToCrypto(25.5, "ETH", "LTC");
        
        verify(mockConverter).cryptoToCrypto(50.0, "BTC", "ETH");
        verify(mockConverter).cryptoToCrypto(25.5, "ETH", "LTC");
        verify(mockConverter, never()).cryptoToCrypto(anyDouble(), eq("BTC"), eq("BTC"));
    }
    
    @Test
    @DisplayName("TC-CONV-MOCK-004: Mock precision handling for 8 decimal places")
    void testMockPrecisionHandling() {
        when(mockConverter.cryptoToCrypto(0.00000001, "BTC", "ETH"))
            .thenReturn(0.00000002);
        
        double result = mockConverter.cryptoToCrypto(0.00000001, "BTC", "ETH");
        
        assertEquals(0.00000002, result, 0.00000001);
        verify(mockConverter).cryptoToCrypto(0.00000001, "BTC", "ETH");
    }
    
    @Test
    @DisplayName("TC-CONV-MOCK-005: Stub different conversion rates for multiple pairs")
    void testMultipleCurrencyPairs() {
        when(mockConverter.cryptoToCrypto(1.0, "BTC", "ETH")).thenReturn(15.5);
        when(mockConverter.cryptoToCrypto(1.0, "ETH", "LTC")).thenReturn(50.0);
        when(mockConverter.cryptoToCrypto(1.0, "BTC", "LTC")).thenReturn(775.0);
        
        assertEquals(15.5, mockConverter.cryptoToCrypto(1.0, "BTC", "ETH"));
        assertEquals(50.0, mockConverter.cryptoToCrypto(1.0, "ETH", "LTC"));
        assertEquals(775.0, mockConverter.cryptoToCrypto(1.0, "BTC", "LTC"));
    }
    
    @Test
    @DisplayName("TC-CONV-MOCK-006: Spy to test real conversion with partial mocking")
    void testSpyRealConversion() {
        ConversionUtil spyConverter = spy(realConverter);
        
        when(spyConverter.cryptoToCrypto(999.0, "TEST", "MOCK")).thenReturn(1998.0);
        
        double realResult = spyConverter.cryptoToCrypto(1.0, "BTC", "ETH");
        assertEquals(2.0, realResult); 
        
        double mockResult = spyConverter.cryptoToCrypto(999.0, "TEST", "MOCK");
        assertEquals(1998.0, mockResult);
    }
    
    @Test
    @DisplayName("TC-CONV-MOCK-007: Verify conversion with boundary values")
    void testBoundaryValueConversions() {
        when(mockConverter.cryptoToCrypto(0.00000001, "BTC", "ETH")).thenReturn(0.00000002);
        when(mockConverter.cryptoToCrypto(1000000.0, "BTC", "ETH")).thenReturn(2000000.0);
        
        assertEquals(0.00000002, mockConverter.cryptoToCrypto(0.00000001, "BTC", "ETH"));
        assertEquals(2000000.0, mockConverter.cryptoToCrypto(1000000.0, "BTC", "ETH"));
        
        verify(mockConverter).cryptoToCrypto(0.00000001, "BTC", "ETH");
        verify(mockConverter).cryptoToCrypto(1000000.0, "BTC", "ETH");
    }
}
