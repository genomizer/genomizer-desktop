package gui.sysadmin.genomereleaseview;

import gui.sysadmin.SysadminTab;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/***
 * This class is the listener for the genome release table.
 * 
 * 
 */

public class GenomeTableListener implements MouseListener, KeyListener  {

    private SysadminTab sysTab;

    public GenomeTableListener(SysadminTab sysTab){
        this.sysTab = sysTab;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        displayInfoData();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {

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

    /***
     * Only updates the screen on up and down arrow keys.
     */
    public void keyReleased(KeyEvent e) {

        if(KeyEvent.VK_UP == e.getKeyCode() || KeyEvent.VK_DOWN == e.getKeyCode())
            displayInfoData();

    }

    /**
     * Display the extra information tab in the GUI
     */
    private void displayInfoData(){

        sysTab.getGenomeReleaseView().addExtraInfoPanel();

    }
}

