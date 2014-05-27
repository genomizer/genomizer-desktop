package util;

import java.awt.Image;

import javax.swing.ImageIcon;

public class IconFactory {
    
    private static ImageIcon getBufferedImage(String path) {
        return new ImageIcon((IconFactory.class).getResource(path));
    }
    
    public static ImageIcon getSearchIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/search.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getClearIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/delete.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getLogoIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/logo.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getPlusIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/plus2.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getMinusIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/minus2.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getInfoIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/information.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getBackIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/left_arrow.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
}
