package controller;

import gui.GUI;
import gui.SettingsTab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                        view.getSettingsTab().updateUser(model);
                    };
                }.start();
            }
        };
    }
}
