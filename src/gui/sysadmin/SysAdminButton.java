package gui.sysadmin;

import java.util.HashMap;

/**
 * @author c12jvr.
 */
public enum SysAdminButton {
	/*
	 * To add a new name for button, simple type in the name of the enum, and 
	 * the name of the button within parenthesis. 
	 * */
    ANNOTATIONS("Annotations"),
    USERS("Users"),
    TEST("Test"),
    ANNOTATIONS_MODIFY("Modify"),
    ANNOTATIONS_ADD("Add"),
    ANNOTATIONS_DELETE("Remove"),
    POPUP_CREATE_ANNOTATION("Create annotation");
    private String name;

    private SysAdminButton(String name){
        this.name = name;
        SysadminHelper.map.put(name, this);
    }

    public String getValue(){
        return name;
    }

    public static SysAdminButton fromString(String string) {
        return SysadminHelper.map.get(string);
    }

    static class SysadminHelper {
        static HashMap<String, SysAdminButton> map = new HashMap<String, SysAdminButton>();
    }
}
