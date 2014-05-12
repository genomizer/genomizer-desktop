package gui.sysadmin.processview;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dv12ilr on 2014-05-12.
 */
public class ProcessViewCreator {

    public ProcessViewCreator(){

    }

    public JPanel buildProcessView() {
        JPanel mainPanel = new JPanel(new BorderLayout(0,0));
        mainPanel.setBackground(Color.cyan);

        JPanel processTab = new JPanel();




        processTab.setBackground(Color.BLACK);
        mainPanel.add(processTab, BorderLayout.EAST);

        return mainPanel;

    }

}
