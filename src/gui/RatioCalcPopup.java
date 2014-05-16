package gui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class RatioCalcPopup extends JFrame {

    private JLabel errorLabel;
    private JPanel ratioPanel;
    private JPanel buttonPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    public JButton cancelButton = new JButton("Cancel");
    public JButton okButton = new JButton("Ok");
    private final JTextField inputReads = new JTextField();
    private final JTextField chromosome = new JTextField();
    private final JTextField ratioWindowSize = new JTextField();
    private final JTextField ratioStepPosition = new JTextField();
    private final JCheckBox ratioPrintMean = new JCheckBox("Print mean");
    private final JCheckBox ratioPrintZeros = new JCheckBox("Print zeros");
    private final JComboBox<String> single = new JComboBox<String>();
    private final JComboBox<String> ratioSmoothType = new JComboBox<String>();

    public RatioCalcPopup(final GenomizerView parent) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parent.getFrame().dispose();
            }
        });
        setTitle("Ratio calculation parameters");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(480, 325));
        this.setLocationRelativeTo(parent.getFrame());
        placeComponents();

        ArrayList<String> ratioSmooth = new ArrayList<String>();
        ArrayList<String> comboSingle = new ArrayList<String>();
        ratioSmooth.add("1");
        ratioSmooth.add("0");
        comboSingle.add("single");
        comboSingle.add("double");
        ratioSmoothType.addItem(ratioSmooth.get(0));
        ratioSmoothType.addItem(ratioSmooth.get(1));
        single.addItem(comboSingle.get(0));
        single.addItem(comboSingle.get(1));
        setUnusedRatioPar();
    }

    /**
     * Sets the layout and looks to the login window
     */
    private void placeComponents() {

        ratioPanel = new JPanel(new BorderLayout());

        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        topPanel.setBorder(BorderFactory
                .createTitledBorder("Ratio calculation"));
        centerPanel.setBorder(BorderFactory
                .createTitledBorder("Ratio smoothing"));
        bottomPanel.setBorder(BorderFactory.createTitledBorder(""));

        ratioPanel.add(topPanel, BorderLayout.NORTH);
        ratioPanel.add(centerPanel, BorderLayout.CENTER);
        ratioPanel.add(bottomPanel, BorderLayout.SOUTH);

        topPanel.add(single);
        topPanel.add(inputReads);
        topPanel.add(chromosome);
        centerPanel.add(ratioWindowSize);
        centerPanel.add(ratioSmoothType);
        centerPanel.add(ratioStepPosition);
        bottomPanel.add(ratioPrintZeros);
        bottomPanel.add(ratioPrintMean);
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);
        bottomPanel.add(buttonPanel);

        single.setPreferredSize(new Dimension(150, 60));
        inputReads.setBorder(BorderFactory
                .createTitledBorder("Input reads cut-off"));
        inputReads.setPreferredSize(new Dimension(160, 60));
        chromosome.setBorder(BorderFactory.createTitledBorder("Chromosomes"));
        chromosome.setPreferredSize(new Dimension(120, 60));

        ratioWindowSize.setBorder(BorderFactory
                .createTitledBorder("Window size"));
        ratioWindowSize.setPreferredSize(new Dimension(120, 60));
        ratioSmoothType.setBorder(BorderFactory
                .createTitledBorder("Smooth type"));
        ratioSmoothType.setPreferredSize(new Dimension(120, 60));
        ratioStepPosition.setBorder(BorderFactory
                .createTitledBorder("Step position"));
        ratioStepPosition.setPreferredSize(new Dimension(120, 60));

        this.add(ratioPanel);

    }

    /**
     * Adds listener to the loginbutton
     *
     * @param listener
     *            The listener to login to the server
     */
    public void addOkListener(ActionListener listener) {
        okButton.addActionListener(listener);
    }

    public void addCancelListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public void updateLoginFailed(String errorMessage) {
        paintErrorMessage(errorMessage);
    }

    public void paintErrorMessage(String message) {
        errorLabel = new JLabel("<html><b>" + message + "</b></html>");
        errorLabel.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
        errorLabel.setBounds(120, 100, 150, 45);
        ratioPanel.add(errorLabel);
        repaint();
    }

    public void removeErrorMessage() {
        if (errorLabel != null) {
            ratioPanel.remove(errorLabel);
            errorLabel = null;
            repaint();
        }
    }

    public String[] getRatioCalcParameters() {
        String[] s = new String[2];

        s[0] = getSingle() + " " + getInputReads() + " " + getChromosomes();
        // s[1] = "150 1 7 0 0";

        s[1] = getWindowSize() + " " + getSmoothType() + " "
                + getStepPosition() + " " + getPrintMean() + " "
                + getPrintZeros();

        return s;
    }

    private String getPrintZeros() {
        if (ratioPrintZeros.isSelected()) {
            return "1";
        } else {
            return "0";
        }
    }

    private String getPrintMean() {
        if (ratioPrintMean.isSelected()) {
            return "1";
        } else {
            return "0";
        }
    }

    private String getStepPosition() {
        return ratioStepPosition.getText().trim();
    }

    private String getSmoothType() {
        return ratioSmoothType.getSelectedItem().toString().trim();
    }

    private String getWindowSize() {
        return ratioWindowSize.getText().trim();
    }

    private String getChromosomes() {
        return chromosome.getText().trim();
    }

    private String getInputReads() {
        return inputReads.getText().trim();
    }

    private String getSingle() {
        return single.getSelectedItem().toString().trim();
    }

    private void setUnusedRatioPar() {

        inputReads.setText("");
        chromosome.setText("");
        ratioWindowSize.setText("");
        ratioSmoothType.setSelectedIndex(0);
        ratioStepPosition.setText("");
    }

    public void setDefaultRatioPar() {

        inputReads.setText("4");
        chromosome.setText("0");
        ratioWindowSize.setText("150");
        ratioSmoothType.setSelectedIndex(0);
        ratioStepPosition.setText("7");

    }

    public void closeRatioWindow(){
        this.setVisible(false);
    }
}
