package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconFactory {
    
    private static BufferedImage getBufferedImage(String path) {
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File(path));
        } catch (IOException e1) {
            return null;
        }
        return icon;
    }
    
    public static ImageIcon getSearchIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/search.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getSearchHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/searchHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getClearIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/clear.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getClearHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/clearHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getBackIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/back.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getBackHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/backHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getPlusIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/plus2.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getPlusHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/plus2Hover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getMinusIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/minus2.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getMinusHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/minus2Hover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getDownloadIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/download.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getDownloadHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/downloadHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAnalyzeIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/analyze.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAnalyzeHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/analyzeHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getProcessIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/process.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getProcessHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/processHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getStopIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/close.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getStopHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/closeHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getRefreshIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/refresh.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getRefreshHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/refreshHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getUploadIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/upload.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getUploadHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/uploadHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getWorkspaceIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/workspace2.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAdministratorIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/administrator.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getLogoutIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/logout.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getLogoutHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/logoutHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getLoginIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/login.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getLoginHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/loginHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getNewExperimentIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/newExperiment.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getNewExperimentHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/newExperimentHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getExistingExperimentIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/existingExperiment.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getExistingExperimentHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/existingExperimentHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getBrowseIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/browse.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getBrowseHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/browseHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAddToWorkspaceIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/addToWorkspace.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
    
    public static ImageIcon getAddToWorkspaceHoverIcon(int x, int y) {
        BufferedImage icon = getBufferedImage("src/icons/addToWorkspaceHover.png");
        return new ImageIcon(icon.getScaledInstance(x, y, Image.SCALE_SMOOTH));
    }
}

