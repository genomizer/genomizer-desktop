package controller;

import gui.GUI;
import gui.SettingsTab;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import util.RequestException;

import model.GenomizerModel;

public class SettingsTabController {
    GUI view;
    GenomizerModel model;


    /**
     * Constructor..
     *
     * @param view
     * @param model
     * @param fileChooser
     */
    public SettingsTabController(GUI view, GenomizerModel model) {
        this.view = view;
        this.model = model;

        SettingsTab st = view.getSettingsTab();
        st.updateUserButtonListener(UpdateUserListener());


    }

    public ActionListener UpdateUserListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {

                        String oldPass = view.getSettingsTab().getOld();
                        String newPass = view.getSettingsTab().getNew();
                        String email = view.getSettingsTab().getEmail();
                        String name = view.getSettingsTab().getRealName();

                        try {
                            model.updateUserSettings(oldPass,newPass,name,email);
                            view.setInstantStatusPanelColor(new Color(155,255,155));
                            view.setStatusPanel("User update succeful");
                            view.setStatusPanelColor("success");
                        } catch (RequestException e) {
                            // TODO Auto-generated catch block
                            view.setInstantStatusPanelColor(new Color(255,155,155));
                            view.setStatusPanel("User update failed");
                            view.setStatusPanelColor("fail");
                         //   e.printStackTrace();

                        }

                        //view.getSettingsTab().updateUser(model);
                    };
                }.start();
            }
        };
    }

}
