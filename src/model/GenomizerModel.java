package model;

import java.util.ArrayList;

import util.ExperimentData;

public interface GenomizerModel {

	public boolean loginUser(String username, String password);

	public boolean logoutUser();

	public boolean uploadFile();

	public ExperimentData[] search(String pubmedString);

	public boolean rawToProfile(ArrayList<String> markedFiles);

	public boolean downloadFile(String fileID, String path);

	public void setIp(String ip);

	public boolean addNewAnnotation(String name, String[] categories,
			boolean forced);

	boolean deleteAnnotation(String[] strings);
}
