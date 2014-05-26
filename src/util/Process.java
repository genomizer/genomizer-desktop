package util;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class Process {
    
    public boolean isCorrectToProcess(JTextField smoothWindowSize,
            JTextField stepPosition, JTextField stepSize, boolean sgrFormat,
            JCheckBox smoothing, JCheckBox stepBox) {
        
        if (!sgrFormat) {
            return true;
        } else if (!smoothing.isSelected()) {
            return true;
        } else if (aboveZero(smoothWindowSize.getText().trim())
                && aboveZero(stepPosition.getText().trim())) {
            if (!stepBox.isSelected()
                    || (stepBox.isSelected() && aboveZero(stepSize.getText()
                            .trim()))) {
                return true;
            }
        }
        return false;
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
