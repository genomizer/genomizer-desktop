package gui.sysadmin;

import gui.ComponentTitledBorder;

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

public class SysadminAnnotationPopup extends JPanel {

	private static final long serialVersionUID = -626744436260839622L;
	private JPanel addCategoriesPanel;
	private JButton addButton, removeButton;
	private ButtonModel createNewAnnotationButtonModel;
	private JTextField nameField;
	private ArrayList<String> categories = new ArrayList<String>();
	private boolean forced = false;
	private JCheckBox forcedBox;

	public SysadminAnnotationPopup() {
		this.setLayout(new BorderLayout());
		JTabbedPane optionsPane = new JTabbedPane();
		nameField = new JTextField();
		optionsPane.addTab("DropDownLists", buildFirstTab());
		optionsPane.addTab("Free Text", buildSecondTab());
		this.add(optionsPane, BorderLayout.CENTER);
	}

	private JPanel buildSecondTab() {

		JPanel secondTab = new JPanel(new GridLayout(0, 1));

		/* Create the top panel for the second tab */
		JPanel topPanelInSecondTab = new JPanel();

		JLabel name = new JLabel("Name:");
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

	private JPanel buildMidPanelInFirstTab() {

		JPanel midPanelInFirstTab = new JPanel(new BorderLayout());
		final JCheckBox catCheckBox = new JCheckBox("Add Categories", true);
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

	private JPanel createDeafultCategoryPanel(final JPanel addCategoriesPanel) {
		JPanel baseCatPanel = new JPanel();

		JLabel categorylabel = new JLabel("Category:");
		final JTextField annotationTextField = new JTextField();
		annotationTextField.setPreferredSize(new Dimension(200, 30));

		baseCatPanel.add(categorylabel);
		baseCatPanel.add(annotationTextField);

		createAddCategoryButton(addCategoriesPanel, baseCatPanel,
				annotationTextField);
		return baseCatPanel;
	}

	private void createAddCategoryButton(final JPanel categoryHolderPanel,
			JPanel baseCatPanel, final JTextField annotationTextField) {

		URL imageUrl = getClass().getResource("/icons/plus.png");
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

	private void createRemoveCategoryButton(final JPanel categoryPanel) {
		URL imageUrl = getClass().getResource("/icons/minus.png");
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
				repaint();

			}

		});
		categoryPanel.add(removeButton);
	}

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
		// JPanel checkboxPanel = createCheckBoxPanel();
		botPanelInFirstTab.add(forced);
		// botPanelInFirstTab.add(checkboxPanel);
		botPanelInFirstTab.add(forcedBox);
		buildCreateNewAnnotationButton(botPanelInFirstTab);

		return botPanelInFirstTab;
	}

	protected void switchForced() {
		forced = (forced == true) ? false : true;
	}

	private void buildCreateNewAnnotationButton(JPanel botPanelInFirstTab) {

		JButton createNewAnnotationButton = new JButton("Create annotation");

		if (createNewAnnotationButtonModel == null) {
			createNewAnnotationButtonModel = createNewAnnotationButton
					.getModel();
		} else {
			createNewAnnotationButton.setModel(createNewAnnotationButtonModel);
		}

		botPanelInFirstTab.add(createNewAnnotationButton);
	}

	private JPanel buildTopPanelInFirstTab() {
		JPanel topPanelInFirstTab = new JPanel(new BorderLayout());
		JLabel name = new JLabel("Name:");

		// nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(250, 30));

		JPanel nameFieldPanel = new JPanel();
		nameFieldPanel.add(name);
		nameFieldPanel.add(nameField);

		topPanelInFirstTab.add(nameFieldPanel);
		JLabel infoLabel = new JLabel(
				"Not adding any categories will result in a Yes/No/Unknown annotation");
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

	protected void addAddedCategoryPanel(JPanel categoryHolderPanel,
			JTextField annotationTextField) {

		JPanel newCategoryPanel = new JPanel();
		JLabel categoryLabel = new JLabel("Category:");
		final JTextField textField = new JTextField();
		textField.setText(annotationTextField.getText());
		textField.setEditable(false);
		textField.setPreferredSize(new Dimension(200, 30));

		newCategoryPanel.add(categoryLabel);
		newCategoryPanel.add(textField);

		categories.add(textField.getText());

		createRemoveCategoryButton(newCategoryPanel);
		categoryHolderPanel.add(newCategoryPanel);
		annotationTextField.setText("");
		repaint();
	}

	protected String getNewAnnotationName() {
		return nameField.getText();
	}

	protected Boolean getNewAnnotationForcedValue() {
		return forced;
	}

	protected String[] getNewAnnotationCategories() {

		String[] newCategories;

		// TODO: make a model for popup? this should not be in a pure view
		// class.
		synchronized (categories) {

			if (categories.isEmpty()) {
				categories.add("Yes");
				categories.add("No");
			}
			newCategories = categories.toArray(new String[categories.size()]);
		}

		return newCategories;
	}

	public void closeWindow() {
		JFrame frame = (JFrame) SwingUtilities
				.getWindowAncestor(addCategoriesPanel); // UGLY?!?
		frame.setVisible(false);
	}

	public void addAddAnnotationListener(ActionListener listener) {
		createNewAnnotationButtonModel.addActionListener(listener);
	}

}
