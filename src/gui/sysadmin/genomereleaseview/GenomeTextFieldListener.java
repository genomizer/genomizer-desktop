package gui.sysadmin.genomereleaseview;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * This class listens to all keys on the keyboard. If something is written in
 * the text field, the 'Clear' button is enabled, if the field is empty, the
 * clear button is disabled.
 * 
 * This class also activates the 'Upload' button if a file is chosen.
 * 
 * Created by dv12ilr on 2014-05-16.
 * 
 * 
 */
public class GenomeTextFieldListener implements KeyListener{

    GenomeReleaseViewCreator gr;

    public GenomeTextFieldListener(GenomeReleaseViewCreator gr){
        this.gr = gr;
    }

    @Override public void keyTyped(KeyEvent keyEvent) {

    }

    @Override public void keyPressed(KeyEvent keyEvent) {
        if(gr.isTextFieldsEmpty()){
            gr.enableClearButton(false);
        } else {
            gr.enableClearButton(true);
        }

        if(gr.allTextFieldsContainInfo()){
            gr.enableAddButton(true);
        } else {
            gr.enableAddButton(false);
        }

    }

    @Override public void keyReleased(KeyEvent keyEvent) {
    }
}
