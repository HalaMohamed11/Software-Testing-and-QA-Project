package com.cryptochecker;

import javax.swing.JPanel;

public class PanelPortfolio {
    public JPanel panel;

    public PanelPortfolio() {
        panel = new JPanel();
    }

    public void refreshPortfolio() {

    }

    public void serializePortfolio() {
    }

    public void reCreate() {
    }

    public void themeSwitch() {
        // Example: just repaint the panel with new theme colors
        this.panel.setBackground(Main.theme.background);
        this.panel.repaint();
    }
}