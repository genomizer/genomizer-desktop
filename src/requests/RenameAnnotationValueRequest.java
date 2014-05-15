package requests;

public class RenameAnnotationValueRequest extends Request {

    private String name;
    private String oldValue;
    private String newValue;

    public RenameAnnotationValueRequest(String annotationName, String oldValue,
            String newValue) {
        super("renameAnnoationValueRequest", "/annotation/value", "PUT");
        this.name = annotationName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    
}
