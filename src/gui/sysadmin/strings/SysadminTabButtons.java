package gui.sysadmin.strings;

import java.util.HashMap;

/**
 * @author c12jvr.
 */
public enum SysadminTabButtons {
    /*
     * To add a new name for button, simple type in the name of the enum, and
     * the name of the button within parenthesis.
     */
    ANNOTATIONS(SysStrings.ANNOTATIONS), USERS(SysStrings.USERS), GENOMES(
            SysStrings.GENOME);
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
