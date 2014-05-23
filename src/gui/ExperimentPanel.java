package gui;

import java.io.File;
import java.util.ArrayList;

public interface ExperimentPanel {
    public ArrayList<String> getGenomeReleases();

    public void deleteFileRow(File f);
}
