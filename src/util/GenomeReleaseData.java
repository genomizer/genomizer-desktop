package util;

public class GenomeReleaseData {

    private String version;
    private String species;
    private String filename;

    public GenomeReleaseData(String version, String species, String filename) {

        this.version = version;
        this.species = species;
        this.filename = filename;

    }

    public String getVersion() {
        return version;
    }

    public String getSpecies() {
        return species;
    }

    public String getFilename() {
        return filename;
    }


}
