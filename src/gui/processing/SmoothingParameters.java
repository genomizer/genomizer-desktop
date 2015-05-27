package gui.processing;

public class SmoothingParameters implements ProcessParameters {

    public String infile;
    public String outfile;
    public int windowSize;
    public String meanOrMedian;
    public int minSmooth;


    public SmoothingParameters(String infile, String outfile, int windowSize, String meanOrMedian, int minSmooth) {
        this.infile = infile;
        this.outfile = outfile;
        this.windowSize = windowSize;
        this.meanOrMedian = meanOrMedian;
        this.minSmooth = minSmooth;
    }
}
