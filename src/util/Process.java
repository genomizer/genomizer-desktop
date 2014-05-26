package util;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class Process {

    public boolean isCorrectToProcess(JTextField smoothWindowSize,
            JTextField stepPosition, JTextField stepSize, boolean sgrFormat,
            JCheckBox smoothing, JCheckBox stepBox) {
        System.out.println("sgrformat: " + sgrFormat);
        System.out.println("smmothsize: " + smoothWindowSize.getText());
        System.out.println("steppos: " + stepPosition.getText());
        System.out.println("stepSize: " + stepSize.getText());

        if (!sgrFormat) {
            return true;
        } else if (!smoothing.isSelected()) {
            return true;
        } else if (aboveZero(smoothWindowSize.getText().trim())
                && aboveZero(stepPosition.getText().trim())) {
            if (!stepBox.isSelected() || (stepBox.isSelected() && aboveZero(stepSize.getText().trim()))) {
                return true;
            }
        }
        return false;
    }

    public boolean isRatioCorrectToProcess(JTextField ratioWindowSize,
            JTextField inputReads, JTextField chromosome,
            JTextField ratioStepPosition) {
        System.out.println("checking if ratio is correct");
        if (aboveZero(ratioWindowSize.getText().trim())
                && aboveZero(inputReads.getText().trim())
                && aboveZero(chromosome.getText().trim())
                && aboveZero(ratioStepPosition.getText().trim())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean aboveZero(String string) {

        try {
            int value = Integer.parseInt(string);
            if (value >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean ZeroOr(String string) {

        try {
            int value = Integer.parseInt(string);
            if (value >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
