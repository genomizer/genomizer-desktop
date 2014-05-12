package model;

import java.io.File;
import java.util.ArrayList;

import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.DeleteAnnoationData;
import util.ExperimentData;

public interface GenomizerModel {

	public boolean loginUser(String username, String password);

	public boolean logoutUser();

	public boolean uploadFile(String ID, File f, String type,
		String username, boolean isPrivate, String release);

	public ArrayList<ExperimentData> search(String pubmedString);

	public boolean rawToProfile(String fileName, String fileID,String expid, String processtype, String[] parameters, String metadata, String genomeRelease, String author);

	public boolean downloadFile(String url, String fileID, String path);

	public void setIp(String ip);

	public boolean addNewAnnotation(String name, String[] categories,
			boolean forced);

	public AnnotationDataType[] getAnnotations();

	boolean deleteAnnotation(DeleteAnnoationData annotation);

	public boolean addNewExperiment(String expName, String username,
		AnnotationDataValue[] annotations);

}
