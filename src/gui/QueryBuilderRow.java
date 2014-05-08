package gui;

import util.AnnotationDataType;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class QueryBuilderRow extends JPanel {
    private JComboBox annotationField;
    private JComboBox annotationAlternatives;
    private JTextField textField;
    private JButton plusButton;
    private JButton minusButton;
    private JComboBox logicField;
    private QuerySearchTab parent;
    private AnnotationDataType[] annotationTypes;
    private static final String[] logicOperators = {"AND", "NOT", "OR"};
    private boolean dropdown = false;
    private boolean firstRow = false;
    private boolean lastRow = false;

    public QueryBuilderRow(QuerySearchTab parent, AnnotationDataType[] annotationTypes) {
        this.parent = parent;
        this.annotationTypes = annotationTypes;
        setLayout(new FlowLayout());
        setPlusButton();
        setMinusButton();
        setLogicBox();
        setFieldBox(annotationTypes);
        setTextField();
        setAnnotationAlternatives(new String[0]);
    }

    public void setAs(Boolean firstRow, Boolean lastRow) {
        removeAll();
    	JPanel logicPanel = new JPanel();
    	logicPanel.setPreferredSize(new Dimension(50,25));
    	JPanel annotationPanel = new JPanel();
    	annotationPanel.setPreferredSize(new Dimension(130,25));
    	JPanel inputPanel = new JPanel();
    	inputPanel.setPreferredSize(new Dimension(420,25));
    	JPanel buttonPanel = new JPanel();
    	buttonPanel.setPreferredSize(new Dimension(60,25));
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        if (firstRow && lastRow) {
            annotationPanel.add(annotationField);
            if(dropdown) {
                inputPanel.add(annotationAlternatives);
            } else {
            	inputPanel.add(textField);
            }
            buttonPanel.add(plusButton);
        } else if (firstRow && !lastRow) {
        	 annotationPanel.add(annotationField);
            if(dropdown) {
            	inputPanel.add(annotationAlternatives);
            } else {
            	inputPanel.add(textField);
            }
            buttonPanel.add(minusButton);
        } else if (!firstRow && !lastRow) {
        	logicPanel.add(logicField);
            annotationPanel.add(annotationField);
            if(dropdown) {
            	inputPanel.add(annotationAlternatives);
            } else {
            	inputPanel.add(textField);
            }
            buttonPanel.add(minusButton);
        } else {
            logicPanel.add(logicField);
            annotationPanel.add(annotationField);
            if(dropdown) {
            	inputPanel.add(annotationAlternatives);
            } else {
            	inputPanel.add(textField);
            }
            buttonPanel.add(minusButton);
            buttonPanel.add(plusButton);
        }
        add(logicPanel);
        add(annotationPanel);
        add(inputPanel);
        add(buttonPanel);
    }

    private void setPlusButton() {
        plusButton = new JButton();

        URL imageUrl = getClass().getResource("/icons/plus.png");
        ImageIcon plusIcon = new ImageIcon(imageUrl);
        plusIcon = new ImageIcon(plusIcon.getImage().getScaledInstance(15, 15,
                Image.SCALE_SMOOTH));
        plusButton.setBorderPainted(true);
        plusButton.setContentAreaFilled(false);
        plusButton.setPreferredSize(new Dimension(20, 20));
        plusButton.setFocusable(true);
        plusButton.setFocusPainted(false);
        plusButton.setIcon(plusIcon);
        plusButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                parent.addRow();
            }
        });
    }

    private void setMinusButton() {
        minusButton = new JButton();
        URL imageUrl = getClass().getResource("/icons/minus.png");
        ImageIcon minusIcon = new ImageIcon(imageUrl);
        minusIcon = new ImageIcon(minusIcon.getImage().getScaledInstance(15,
                15, Image.SCALE_SMOOTH));
        minusButton.setBorderPainted(true);
        minusButton.setContentAreaFilled(false);
        minusButton.setPreferredSize(new Dimension(20, 20));
        minusButton.setFocusable(true);
        minusButton.setFocusPainted(false);
        minusButton.setIcon(minusIcon);
        final QueryBuilderRow row = this;
        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.removeRow(row);
                parent.updateSearchArea();
            }
        });

    }


    private void setTextField() {
        textField = new JTextField(50);
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

    private void setAnnotationAlternatives(String[] values) {
        annotationAlternatives = new JComboBox(values);
        annotationAlternatives.setPrototypeDisplayValue("AAAAAAAAAAAAAAAAAAAAAAA"
        			+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        annotationAlternatives.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.updateSearchArea();
            }
        });
    }

    public void setFieldBox(AnnotationDataType[] annotations) {
        this.annotationTypes = annotations;
        String[] annotationNames = new String[annotationTypes.length];
        for(int i=0; i<annotationTypes.length; i++) {
            annotationNames[i] = annotationTypes[i].getName();
        }
        annotationField = new JComboBox(annotationNames);
        annotationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               String annotation = (String)annotationField.getSelectedItem();
                for(int i=0; i<annotationTypes.length; i++) {
                    if(annotation.equals(annotationTypes[i].getName())) {
                        String[] values = annotationTypes[i].getValues();
                        if(values != null) {
                            if (!values[0].equals("freetext")) {
                                dropdown = true;
                                setAnnotationAlternatives(values);
                            } else {
                                dropdown = false;
                            }
                            setAs(firstRow, lastRow);
                            parent.updateSearchArea();
                            repaint();
                            revalidate();
                        }
                    }
                }
            }
        });
        annotationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.updateSearchArea();
            }
        });
    }

    private void setLogicBox() {
        logicField = new JComboBox(logicOperators);
        logicField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.updateSearchArea();
            }
        });
    }

    public String getText() {
        if(!dropdown) {
            return textField.getText();
        } else {
            return (String) annotationAlternatives.getSelectedItem();
        }
    }

    public String getLogic() {
        return (String) logicField.getSelectedItem();
    }

    public String getAnnotation() {
        return (String) annotationField.getSelectedItem();
    }
}