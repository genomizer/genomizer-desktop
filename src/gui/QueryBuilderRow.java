package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
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
        JPanel logicPanel = new JPanel(new BorderLayout());
        logicPanel.setPreferredSize(new Dimension(70, 30));
        JPanel annotationPanel = new JPanel(new BorderLayout());
        annotationPanel.setPreferredSize(new Dimension(240, 30));
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setPreferredSize(new Dimension(450, 30));
        JPanel firstButtonPanel = new JPanel();
        firstButtonPanel.setPreferredSize(new Dimension(25, 30));
        JPanel secondButtonPanel = new JPanel();
        secondButtonPanel.setPreferredSize(new Dimension(25, 30));
        /* All rows except the first should have a logic field */
        if (!firstRow) {
            logicPanel.add(logicBox, BorderLayout.CENTER);
        }
        /* All rows should have a annotation field */
        annotationPanel.add(annotationBox, BorderLayout.CENTER);
        /*
         * If the selected annotation has predefinied values the row should have
         * a dropdown menu with annotation alternatives, else it should have a
         * text field for free text input
         */
        if (dropdown) {
            inputPanel.add(annotationAlternatives, BorderLayout.CENTER);
        } else {
            inputPanel.add(textField, BorderLayout.CENTER);
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
            if (firstRow) {
                firstButtonPanel.add(plusButton);
            } else {
                secondButtonPanel.add(plusButton);
            }
            
        }
        
        add(logicPanel);
        add(Box.createHorizontalStrut(5));
        add(annotationPanel);
        add(Box.createHorizontalStrut(5));
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
        textField = new JTextField();
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
        // annotationAlternatives
        // .setPrototypeDisplayValue("AAAAAAAAAAAAAAAAAAAAAAA"
        // + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
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
        String[] annotationNames = new String[annotationTypes.length + 3];
        annotationNames[0] = "ExpID";
        annotationNames[1] = "Exp upload date";
        annotationNames[2] = "Exp author";
        for (int i = 3; i < annotationTypes.length + 3; i++) {
            annotationNames[i] = annotationTypes[i - 3].getName();
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
                        }
                    }
                    dropdown = false;
                    setAs(firstRow, lastRow);
                    parent.updateSearchArea();
                    repaint();
                    revalidate();
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
