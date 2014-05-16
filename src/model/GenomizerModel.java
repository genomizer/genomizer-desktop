package model;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;

import communication.DownloadHandler;
import communication.UploadHandler;

public interface GenomizerModel {

    public boolean loginUser(String username, String password);

    public boolean logoutUser();

    public boolean uploadFile(String expName, File f, String type,
            String username, boolean isPrivate, String release);

    public ArrayList<ExperimentData> search(String pubmedString);

    public boolean rawToProfile(String fileName, String fileID, String expid,
            String processtype, String[] parameters, String metadata,
            String genomeRelease, String author);

    public boolean downloadFile(String url, String fileID, String path,
            String fileName);

    public void setIp(String ip);

    public boolean addNewAnnotation(String name, String[] categories,
            boolean forced);

    public AnnotationDataType[] getAnnotations();

    public GenomeReleaseData[] getGenomeReleases();

    boolean deleteAnnotation(String annotationName);

    public boolean addNewExperiment(String expName, String username,
            AnnotationDataValue[] annotations);

    boolean editAnnotation(String name, String[] categories, boolean forced,
            AnnotationDataType oldAnnotation);

    boolean renameAnnotationField(String oldname, String newname);

    public CopyOnWriteArrayList<DownloadHandler> getOngoingDownloads();

    public ExperimentData retrieveExperiment(String expID);

    public boolean renameAnnotationValue(String name, String oldValue, String newValue);

    public CopyOnWriteArrayList<UploadHandler> getOngoingUploads();


    public boolean removeAnnotationValue(String annotationName, String valueName);

    public boolean removeAnnotationField(String annotationName);

    public ProcessFeedbackData[] getProcessFeedback();
}
