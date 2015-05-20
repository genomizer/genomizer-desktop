package model;

import gui.GUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;
import util.RequestException;

import communication.DownloadHandler;
import communication.HTTPURLUpload;

public interface GenomizerModel {

    public String loginUser(String username, String password);

    public boolean logoutUser();

    /**
     * Try to upload a file, sending a normal Connection first with the passed
     * data, and then a HTTPURLUpload with the response url.
     *
     * @param expName
     * @param f
     * @param type
     * @param username
     * @param isPrivate
     * @param release
     * @return true if started without error.
     * @throws IOException
     * @throws RequestException
     * @throws IllegalArgumentException
     */
    public void uploadFile(String expName, File f, String type,
            boolean isPrivate, String release) throws IllegalArgumentException, RequestException, IOException;

    public ArrayList<ExperimentData> search(String pubmedString)
            throws RequestException;

    public void rawToProfile(String expid, String[] parameters,
            String metadata, String genomeRelease, String author)
            throws RequestException;

    public boolean downloadFile(String url, String fileID, String path,
            String fileName);

    public void setIP(String ip);

    public void addNewAnnotation(String name, String[] categories,
            boolean forced) throws IllegalArgumentException, RequestException;

    public AnnotationDataType[] getAnnotations();

    public GenomeReleaseData[] getGenomeReleases();

    void deleteAnnotation(String annotationName) throws RequestException;

    public void addNewExperiment(String expName,
            AnnotationDataValue[] annotations) throws RequestException;

    boolean editAnnotation(String name, String[] categories, boolean forced,
            AnnotationDataType oldAnnotation) throws RequestException;

    void renameAnnotationField(String oldname, String newname) throws RequestException;

    public CopyOnWriteArrayList<DownloadHandler> getOngoingDownloads();

    public ExperimentData retrieveExperiment(String expID);

    public void renameAnnotationValue(String name, String oldValue,
            String newValue) throws RequestException;

    public CopyOnWriteArrayList<HTTPURLUpload> getOngoingUploads();

    public void removeAnnotationValue(String annotationName, String valueName)
            throws RequestException;

    public ProcessFeedbackData[] getProcessFeedback();

    public void deleteGenomeRelease(String gr, String specie)
            throws RequestException;

    public GenomeReleaseData[] getSpeciesGenomeReleases(String species);

    public void deleteFileFromExperiment(String id) throws RequestException;

    public boolean deleteExperimentFromDatabase(String name);

    public void addGenomeReleaseFile(String[] filePaths, String specie,
            String version) throws RequestException, IllegalArgumentException,
            IOException;

    public void addNewAnnotationValue(String annotationName, String valueName)
            throws RequestException;

    public void resetModel();

    public boolean addGenomeRelease();

    public void changeExperiment(String expName,
            AnnotationDataValue[] annotations) throws RequestException;

    public void addTickingTask(Runnable task);

    public void clearTickingTasks();

}
