package model;

import java.util.ArrayList;

import communication.DownloadHandler;

public class OngoingDownloads {
    
    private ArrayList<DownloadHandler> ongoingDownloads;
    
    public OngoingDownloads() {
        
    }
    
    public void addOngoingDownload(DownloadHandler handler) {
        ongoingDownloads.add(handler);
    }
    
    public DownloadHandler getHandler(String fileID) {
        for (DownloadHandler handler : ongoingDownloads) {
            if (handler.getFileID().equals(fileID)) {
                return handler;
            }
        }
        return null;
    }
    
    public ArrayList<DownloadHandler> getOngoingDownloads() {
        return ongoingDownloads;
    }
    
    public void removeOngoingDownload(String fileID) {
        for (DownloadHandler handler : ongoingDownloads) {
            if (handler.getFileID().equals(fileID)) {
                ongoingDownloads.remove(handler);
            }
        }
    }
    
}
