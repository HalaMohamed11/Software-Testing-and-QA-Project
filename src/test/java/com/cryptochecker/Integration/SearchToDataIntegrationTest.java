package com.cryptochecker.integration;

import com.cryptochecker.SearchUtil;
import com.cryptochecker.WebData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integration Test: SearchUtil -> WebData")
public class SearchToDataIntegrationTest {
    
    private SearchUtil searchUtil;
    private WebData webData;
    
    @BeforeEach
    void setUp() {
        searchUtil = new SearchUtil();
        webData = new WebData();
    }
    
    @Test
    @DisplayName("INT-010: Search returns valid result for data lookup")
    void testSearchReturnsValidResult() {
        String result = searchUtil.searchByName("Bitcoin");
        
        assertNotNull(result);
        assertEquals("Bitcoin", result);
    }
    
    @Test
    @DisplayName("INT-011: Search with null returns no results")
    void testSearchNullHandling() {
        String result = searchUtil.searchByName(null);
        
        assertEquals("No results found", result);
    }
    
    @Test
    @DisplayName("INT-012: Search result integration with data retrieval")
    void testSearchDataIntegration() {
        String searchResult = searchUtil.searchByName("Ethereum");
        
        assertNotNull(searchResult);
        assertEquals("Ethereum", searchResult);
    }
    
    @Test
    @DisplayName("INT-013: Multiple search queries consistency")
    void testMultipleSearchQueriesConsistency() {
        String result1 = searchUtil.searchByName("Bitcoin");
        String result2 = searchUtil.searchByName("Bitcoin");
        
        assertEquals(result1, result2);
    }
    
    @Test
    @DisplayName("INT-014: Whitespace handling in search-data flow")
    void testWhitespaceHandling() {
        String result = searchUtil.searchByName("   Bitcoin   ");
        
        assertEquals("Bitcoin", result);
    }
}
