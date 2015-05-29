package gui.processing;

public class StepParameters implements ProcessParameters {

    public String infile;
    public String outfile;
    public int stepSize;

    public StepParameters(String infile, String outfile, int stepSize) {

        this.infile = infile;
        this.outfile = outfile;
        this.stepSize = stepSize;

    }
}
