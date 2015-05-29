package gui.processing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * Abstract class representing a file row of inputs for a CommandComponent
 *
 * @author oi12mlw, oi12pjn
 * @see CommandComponent
 */
@SuppressWarnings("serial")
public abstract class CommandFileRow extends JComponent {

    public static final String WIDE =   "w 80:120:160";
    public static final String NARROW = "w 60:80:100";
    public static final String MEDIUM = "w 80:100:120";

    protected abstract void addInputFields();
    protected abstract void addLabels();

    protected class InfileActionListener implements ActionListener {

        private JTextField outFileTextField;
        private String fileEnding;

        public InfileActionListener(JTextField outFileTextField, String fileEnding) {
            this.outFileTextField = outFileTextField;
            this.fileEnding = fileEnding;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            @SuppressWarnings("rawtypes")
            JComboBox inFileComboBox = (JComboBox) e.getSource();
            String inFile = inFileComboBox.getSelectedItem().toString();
            String inFileWithoutEnding = inFile.split("\\.")[0];
            String outFile = inFileWithoutEnding + fileEnding;
            outFileTextField.setText(outFile);

        }

    }


}
