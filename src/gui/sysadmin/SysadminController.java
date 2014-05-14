package gui.sysadmin;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import model.GenomizerModel;
import requests.AddAnnotationRequest;

public class SysadminController extends Observable {
    
    private static SysadminTab sysTab;
    private GenomizerModel     model;
    
    public SysadminController(Observer observer) {
        
        this.addObserver(observer);
        
    }
    
    public SysadminController(GenomizerModel model) {
        this.model = model;
    }
    
    /* You need me */
    public void setSysadminPanel(SysadminTab sysTab) {
        
        this.sysTab = sysTab;
        
    }
    
    public static ActionListener createAnnotationButtonListener() {
        return new AnnotationButtonsListener(sysTab);
    }
    
    public static ActionListener createAnnotationPopupListener() {
        return new AnnotationPopupListener(sysTab);
    }
    
    public void sendNewAnnotation() {
        
        SysadminAnnotationPopup popup = sysTab.getAnnotationsView().getPop();
        model.addNewAnnotation(popup.getNewAnnotationName(),
                popup.getNewAnnotationCategories(),
                popup.getNewAnnotationForcedValue());
        
        // System.out.println(popup.getNewAnnotationName());
        // AddAnnotationRequest ann = new AddAnnotationRequest(
        // popup.getNewAnnotationName(),
        // popup.getNewAnnotationCategories(),
        // popup.getNewAnnotationForcedValue());
        //
        // setChanged();
        // notifyObservers(ann);
    }
}
