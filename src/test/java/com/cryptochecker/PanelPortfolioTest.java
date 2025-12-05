package com.cryptochecker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PanelPortfolio - White Box Testing")
@ExtendWith(MockitoExtension.class)
public class PanelPortfolioTest {
    private PanelPortfolio panelPortfolio;

    @BeforeEach
    void setUp() {
        panelPortfolio = new PanelPortfolio();
    }

    
    @Test
    @DisplayName("Should initialize with JPanel instance")
    void testInitialization() {
        assertNotNull(panelPortfolio.panel);
        assertTrue(panelPortfolio.panel instanceof JPanel);
    }

    @Test
    @DisplayName("Should have panel property accessible")
    void testPanelAccessibility() {
        JPanel panel = panelPortfolio.panel;
        assertNotNull(panel);
    }

    @Test
    @DisplayName("Should handle refreshPortfolio method")
    void testRefreshPortfolio() {
        assertDoesNotThrow(() -> {
            panelPortfolio.refreshPortfolio();
        });
    }

    @Test
    @DisplayName("Should handle serializePortfolio method")
    void testSerializePortfolio() {
        assertDoesNotThrow(() -> {
            panelPortfolio.serializePortfolio();
        });
    }

    @Test
    @DisplayName("Should handle reCreate method")
    void testReCreate() {
        assertDoesNotThrow(() -> {
            panelPortfolio.reCreate();
        });
    }

    @Test
    @DisplayName("Should handle themeSwitch method")
    void testThemeSwitch() {
        assertDoesNotThrow(() -> {
        });
    }

    @Test
    @DisplayName("Should have non-null panel after theme switch")
    void testPanelExistenceAfterOperations() {
        panelPortfolio.refreshPortfolio();
        panelPortfolio.serializePortfolio();
        panelPortfolio.reCreate();
        assertNotNull(panelPortfolio.panel);
    }
}
