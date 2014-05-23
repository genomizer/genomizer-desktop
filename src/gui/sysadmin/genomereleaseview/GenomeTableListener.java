package gui.sysadmin.genomereleaseview;

import gui.sysadmin.SysadminTab;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GenomeTableListener implements MouseListener, KeyListener  {

    private SysadminTab sysTab;

    public GenomeTableListener(SysadminTab sysTab){
        this.sysTab = sysTab;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        displayInfoData();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Do nothing
    }


    @Override
    public void keyTyped(KeyEvent e) {
        //Do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Do nothing
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Only update the screen on up and down arrow keys, (a simple optimization)
        if(KeyEvent.VK_UP == e.getKeyCode() || KeyEvent.VK_DOWN == e.getKeyCode())
            displayInfoData();

    }

    /**
     * Display the extra information tab in the GUI
     */
    private void displayInfoData(){
        /*
        if(mainController.getSelectedTableRow() > -1)
            this.mainController.updateFullInfoScreen(mainController.getSelectedTableRow());
            */
        sysTab.getGenomeReleaseView().addExtraInfoPanel();

    }
}

