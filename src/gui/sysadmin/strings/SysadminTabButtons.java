package gui.sysadmin.strings;

import java.util.HashMap;

/**
 * @author c12jvr.
 */
public enum SysadminTabButtons {
    /*
     * To add a new name for button, add the name to the SysStrings class first
     * , and the add it as below with the name of the button within parenthesis.
     */
    ANNOTATIONS(SysStrings.ANNOTATIONS), GENOMES(
            SysStrings.GENOME), USERS(SysStrings.USERS);
    private String name;

    private SysadminTabButtons(String name) {
        this.name = name;
        SysadminHelper.map.put(name, this);
    }

    public static SysadminTabButtons fromString(String string) {
        return SysadminHelper.map.get(string);
    }

    public String getValue() {
        return name;
    }

    static class SysadminHelper {
        static HashMap<String, SysadminTabButtons> map = new HashMap<String, SysadminTabButtons>();
    }
}
