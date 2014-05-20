package requests;

public class RenameAnnotationFieldRequest extends Request {

    public String newName;
    public String oldName;

    public RenameAnnotationFieldRequest(String oldname, String newname) {
        super("renameAnnotationRequest", "/annotation/field", "PUT");
        this.oldName = oldname;
        this.newName = newname;
    }
    
}
