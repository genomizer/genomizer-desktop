package gui.sysadmin.genomereleaseview;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dv12ilr on 2014-05-16.
 */
public class GenomeDeletePopup extends JPanel {

    public GenomeDeletePopup(){
        boolean test = false;
        if (test) {
            JOptionPane.showMessageDialog(null, "Please select an annotation to edit");
            this.setEnabled(false);
        } else {

            this.setLayout(new BorderLayout());
            JButton button = new JButton("Delete");
            this.add(button, BorderLayout.CENTER);
            this.setVisible(true);
        }
    }


}
