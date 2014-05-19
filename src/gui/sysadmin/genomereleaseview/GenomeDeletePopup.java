package gui.sysadmin.genomereleaseview;

import gui.sysadmin.strings.SysStrings;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dv12ilr on 2014-05-16.
 */
public class GenomeDeletePopup extends JFrame {

    private GenomeReleaseViewCreator genView;
    private String specie;
    private String version;

    public GenomeDeletePopup(GenomeReleaseViewCreator genView){
        this.genView = genView;


        if (!genView.isGeneSelected()) {
            JOptionPane.showMessageDialog(null,
                    "Please select a genome release that should be removed");
            this.setEnabled(false);
        } else {

            specie = genView.getSelectedSpecie();
            version = genView.getSelectedVersion();

            String message = "WARNING! You are about to permanently delete the genome release for \ngenome version: " + version + "\nspecie: "+ specie + "\nare you sure?";
            JOptionPane userMessage = new JOptionPane();


            if(userMessage.showConfirmDialog(null, message,
                    SysStrings.GENOME_POPUP_NAME, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    javax.swing.UIManager.getIcon("OptionPane.warningIcon"))
                    == JOptionPane.YES_OPTION){

                /** TODO: remove the shit here!!!!*/
            }

        }
    }


}
