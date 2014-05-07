package gui;

import util.AnnotationDataTypes;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
    private AnnotationDataTypes[] annotationTypes;
    private static final String[] logicOperators = {"AND", "NOT", "OR"};
    private boolean dropdown = false;
    private boolean firstRow = false;
    private boolean lastRow = false;

    public QueryBuilderRow(QuerySearchTab parent, AnnotationDataTypes[] annotationTypes) {
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
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        removeAll();
        if (firstRow && lastRow) {
            add(Box.createHorizontalStrut(73));
            add(annotationField);
            if(dropdown) {
                add(annotationAlternatives);
            } else {
                add(textField);
            }
            add(plusButton);
            add(Box.createHorizontalStrut(20));
        } else if (firstRow && !lastRow) {
            add(Box.createHorizontalStrut(73));
            add(annotationField);
            if(dropdown) {
                add(annotationAlternatives);
            } else {
                add(textField);
            }
            add(minusButton);
            add(Box.createHorizontalStrut(20));
        } else if (!firstRow && !lastRow) {
            add(logicField);
            add(annotationField);
            if(dropdown) {
                add(annotationAlternatives);
            } else {
                add(textField);
            }
            add(minusButton);
            add(Box.createHorizontalStrut(20));
        } else {
            add(logicField);
            add(annotationField);
            if(dropdown) {
                add(annotationAlternatives);
            } else {
                add(textField);
            }
            add(minusButton);
            add(plusButton);
        }
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
        annotationAlternatives.setPrototypeDisplayValue("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        annotationAlternatives.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.updateSearchArea();
            }
        });
    }

    public void setFieldBox(AnnotationDataTypes[] annotations) {
        this.annotationTypes = annotations;
        String[] annotationNames = new String[annotationTypes.length];
        for(int i=0; i<annotationTypes.length; i++) {
            annotationNames[i] = annotationTypes[i].getName();
        }
        annotationField = new JComboBox(annotationNames);
        annotationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropdown = false;
               String annotation = (String)annotationField.getSelectedItem();
                for(int i=0; i<annotationTypes.length; i++) {
                    if(annotation.equals(annotationTypes[i].getName())) {
                        String[] values = annotationTypes[i].getValue();
                        if(!values[0].equals("freetext")) {
                            dropdown = true;
                            setAnnotationAlternatives(values);
                        }
                        setAs(firstRow, lastRow);
                        parent.updateSearchArea();
                        repaint();
                        revalidate();
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