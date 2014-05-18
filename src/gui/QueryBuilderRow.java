package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import util.AnnotationDataType;
import util.IconFactory;

/**
 * Class the represents a row in the query builder
 * 
 * @author bDtKarlsson
 */
public class QueryBuilderRow extends JPanel {
    private JComboBox annotationBox;
    private JComboBox annotationAlternatives;
    private JTextField textField;
    private JButton plusButton;
    private JButton minusButton;
    private JComboBox logicBox;
    private QuerySearchTab parent;
    private AnnotationDataType[] annotationTypes;
    private static final String[] logicOperators = { "AND", "NOT", "OR" };
    private boolean dropdown = false;
    private boolean firstRow = false;
    private boolean lastRow = false;
    
    public QueryBuilderRow(QuerySearchTab parent,
            AnnotationDataType[] annotationTypes) {
        /* The Parent query search tab */
        this.parent = parent;
        /* The annotation information */
        this.annotationTypes = annotationTypes;
        setLayout(new FlowLayout());
        /* Set up the components (fieldBox must be set last) */
        setPlusButton();
        setMinusButton();
        setLogicBox();
        setTextField();
        setAnnotationAlternatives(new String[0]);
        setAnnotationBox(annotationTypes);
//        setBorder(BorderFactory
//                .createTitledBorder(""));
    }
    
    /**
     * Method for setting information about the row
     * 
     * @param firstRow
     *            - if the row is the first row
     * @param lastRow
     *            - if the row is the last row
     */
    public void setAs(Boolean firstRow, Boolean lastRow) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        /* Remove all current components */
        removeAll();
        /* Create the panels */
        JPanel logicPanel = new JPanel();
        logicPanel.setPreferredSize(new Dimension(70, 35));
        JPanel annotationPanel = new JPanel();
        annotationPanel.setPreferredSize(new Dimension(240, 35));
        JPanel inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(450, 35));
        JPanel firstButtonPanel = new JPanel();
        firstButtonPanel.setPreferredSize(new Dimension(25, 35));
        JPanel secondButtonPanel = new JPanel();
        secondButtonPanel.setPreferredSize(new Dimension(25, 35));
        /* All rows except the first should have a logic field */
        if (!firstRow) {
            logicPanel.add(logicBox);
        }
        /* All rows should have a annotation field */
        annotationPanel.add(annotationBox);
        /*
         * If the selected annotation has predefinied values the row should have
         * a dropdown menu with annotation alternatives, else it should have a
         * text field for free text input
         */
        if (dropdown) {
            inputPanel.add(annotationAlternatives);
        } else {
            inputPanel.add(textField);
        }
        /*
         * All rows except if there is only one row in the builder should have a
         * minus button
         */
        if (!(firstRow && lastRow)) {
            firstButtonPanel.add(minusButton);
        }
        /* The last row shoyld have a plus button */
        if (lastRow) {
            if(firstRow) {
                firstButtonPanel.add(plusButton);
            } else {
                secondButtonPanel.add(plusButton);
            }

        }
        
        add(logicPanel);
        add(annotationPanel);
        add(inputPanel);
        add(firstButtonPanel);
        add(secondButtonPanel);
    }
    
    /**
     * Method for constructing the plus button
     */
    private void setPlusButton() {
        plusButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getPlusIcon(15, 15),
                IconFactory.getPlusHoverIcon(17, 17), 17, 25, null);
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Add a row to the parent when button is clicked */
                parent.addRow();
            }
        });
    }
    
    /**
     * Method for constructing a minus button
     */
    private void setMinusButton() {
        minusButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getMinusIcon(15, 15),
                IconFactory.getMinusHoverIcon(17, 17), 17, 25, null);
        final QueryBuilderRow row = this;
        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Remove the row and update the search area in the parent */
                parent.removeRow(row);
                parent.updateSearchArea();
            }
        });
    }
    
    /**
     * Method for constructing the text field for free text input
     */
    private void setTextField() {
        textField = new JTextField(40);
        /*
         * The search field in the parent is updated when the text field content
         * is changed
         */
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                parent.updateSearchArea();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                parent.updateSearchArea();
            }
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                parent.updateSearchArea();
            }
        });
    }
    
    /**
     * Method for constructing the annotation alternatives combobox
     * 
     * @param alternatives
     *            - the annotation alternatives
     */
    private void setAnnotationAlternatives(String[] alternatives) {
        annotationAlternatives = new JComboBox(alternatives);
        /* Setting the width of the combobox */
        annotationAlternatives
                .setPrototypeDisplayValue("AAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        annotationAlternatives.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Update search area in parent when selected item is changed */
                parent.updateSearchArea();
            }
        });
    }
    
    /**
     * Method for creating the annotations combobox
     * 
     * @param annotations
     *            - the annotations
     */
    public void setAnnotationBox(AnnotationDataType[] annotations) {
        this.annotationTypes = annotations;
        /* Get the annotation names */
        String[] annotationNames = new String[annotationTypes.length];
        for (int i = 0; i < annotationTypes.length; i++) {
            annotationNames[i] = annotationTypes[i].getName();
        }
        if (annotationNames.length > 0) {
            annotationBox = new JComboBox(annotationNames);
        } else {
            annotationBox = new JComboBox();
        }
        annotationBox.setPrototypeDisplayValue("AAAAAAAAAAAAAAAAAAAAAAA");
        annotationBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * When the selected item in the box is changed a check must be
                 * made to know wether a free text field or a annotation
                 * alternatives box should be displayed
                 */
                String annotation = (String) annotationBox.getSelectedItem();
                for (int i = 0; i < annotationTypes.length; i++) {
                    if (annotation.equals(annotationTypes[i].getName())) {
                        String[] values = annotationTypes[i].getValues();
                        if (values != null) {
                            if (!values[0].equals("freetext")) {
                                /* Dropdown annotation alternatives box */
                                dropdown = true;
                                setAnnotationAlternatives(values);
                            } else {
                                /* Free text field */
                                dropdown = false;
                            }
                            /* Update row and parent search area */
                            setAs(firstRow, lastRow);
                            parent.updateSearchArea();
                            repaint();
                            revalidate();
                        }
                    }
                }
            }
        });
        /*
         * Change selected item so the actionlistener will be called upon
         * starting the program
         */
        if (annotationBox.getSelectedIndex() != -1) {
            annotationBox.setSelectedIndex(0);
        }
    }
    
    /**
     * Method for creating the logic box
     */
    private void setLogicBox() {
        logicBox = new JComboBox(logicOperators);
        logicBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * Update the parent search area when selected logic operator is
                 * changed
                 */
                parent.updateSearchArea();
            }
        });
    }
    
    /**
     * Get text either from the freetext field or the annotation alternatives
     * box
     * 
     * @return
     */
    public String getText() {
        if (!dropdown) {
            return textField.getText();
        } else {
            return (String) annotationAlternatives.getSelectedItem();
        }
    }
    
    /**
     * Get the selected logic operator
     * 
     * @return logic operator
     */
    public String getLogic() {
        return (String) logicBox.getSelectedItem();
    }
    
    /**
     * Get the selected annotation
     * 
     * @return the annotation
     */
    public String getAnnotation() {
        return (String) annotationBox.getSelectedItem();
    }
}
