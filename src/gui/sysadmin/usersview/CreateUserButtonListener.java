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

                String uName = sysTab.getUserView().getUserName();
                String pass = sysTab.getUserView().getPassword();
                String role = sysTab.getUserView().getRole();
                String rName = sysTab.getUserView().getRealName();
                String mail = sysTab.getUserView().getMail();
                sysTab.getSysController().createNewUser(uName, pass, role, rName, mail);

                break;
            case "Delete User":
                sysTab.getSysController().deleteUser(sysTab.getUserView().getDeleteUserName());

                break;
            case "Update User":
                String uuName = sysTab.getUserView().getUpdateUserName();
                String upass = sysTab.getUserView().getUpdatePassword();
                String urole = sysTab.getUserView().getUpdateRole();
                String urName = sysTab.getUserView().getUpdateRealName();
                String umail = sysTab.getUserView().getUpdateMail();
                sysTab.getSysController().updateUser(uuName, upass, urole, urName, umail);
                break;

        }
    }

}