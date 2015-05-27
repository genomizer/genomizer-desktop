package gui.processing;

public class RatioParameters implements ProcessParameters {

    public String preChipFile;
    public String postChipFile;
    public String outfile;
    public String mean;
    public int readsCutoff;
    public String chromosome;

    public RatioParameters(String preChipFile, String postChipFile,
            String outfile, String mean, int readsCutoff, String chromosome) {
        this.preChipFile = preChipFile;
        this.postChipFile = postChipFile;
        this.outfile = outfile;
        this.mean = mean;
        this.readsCutoff = readsCutoff;
        this.chromosome = chromosome;
    }

    @Override
    public String toString() {
        return "preChipFile: " + preChipFile + "\n" + "postChipFile: "
                + postChipFile + "\n" + "outfile: " + outfile + "\n" + "mean: "
                + mean + "\n" + "readsCutoff: " + readsCutoff + "\n"
                + "chromosome: " + chromosome;
    }

}
