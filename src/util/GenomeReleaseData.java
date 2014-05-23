package util;

public class GenomeReleaseData {

    private String genomeVersion;
    private String specie;
    private String path;
    private String[] fileName;

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

    public String[] getFilename() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

}
