package gui.processing;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import controller.ProcessTabController;

public class ProcessTab  extends JPanel {
    private ProcessTabController processTabController;

    public ProcessTab() {
        setPreferredSize(new Dimension(1225, 725));
        setMinimumSize(new Dimension(20000, 20000));
        this.setLayout(new BorderLayout());
    }

    public void setController(ProcessTabController processTabController) {
        this.processTabController = processTabController;
    }
}
