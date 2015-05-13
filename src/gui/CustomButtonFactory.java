package gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Class (static) capable of creating a custom style button.
 * <\p>
 * Can return a JButton with custom image and hover properties. Set up
 * automatically using 'set...' of the JButton.
 * @author (of comment) c12oor
 *
 */
public class CustomButtonFactory {

    /**
     * Get a custom button with given properties. (OO comment)
     * @param icon ImageIcon to place on the button
     * @param hoverIcon ImageIcon to use when hovering over the button
     * @param width Dimension width of buttonsize
     * @param height Dimension height of buttonsize
     * @param tooltip String of button
     * @return JButton with these properties set.
     */
    public static JButton makeCustomButton(ImageIcon icon, ImageIcon hoverIcon, int width, int height, String tooltip) {
        JButton button = new JButton(icon);
        button.setRolloverIcon(hoverIcon);
        button.setBorderPainted(true);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusable(true);
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);
        return button;
    }

}
