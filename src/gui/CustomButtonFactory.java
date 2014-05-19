package gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import util.IconFactory;

public class CustomButtonFactory {
    
    public static JButton makeCustomButton(ImageIcon icon, ImageIcon hoverIcon, int x, int y, String tooltip) {
        JButton button = new JButton(icon);
        button.setRolloverIcon(hoverIcon);
        button.setBorderPainted(true);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(x, y));
        button.setFocusable(true);
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);
        return button;
    }
    
}
