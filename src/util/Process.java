package util;

import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Process {
    
    public boolean isCorrectToProcess(JTextField smoothWindowSize,
            JTextField stepPosition, JTextField stepSize) {
        System.out.println("checking if parameters are correct");
        System.out.println("smmoth: " + smoothWindowSize.getText().trim());
        System.out.println("stepposition: " + stepPosition.getText().trim());
        System.out.println("stepsize: " + stepSize.getText().trim());
        if (aboveZero(smoothWindowSize.getText().trim())
                && aboveZero(stepPosition.getText().trim())
                && aboveZero(stepSize.getText().trim())) {
            return true;
        } else {
            return false;
        }
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
    
    public String[] getOtherParameters(JRadioButton outputGFF,
            JRadioButton outputSGR, JRadioButton outputSAM) {
        String[] s = new String[2];
        s[0] = "y";
        if (outputSAM.isSelected()) {
            s[0] = "";
            s[1] = "";
        } else if (outputSGR.isSelected()) {
            s[1] = "y";
        } else {
            s[1] = "n";
        }
        return s;
    }
}
