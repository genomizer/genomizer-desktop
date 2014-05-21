package gui;

import javax.swing.JTextField;

public class Process {
    
    public boolean isCorrectToProcess(JTextField smoothWindowSize, JTextField stepPosition,JTextField stepSize) {

        if(aboveZero(smoothWindowSize.getText().trim()) && aboveZero(stepPosition.getText().trim()) && aboveZero(stepSize.getText().trim())){
           return true;
        }
        else {
            return false;
        }
    }
    
    public boolean aboveZero(String string) {

        try {
            int value = Integer.parseInt(string);
            if (value >= 0) {
                System.out.println(value);
                return true;
            } else {
                System.out.println(value);
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
