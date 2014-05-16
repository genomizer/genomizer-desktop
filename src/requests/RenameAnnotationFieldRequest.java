package requests;

public class RenameAnnotationFieldRequest extends Request {

    public String name;
    public String newName;

    public RenameAnnotationFieldRequest(String oldname, String newname) {
        super("renameAnnotationRequest", "/annotation/field", "PUT");
        this.name = oldname;
        this.newName = newname;
    }
    
}
