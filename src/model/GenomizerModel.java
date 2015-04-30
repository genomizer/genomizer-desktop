package model;

import gui.GenomizerView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;

import communication.DownloadHandler;
import communication.HTTPURLUpload;

public interface GenomizerModel {

    public String loginUser(String username, String password);

    public boolean logoutUser();

    /**
     * Try to upload a file, sending a normal Connection first with
     * the passed data, and then a HTTPURLUpload with the response url.
     * @param expName
     * @param f
     * @param type
     * @param username
     * @param isPrivate
     * @param release
     * @return true if started without error.
     */
    public boolean uploadFile(String expName, File f, String type, boolean isPrivate, String release);

    public ArrayList<ExperimentData> search(String pubmedString);

    public boolean rawToProfile(String expid, String[] parameters,
            String metadata, String genomeRelease, String author);

    public boolean downloadFile(String url, String fileID, String path,
            String fileName);

    public void setIP(String ip);

    void setGenomizerView(GenomizerView view);

    public boolean addNewAnnotation(String name, String[] categories,
            boolean forced);

    public AnnotationDataType[] getAnnotations();

    public GenomeReleaseData[] getGenomeReleases();

    boolean deleteAnnotation(String annotationName) throws DeleteAnnotationException;

    public boolean addNewExperiment(String expName,
            AnnotationDataValue[] annotations);

    boolean editAnnotation(String name, String[] categories, boolean forced,
            AnnotationDataType oldAnnotation);

    boolean renameAnnotationField(String oldname, String newname);

    public CopyOnWriteArrayList<DownloadHandler> getOngoingDownloads();

    public ExperimentData retrieveExperiment(String expID);

    public boolean renameAnnotationValue(String name, String oldValue,
            String newValue);

    public CopyOnWriteArrayList<HTTPURLUpload> getOngoingUploads();

    public boolean removeAnnotationValue(String annotationName, String valueName);

    public ProcessFeedbackData[] getProcessFeedback();

    public boolean deleteGenomeRelease(String gr, String specie);

    public GenomeReleaseData[] getSpeciesGenomeReleases(String species);

    public boolean deleteFileFromExperiment(String id);

    public boolean deleteExperimentFromDatabase(String name);

    public boolean addGenomeReleaseFile(String[] filePaths, String specie,
            String version);

    public boolean addNewAnnotationValue(String annotationName, String valueName);

    public void resetModel();

    public boolean addGenomeRelease();

    public boolean changeExperiment(String expName,
            AnnotationDataValue[] annotations);
}
