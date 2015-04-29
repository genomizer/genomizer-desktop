package gui;

import java.io.File;
import java.util.ArrayList;

/**
 * An interface implemented by the existing experiment panel and the new
 * experiment panel. This interface is used to connect the two panels.
 * 
 * @author
 * 
 */
public interface ExperimentPanel {
    
    /**
     * Method returning the current available genome releases for the species
     * selected.
     * 
     * @return an Array of Strings representing the releases.
     */
    public ArrayList<String> getGenomeReleases();
    
    /**
     * Deletes an uploadFileRow and calls a private repaint method. If it fails
     * to find the file, an error message is shown to the user.
     * 
     * @param f
     *            This is used to identify which uploadFileRow to be deleted.
     */
    public void deleteFileRow(File f);
}
