
package gui.processing;

public class RatioParameters implements ProcessParameters{

    public String infile;
    public String outfile;
    public String params;
    public String genomeVersion;
    public boolean keepSam;


    public RatioParameters(String infile, String outfile, String params,
            String genomeVersion, boolean keepSam) {
        this.infile = infile;
        this.outfile = outfile;
        this.params = params;
        this.genomeVersion = genomeVersion;
        this.keepSam = keepSam;
    }

    @Override
    public String toString() {
        return "infile: " + infile + "\n" +
               "outfile: " + outfile + "\n" +
               "params: " + params + "\n" +
               "gr: " + genomeVersion + "\n" +
               "keepSam: " + keepSam;
    }

}