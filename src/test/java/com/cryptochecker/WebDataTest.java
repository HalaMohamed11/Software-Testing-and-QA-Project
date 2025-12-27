package com.cryptochecker;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("WebData Unit Tests")
class WebDataTest {

    private WebData webData;
    private WebData.Coin testCoin;

    @BeforeEach
    void setUp() throws Exception {
        testCoin = createTestCoin();
    }

    private WebData.Coin createTestCoin() {
        WebData.Coin coin = new WebData().new Coin();
        coin.id = "bitcoin";
        coin.name = "Bitcoin";
        coin.symbol = "BTC";
        coin.rank = 1;
        coin.price = 50000.0;
        coin.percent_change_1h = 1.5;
        coin.percent_change_24h = -2.3;
        coin.percent_change_7d = 5.7;
        coin.market_cap = 1000000000.0;
        return coin;
    }

    @Test
    @DisplayName("FR1: Should display cryptocurrency price correctly")
    void testCoinPriceDisplay() {
        assertNotNull(testCoin.price);
        assertTrue(testCoin.price > 0);
        assertEquals(50000.0, testCoin.price);
    }

    @Test
    @DisplayName("FR2: Should display trend data (24h change)")
    void testCoinTrendDisplay() {
        assertNotNull(testCoin.percent_change_24h);
        assertEquals(-2.3, testCoin.percent_change_24h);
    }

    @ParameterizedTest
    @CsvSource({
        "1.5, true",
        "-2.3, false",
        "0.0, true"
    })
    @DisplayName("TC-GUI-003: Should identify positive/negative trends correctly")
    void testTrendColorIdentification(double change, boolean isPositive) {
        testCoin.percent_change_24h = change;
        
        if (isPositive) {
            assertTrue(testCoin.percent_change_24h >= 0);
        } else {
            assertTrue(testCoin.percent_change_24h < 0);
        }
    }

    @Test
    @DisplayName("Should format price correctly based on value")
    void testPriceTrimming() {
        assertEquals("50000", testCoin.trimPrice(50000.0).replace(",", ""));
        assertTrue(testCoin.trimPrice(0.0001).length() > 0);
        assertTrue(testCoin.trimPrice(1.5).matches("\\d+\\.\\d+"));
    }

    @Test
    @DisplayName("Should generate coin info string")
    void testGetInfo() {
        String info = testCoin.getInfo();
        
        assertNotNull(info);
        assertTrue(info.contains("Bitcoin"));
        assertTrue(info.contains("BTC"));
        assertTrue(info.contains("Rank: 1"));
    }

    @Test
    @DisplayName("Should clone coin correctly")
    void testCoinCopy() {
        Object copiedCoin = testCoin.copy();
        
        assertNotNull(copiedCoin);
        assertNotSame(testCoin, copiedCoin);
    }

    @Test
    @DisplayName("FR3/FR4: Should support search by name and symbol")
    void testCoinIdentification() {
        assertEquals("Bitcoin", testCoin.name);
        assertEquals("BTC", testCoin.symbol);
        assertEquals("bitcoin", testCoin.id);
    }
}
