package gui.sysadmin.annotationview;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Isak Dunér Lundberg
 * Date: 2014-04-07
 */
public class SearchTextField extends JTextField implements FocusListener {

    private static Color searchHintText  = new Color(100, 100, 100);
    private static Color searchTextColor = new Color(0, 0, 0);

    private String hintText;

    public SearchTextField() {
        this.hintText = "";
    }

    public SearchTextField(String text) {
        this.setForeground(searchHintText);
        this.setText(text);
        hintText = text;
        this.addFocusListener(this);
    }

    /**
     * @return the hint text
     */
    public String getHintText() {
        return this.hintText;
    }

    /**
     * @param hintText the hint text
     */
    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    /**
     * Removes the hint text when box is selected
     *
     * @param e event
     */
    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().equals(hintText)) {
            this.setForeground(searchTextColor);
            this.setText("");
        }

    }

    /**
     * Adds hint text when box is deselected, but only if the box was empty
     *
     * @param e event
     */
    @Override
    public void focusLost(FocusEvent e) {
        if (!this.getText().equals(hintText) && this.getText().length() == 0) {
            this.setForeground(searchHintText);
            this.setText(hintText);

        }
    }
}
