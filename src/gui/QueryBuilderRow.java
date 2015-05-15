package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.QueryRowController;

import util.AnnotationDataType;
import util.IconFactory;

/**
 * Class the represents a row in the query builder
 *
 * @author bDtKarlsson
 */
public class QueryBuilderRow extends JPanel {

    private static final long serialVersionUID = -7684513985741278158L;
    private JComboBox annotationBox;
    private JComboBox annotationAlternatives;
    private JTextField textField;
    private JButton plusButton;
    private JButton minusButton;
    private JComboBox logicBox;
    private QuerySearchTab parent;
    private CopyOnWriteArrayList<AnnotationDataType> annotationTypes;
    private static final String[] logicOperators = { "AND", "NOT", "OR" };
    private boolean dropdown = false;
    private boolean firstRow = false;
    private boolean lastRow = false;
    private QueryRowController queryRowController;

    public QueryBuilderRow(QuerySearchTab parent,
            AnnotationDataType[] annotationTypes,
            QueryRowController queryRowController) {
        /* The Parent query search tab */
        this.parent = parent;
        /* The annotation information */
        this.annotationTypes = new CopyOnWriteArrayList<>();
        this.queryRowController = queryRowController;
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
    public synchronized void setAs(Boolean firstRow, Boolean lastRow) {
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
        if (annotationBox == null) {
            annotationBox = new JComboBox();
        }
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
                IconFactory.getPlusIcon(17, 17), 17, 25, null);
        plusButton.addActionListener(queryRowController
                .createPlusButtonListener());

        plusButton.setFocusable(false);
    }

    /**
     * Method for constructing a minus button
     */
    private void setMinusButton() {
        minusButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getMinusIcon(15, 15),
                IconFactory.getMinusIcon(17, 17), 17, 25, null);
        minusButton.addActionListener(queryRowController
                .createMinusButtonListener(this));
        minusButton.setFocusable(false);
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
        textField.getDocument().addDocumentListener(
                queryRowController.createDocumentListener());
        setTextFieldOnEnterListener(textField);
    }

    private void focusNextQuery() {
        parent.getNextQuery(this).getTextField().requestFocus();
    }

    public JTextField getTextField() {
        return this.textField;
    }

    private void onPressedEnter() {
        parent.getSearchButton().doClick();
    }

    private void setTextFieldOnEnterListener(JTextField textField) {

        final QueryBuilderRow queryRow = this;

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == event.VK_ENTER) {
                    if (parent.isLastQueryIndex(queryRow)) {
                        onPressedEnter();
                    } else {
                        focusNextQuery();
                    }
                }
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

        annotationAlternatives.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Update search area in parent when selected item is changed */
                parent.updateSearchArea();
            }
        });
    }

    private CopyOnWriteArrayList<AnnotationDataType> getManuallyAddedAnnotations() {
        CopyOnWriteArrayList<AnnotationDataType> annotations = new CopyOnWriteArrayList<>();
        annotations.add(new AnnotationDataType("ExpID", null, true));
        annotations.add(new AnnotationDataType("FileName", null, true));
        annotations.add(new AnnotationDataType("FileType", new String[] {
                "Raw", "Profile", "Region" }, true));
        annotations.add(new AnnotationDataType("Date", null, true));
        annotations.add(new AnnotationDataType("Author", null, true));
        annotations.add(new AnnotationDataType("Uploader", null, true));
        return annotations;
    }

    /**
     * Method for creating the annotations combobox
     *
     * @param annotations
     *            - the annotations
     */
    public void setAnnotationBox(AnnotationDataType[] annotations) {
        annotationTypes = new CopyOnWriteArrayList<>();
        annotationTypes.addAll(getManuallyAddedAnnotations());
        annotationTypes.addAll(new ArrayList<>(Arrays.asList(annotations)));
        /* Get the annotation names */
        final ArrayList<String> annotationNames = new ArrayList<>();
        for (AnnotationDataType dataType : annotationTypes) {
            annotationNames.add(dataType.getName());
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (annotationNames.size() > 0) {
                    annotationBox = new JComboBox<String>(annotationNames
                            .toArray(new String[annotationNames.size()])); // TODO Denna rad kastar exception ibland vid uppstart.
                } else {
                    annotationBox = new JComboBox<String>();
                }

                annotationBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        /*
                         * When the selected item in the box is changed a check
                         * must be made to know wether a free text field or a
                         * annotation alternatives box should be displayed
                         */
                        String annotation = (String) annotationBox
                                .getSelectedItem();
                        dropdown = false;
                        for (AnnotationDataType dataType : annotationTypes) {
                            if (annotation.equals(dataType.getName())) {
                                String[] values = dataType.getValues();
                                if (values != null) {
                                    if (!values[0].equals("freetext") || values.length>1) {
                                        /* Dropdown annotation alternatives box */
                                        dropdown = true;
                                        setAnnotationAlternatives(values);
                                    } else {
                                        /* Free text field */
                                        dropdown = false;
                                    }
                                }
                            }
                            /* Update row and parent search area */
                            setAs(firstRow, lastRow);
                            parent.updateSearchArea();
                            repaint();
                            revalidate();
                        }
                    }
                });
                /*
                 * Change selected item so the actionlistener will be called
                 * upon starting the program
                 */
                if (annotationBox.getSelectedIndex() != -1) {
                    annotationBox.setSelectedIndex(0);
                }
            }
        });
    }

    /**
     * Method for creating the logic box
     */
    private void setLogicBox() {
        logicBox = new JComboBox<Object>(logicOperators);
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

    public void setEnabled(boolean enabled) {
        annotationBox.setEnabled(enabled);
        annotationAlternatives.setEnabled(enabled);
        logicBox.setEnabled(enabled);
        textField.setEnabled(enabled);
        plusButton.setEnabled(enabled);
        minusButton.setEnabled(enabled);
    }
}
