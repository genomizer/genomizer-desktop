package gui.sysadmin.genomereleaseview;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by dv12ilr on 2014-05-16.
 */
public class GenomeTextFieldListener implements KeyListener{

    GenomeReleaseViewCreator gr;

    public GenomeTextFieldListener(GenomeReleaseViewCreator gr){
        this.gr = gr;
    }

    @Override public void keyTyped(KeyEvent keyEvent) {

    }

    @Override public void keyPressed(KeyEvent keyEvent) {

    }

    @Override public void keyReleased(KeyEvent keyEvent) {
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
}
