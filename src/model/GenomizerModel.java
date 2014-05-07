package model;

import java.util.ArrayList;

import util.AnnotationDataType;
import util.DeleteAnnoationData;
import util.ExperimentData;

public interface GenomizerModel {

	public boolean loginUser(String username, String password);

	public boolean logoutUser();

	public boolean uploadFile();

	public ExperimentData[] search(String pubmedString);

	public boolean rawToProfile(String fileName, String filePath,String metadata, String genomeRelease, String author, String expid, String[] parameters);

	public boolean downloadFile(String fileID, String path);

	public void setIp(String ip);

	public boolean addNewAnnotation(String name, String[] categories,
			boolean forced);

	public AnnotationDataType[] getAnnotations();

	boolean deleteAnnotation(DeleteAnnoationData annotation);
}
