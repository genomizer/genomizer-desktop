package gui.sysadmin.annotationview;

import gui.sysadmin.strings.SysStrings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

/**
 * This class builds the popup generated when the user wants to add a new
 * annotation.
 * */

public class AddAnnotationPopup extends JPanel {

    private static final int FREETEXT_TAB = 1;
    private static final long serialVersionUID = -626744436260839622L;
    private JPanel addCategoriesPanel;
    private JButton addButton, removeButton;
    private ButtonModel createNewAnnotationButtonModel;
    private JTextField nameField;
    private boolean forced = false;
    private JCheckBox forcedBox;
    private ArrayList<JTextField> valueTexts = new ArrayList<JTextField>();
    private JTabbedPane optionsPane;

    public AddAnnotationPopup() {
        this.setLayout(new BorderLayout());
        optionsPane = new JTabbedPane();
        nameField = new JTextField();
        optionsPane.addTab("DropDownLists", buildFirstTab());
        optionsPane.addTab("Free Text", buildSecondTab());
        this.add(optionsPane, BorderLayout.CENTER);
    }

    /**
     * @return a JPanel containing the second tab within the popup
     */
    private JPanel buildSecondTab() {

        JPanel secondTab = new JPanel(new GridLayout(0, 1));

        /* Create the top panel for the second tab */
        JPanel topPanelInSecondTab = new JPanel();

        JLabel name = new JLabel("Annotation name:");
        JTextField nameField2 = new JTextField(nameField.getDocument(), "", 0);
        nameField2.setPreferredSize(new Dimension(250, 30));
        topPanelInSecondTab.add(name);
        topPanelInSecondTab.add(nameField2);

        /* Create bottom panel for the second tab */
        JPanel botPanelInSecondTab = buildBotPanelInFirstTab();

        secondTab.add(topPanelInSecondTab);
        secondTab.add(botPanelInSecondTab);
        return secondTab;
    }

    /**
     * @return a JScrollPane in which the first tab of the popup is contained
     */
    private JScrollPane buildFirstTab() {

        JPanel firstTab = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(firstTab);
        scrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel topPanelInFirstTab = buildTopPanelInFirstTab();
        JPanel midPanelInFirstTab = buildMidPanelInFirstTab();
        JPanel botPanelInFirstTab = buildBotPanelInFirstTab();

        /* Add all complete panels to the first tab */
        firstTab.add(topPanelInFirstTab, BorderLayout.NORTH);
        firstTab.add(midPanelInFirstTab, BorderLayout.CENTER);
        firstTab.add(botPanelInFirstTab, BorderLayout.SOUTH);

        return scrollPane;

    }

    /**
     * Builds everything contained in the mid panel of the first tab of the
     * popup
     * 
     * @return the JPanel with the contents
     */
    private JPanel buildMidPanelInFirstTab() {

        JPanel midPanelInFirstTab = new JPanel(new BorderLayout());
        final JCheckBox catCheckBox = new JCheckBox("Add Values", true);
        catCheckBox.setFocusPainted(false);

        final JPanel categoryPanel = new JPanel(new BorderLayout());

        addCategoriesPanel = new JPanel(new GridLayout(0, 1));

        JPanel baseCatPanel = createDeafultCategoryPanel(addCategoriesPanel);
        addCategoriesPanel.add(baseCatPanel);

        categoryPanel.add(addCategoriesPanel, BorderLayout.NORTH);

        ComponentTitledBorder componentBorder = createDynamicBorder(
                categoryPanel, catCheckBox);
        categoryPanel.setBorder(componentBorder);

        midPanelInFirstTab.add(categoryPanel, BorderLayout.CENTER);

        return midPanelInFirstTab;
    }

    /**
     * Creates the panel where the values of the new annotation will be filled
     * in
     * 
     * @param addCategoriesPanel
     * @return the JPanel with a field and a button
     */
    private JPanel createDeafultCategoryPanel(final JPanel addCategoriesPanel) {
        JPanel baseCatPanel = new JPanel();

        JLabel categorylabel = new JLabel("Value:");
        final JTextField annotationTextField = new JTextField();
        valueTexts.add(annotationTextField);
        annotationTextField.setPreferredSize(new Dimension(200, 30));

        baseCatPanel.add(categorylabel);
        baseCatPanel.add(annotationTextField);

        createAddCategoryButton(addCategoriesPanel, baseCatPanel,
                annotationTextField);
        return baseCatPanel;
    }

    /**
     * @param categoryHolderPanel
     *            the panel holding the buttonpanel
     * @param baseCatPanel
     *            the panel holding the button and textfield
     * @param annotationTextField
     *            the textfield connected to the button
     */
    private void createAddCategoryButton(final JPanel categoryHolderPanel,
            JPanel baseCatPanel, final JTextField annotationTextField) {

        URL imageUrl = getClass().getResource("/icons/plus2.png");
        ImageIcon addIcon = new ImageIcon(imageUrl);
        addIcon = new ImageIcon(addIcon.getImage().getScaledInstance(20, 20,
                BufferedImage.SCALE_SMOOTH));
        addButton = new JButton("");

        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false);

        addButton.setIcon(addIcon);

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!annotationTextField.getText().equals("")) {
                    addAddedCategoryPanel(categoryHolderPanel,
                            annotationTextField);
                }
            }

        });
        baseCatPanel.add(addButton);
    }

    /**
     * Creates a button for removing values from the annotation to be created
     * 
     * @param categoryPanel
     *            is the panel holding the button
     */
    private void createRemoveCategoryButton(final JPanel categoryPanel) {
        URL imageUrl = getClass().getResource("/icons/minus2.png");
        ImageIcon removeIcon = new ImageIcon(imageUrl);
        removeIcon = new ImageIcon(removeIcon.getImage().getScaledInstance(15,
                15, BufferedImage.SCALE_SMOOTH));
        removeButton = new JButton("");

        removeButton.setBorderPainted(false);
        removeButton.setContentAreaFilled(false);

        removeButton.setIcon(removeIcon);

        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                categoryPanel.getParent().remove(categoryPanel);
                for (Component c : categoryPanel.getComponents()) {
                    if (c.getName() != null && c.getName().equals("textField")) {
                        valueTexts.remove(c);
                    }
                }
                repaint();

            }

        });
        categoryPanel.add(removeButton);
    }

    /**
     * Builds the bottom panel of the first tab, containing a checkbox and a
     * label
     * 
     * @return the JPanel containing the "forced" checkbox and label
     */
    private JPanel buildBotPanelInFirstTab() {
        JPanel botPanelInFirstTab = new JPanel();
        JLabel forced = new JLabel("Forced Annotation:");
        forcedBox = new JCheckBox("Yes");
        forcedBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switchForced();

            }
        });
        botPanelInFirstTab.add(forced);
        botPanelInFirstTab.add(forcedBox);
        buildCreateNewAnnotationButton(botPanelInFirstTab);

        return botPanelInFirstTab;
    }

    /**
     * Switches the value of whether the created annotation should be forced or
     * not
     */
    protected void switchForced() {
        forced = (forced == true) ? false : true;
    }

    /**
     * Creates the button the user will press when he/she wants to create the
     * annotation as specified
     * 
     * @param botPanelInFirstTab
     *            the panel containing the button
     */
    private void buildCreateNewAnnotationButton(JPanel botPanelInFirstTab) {

        JButton createNewAnnotationButton = new JButton(
                SysStrings.ANNOTATIONS_POPUP_CREATE_ANNO);



        if (createNewAnnotationButtonModel == null) {
            createNewAnnotationButtonModel = createNewAnnotationButton
                    .getModel();
        } else {
            createNewAnnotationButton.setModel(createNewAnnotationButtonModel);
        }
        createNewAnnotationButtonModel
                .setActionCommand(SysStrings.ANNOTATIONS_POPUP_CREATE_ANNO);
        botPanelInFirstTab.add(createNewAnnotationButton);
    }

    /**
     * Builds the top panel in the first tab, where the textfield is located
     * which will hold the name of the new annotation
     * 
     * @return the top JPanel
     */
    private JPanel buildTopPanelInFirstTab() {
        JPanel topPanelInFirstTab = new JPanel(new BorderLayout());
        JLabel name = new JLabel("Annotation name:");

        nameField.setPreferredSize(new Dimension(250, 30));

        JPanel nameFieldPanel = new JPanel();
        nameFieldPanel.add(name);
        nameFieldPanel.add(nameField);

        topPanelInFirstTab.add(nameFieldPanel);
        JLabel infoLabel = new JLabel(
                "Not adding any values will result in a Yes/No/Unknown drop down annotation.");
        topPanelInFirstTab.add(infoLabel, BorderLayout.SOUTH);
        return topPanelInFirstTab;
    }

    private ComponentTitledBorder createDynamicBorder(
            final JPanel categoryPanel, final JCheckBox catCheckBox) {

        ComponentTitledBorder componentBorder = new ComponentTitledBorder(
                catCheckBox, categoryPanel, BorderFactory.createEtchedBorder());

        catCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setBorderEnabled(categoryPanel, catCheckBox);
            }
        });

        return componentBorder;
    }


    private void setBorderEnabled(final JPanel categoryPanel,
            final JCheckBox catCheckBox) {
        boolean enable = catCheckBox.isSelected();
        Component components[] = categoryPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            component.setEnabled(enable);
            if (component instanceof JPanel) {
                setBorderEnabled((JPanel) component, catCheckBox);
            }
        }
    }

    /**
     * Creates a new panel for a new value of the annotation which is to be
     * created
     * 
     * @param categoryHolderPanel
     *            is the panel which will hold the panel
     * @param annotationTextField
     *            is the text field within the panel
     */
    public void addAddedCategoryPanel(JPanel categoryHolderPanel,
            JTextField annotationTextField) {

        JPanel newCategoryPanel = new JPanel();
        JLabel categoryLabel = new JLabel("Value:");
        final JTextField textField = new JTextField();
        textField.setName("textField");
        textField.setText(annotationTextField.getText());
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(200, 30));

        newCategoryPanel.add(categoryLabel);
        newCategoryPanel.add(textField);

        valueTexts.add(textField);

        createRemoveCategoryButton(newCategoryPanel);
        categoryHolderPanel.add(newCategoryPanel);
        annotationTextField.setText("");
        repaint();
    }

    /**
     * @return the string currently in the annotation name field as a String
     */
    public String getNewAnnotationName() {
        return nameField.getText();
    }

    /**
     * @return the "forced" value of the new annotation
     */
    public boolean getNewAnnotationForcedValue() {
        return forced;
    }

    /**
     * Goes through the textfields and gets all the values of the new
     * annotations
     * 
     * @return an array of the values as Strings
     */
    public String[] getNewAnnotationCategories() {

        ArrayList<String> categories = new ArrayList<String>();



        if (optionsPane.getSelectedIndex() == FREETEXT_TAB) {
            return new String[] { "freetext" };
        }

        synchronized (valueTexts) {

            for (JTextField field : valueTexts) {
                if (!field.getText().isEmpty()) {
                    categories.add(field.getText());
                }
            }
            if (isCategoriesEmpty(categories)) {
                categories.clear();
                categories.add("Yes");
                categories.add("No");
                categories.add("Unknown");
            }
        }

        return categories.toArray(new String[categories.size()]);
    }

    /**
     * Checks if the categories field is empty
     * 
     * @param categories
     *            is the arraylist of Strings containing the categories
     * @return true if empty, otherwise false
     */
    private boolean isCategoriesEmpty(ArrayList<String> categories) {
        for (String s : categories) {
            if (!s.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Closes the popup window
     */
    public void closeWindow() {
        JFrame frame = (JFrame) SwingUtilities
                .getWindowAncestor(addCategoriesPanel);
        frame.setVisible(false);
    }

    /**
     * Adds an ActionListener to new annotation button model
     * 
     * @param listener
     *            is the listener to be added
     */
    public void addAddAnnotationListener(ActionListener listener) {


        createNewAnnotationButtonModel.addActionListener(listener);
    }

}
