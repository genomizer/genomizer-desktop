package util;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class Process {
    
    public boolean isCorrectToProcess(JTextField smoothWindowSize,
            JTextField stepPosition, JTextField stepSize) {
        
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
    
    public String[] getOtherParameters(JCheckBox outputGFF,JCheckBox outputSGR) {
        String[] s = new String[2];
        s[0] = getFormat(outputGFF);
        s[1] = getFormat(outputSGR);

        return s;
    }

    private String getFormat(JCheckBox format) {
        if (format.isSelected()) {
            return "y";
        } else {
            return "";
        }
    }
}
