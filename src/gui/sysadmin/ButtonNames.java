package gui.sysadmin;

public class ButtonNames {
    protected final static String ANNOTATIONS = "Annotations";
    protected final static String USERS = "Users";
    protected final static String TEST = "Test";

    protected final static String ANNOTATIONS_MODIFY = "Modify";
    protected final static String ANNOTATIONS_ADD = "Add";
    protected final static String ANNOTATIONS_DELETE = "Remove";
    public final static String POPUP_CREATE_ANNO = "Create annotation";

    public ButtonNames() {

    }

    public String[] getButtonNames() {
        return new String[] { ANNOTATIONS, USERS, TEST };
    }

}
