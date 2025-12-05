package com.cryptochecker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SearchUtil - White Box Testing")
public class SearchUtilTest {
    private SearchUtil searchUtil = new SearchUtil();

    
    @Test
    @DisplayName("Should return 'No results found' for null input")
    void testNullInput() {
        String result = searchUtil.searchByName(null);
        assertEquals("No results found", result);
    }

    @Test
    @DisplayName("Should return 'No results found' for empty string")
    void testEmptyString() {
        String result = searchUtil.searchByName("");
        assertEquals("No results found", result);
    }

    @Test
    @DisplayName("Should return 'No results found' for whitespace only")
    void testWhitespaceOnly() {
        String result = searchUtil.searchByName("   ");
        assertEquals("No results found", result);
    }

    @Test
    @DisplayName("Should return trimmed name for valid input")
    void testValidInput() {
        String result = searchUtil.searchByName("Bitcoin");
        assertEquals("Bitcoin", result);
    }

    @Test
    @DisplayName("Should trim leading whitespace")
    void testLeadingWhitespace() {
        String result = searchUtil.searchByName("   Bitcoin");
        assertEquals("Bitcoin", result);
    }

    @Test
    @DisplayName("Should trim trailing whitespace")
    void testTrailingWhitespace() {
        String result = searchUtil.searchByName("Bitcoin   ");
        assertEquals("Bitcoin", result);
    }

    @Test
    @DisplayName("Should trim both leading and trailing whitespace")
    void testBothWhitespace() {
        String result = searchUtil.searchByName("   Bitcoin   ");
        assertEquals("Bitcoin", result);
    }

    @Test
    @DisplayName("Should handle case sensitivity")
    void testCaseSensitivity() {
        String result = searchUtil.searchByName("BITCOIN");
        assertEquals("BITCOIN", result);
    }

    @Test
    @DisplayName("Should handle special characters in search")
    void testSpecialCharacters() {
        String result = searchUtil.searchByName("Bitcoin-123");
        assertEquals("Bitcoin-123", result);
    }
}
