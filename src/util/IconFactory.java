package util;

import java.awt.Image;

import javax.swing.ImageIcon;

public class IconFactory {
    
    private static ImageIcon getBufferedImage(String path) {
        return new ImageIcon((IconFactory.class).getResource(path));
    }
    
    public static ImageIcon getSearchIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/zoom_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getSearchHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/zoom_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getClearIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getClearHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getBackIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getBackHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getPlusIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/plus2.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getPlusHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/plus2Hover.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getMinusIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/minus2.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getMinusHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/minus2Hover.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getDownloadIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getDownloadHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAnalyzeIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAnalyzeHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getProcessIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/cog_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getProcessHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/cog_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getStopIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getStopHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getRefreshIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/reload_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getRefreshHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/reload_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getUploadIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getUploadHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getWorkspaceIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAdministratorIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getLogoutIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getLogoutHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getLoginIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getLoginHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getNewExperimentIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getNewExperimentHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getExistingExperimentIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getExistingExperimentHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getBrowseIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getBrowseHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAddToWorkspaceIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAddToWorkspaceHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAddToListIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAddToListHoverIcon(int x, int y) {
        ImageIcon icon = getBufferedImage("/icons/trash_icon.png");
        return new ImageIcon(icon.getImage().getScaledInstance(x, y,
                Image.SCALE_SMOOTH));
    }
}
