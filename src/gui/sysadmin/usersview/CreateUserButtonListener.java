package gui.sysadmin.usersview;

import gui.sysadmin.SysadminTab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUserButtonListener implements ActionListener {

    private SysadminTab sysTab;

    /**
     * The listener that handles actions within the "Annotations" view in the
     * SysadminTab
     *
     * @param sysTab
     *            is the SysadminTab connected to the listener
     */
    public CreateUserButtonListener(SysadminTab sysTab) {
        this.sysTab = sysTab;
    }

    /**
     * The actionPerformed method checks and compares the ActionCommand of the
     * event and performs different actions depending on where the action
     * originated.
     */
    @Override
    public void actionPerformed(ActionEvent e) {



        switch (e.getActionCommand()) {
            case "Create User":
                System.out.println(sysTab.getUserView().getUserName());
                //sysTab.getSysController()...

                break;
            case "Delete User":


                break;
            case "Update User":

                break;

        }
    }

}