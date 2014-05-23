package util;

public class GenomeReleaseData {

    private String genomeVersion;
    private String species;
    private String folderPath;
    private String[] files;

    public GenomeReleaseData(String genomeVersion, String specie, String path,
            String fileName) {

        this.genomeVersion = genomeVersion;
        this.species = specie;
        this.folderPath = path;
    }

    public String getVersion() {
        return genomeVersion;
    }

    public String getSpecies() {
        return species;
    }

    public String[] getFilenames() {
        return files;
    }

    public String getPath() {
        return folderPath;
    }

}
