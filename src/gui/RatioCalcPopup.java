package gui;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * "If the user wants to perform a ratio calculation while processing a file the
 * user has the option to press the Use ratio calculation button. When pressed a
 * popup window appears and the user gets the option to write in several ratio
 * calculation parameters. These parameters consists of eight parameters Ratio
 * calculation, Input reads cut-off, Chromosomes, Window size , Smooth type,
 * Step position, print mean and print zeros." - From technical documentation
 * '4.1.4 Process'
 * 
 * @author (of comment) c12oor
 * 
 */
public class RatioCalcPopup extends JFrame {
    
    private static final long serialVersionUID = 5949688340459992769L;
    
    private JPanel ratioPanel;
    private JPanel buttonPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    public JButton okButton = new JButton("Ok");
    public final JTextField inputReads = new JTextField();
    public final JTextField chromosome = new JTextField();
    public final JTextField ratioWindowSize = new JTextField();
    public final JTextField ratioStepPosition = new JTextField();
    public final JCheckBox ratioPrintMean = new JCheckBox("Print mean");
    public final JCheckBox ratioPrintZeros = new JCheckBox("Print zeros");
    public final JComboBox<String> single = new JComboBox<String>();
    public final JComboBox<String> ratioSmoothType = new JComboBox<String>();
    public ArrayList<String> ratioSmooth = new ArrayList<String>();
    public ArrayList<String> comboSingle = new ArrayList<String>();
    
    /**
     * Create a new RatioCalcPopup
     * 
     * @param parent
     */
    public RatioCalcPopup(final GUI parent) {
        
        URL url = ClassLoader.getSystemResource("icons/logo.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        setIconImage(img);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parent.getFrame().dispose();
            }
        });
        
        setTitle("Ratio calculation parameters");
        setResizable(false);
        setSize(new Dimension(480, 325));
        this.setLocationRelativeTo(parent.getFrame());
        placeComponents();
        setDefaultRatioPar();
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
    
    public void setUnusedRatioPar() {
        
        single.removeAllItems();
        ratioSmoothType.removeAllItems();
        inputReads.setText("");
        chromosome.setText("");
        ratioWindowSize.setText("");
        ratioStepPosition.setText("");
        single.addItem("");
        ratioSmoothType.addItem("");
        
    }
    
    /**
     * sets the ratio to be defau- ok, idk, TODO understand
     * 
     */
    public void setDefaultRatioPar() {
        
        single.removeAllItems();
        ratioSmoothType.removeAllItems();
        ratioSmooth.add("Median");
        ratioSmooth.add("Trimmed mean");
        comboSingle.add("single");
        comboSingle.add("double");
        ratioSmoothType.addItem(ratioSmooth.get(0));
        ratioSmoothType.addItem(ratioSmooth.get(1));
        single.addItem(comboSingle.get(0));
        single.addItem(comboSingle.get(1));
        inputReads.setText("4");
        chromosome.setText("0");
        ratioWindowSize.setText("150");
        ratioSmoothType.setSelectedIndex(0);
        ratioStepPosition.setText("7");
        
    }
    
    /**
     * Adds an ActionListener to the okButton
     * 
     * @see controller.Controller#OkListener()
     * @param listener
     */
    public void addOkListener(ActionListener listener) {
        okButton.addActionListener(listener);
    }
    
    public void hideRatioWindow() {
        this.setVisible(false);
    }
    
    /**
     * Read the popup entry fields and put together a parameter string[2].<br>
     * The first string containing space separated 'Single' 'Input
     * Reads' 'Chromosomes'.<br>
     * The second string containing space separated 'Window size',<br>
     * 'Smooth Type', 'Step Position', 'Print Type(mean)', and 'Print
     * Type(zeros)'.
     * 
     * @return
     */
    public String[] getRatioCalcParameters() {
        String[] s = new String[2];
        
        s[0] = getSingle() + " " + getInputReads() + " " + getChromosomes();
        
        s[1] = getWindowSize() + " " + getSmoothType() + " "
                + getStepPosition() + " " + getPrintType(ratioPrintMean) + " "
                + getPrintType(ratioPrintZeros);
        
        return s;
    }
    
    private String getPrintType(JCheckBox print) {
        if (print.isSelected()) {
            return "1";
        } else if (single.getItemAt(0).equals("")) {
            return "";
        } else {
            return "0";
        }
    }
    
    private String getSmoothType() {
        String smooth = "0";
        if (ratioSmoothType.getSelectedItem().toString().equals("Median")) {
            return "1";
        }
        return smooth;
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
    
    private String getStepPosition() {
        return ratioStepPosition.getText().trim();
    }
}
