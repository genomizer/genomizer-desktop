package gui;

import java.awt.BorderLayout;

import javax.swing.*;

public class QuerySearchTab extends JPanel {
    private JPanel searchPanel;
    private JTextField searchField;

    public QuerySearchTab() {
	createSearchPanel();
	add(searchPanel, BorderLayout.NORTH);
    }

    private void createSearchPanel() {
	searchPanel = new JPanel();
	searchField = new JTextField("Use the builder below to create your search");
	searchPanel.add(searchField, BorderLayout.CENTER);
    }

    public static void main(String[] args) {


    }

}
