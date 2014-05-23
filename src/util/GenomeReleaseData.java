package util;

public class GenomeReleaseData {

    private String genomeVersion;
    private String specie;
    private String path;
    private String[] fileNames;

    public GenomeReleaseData(String genomeVersion, String specie, String path,
            String fileName) {

        this.genomeVersion = genomeVersion;
        this.specie = specie;
        this.path = path;
    }

    public String getVersion() {
        return genomeVersion;
    }

    public String getSpecies() {
        return specie;
    }

    public String[] getFilenames() {
        return fileNames;
    }

    public String getPath() {
        return path;
    }

}
