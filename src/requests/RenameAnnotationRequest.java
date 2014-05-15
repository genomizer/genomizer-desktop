package requests;

public class RenameAnnotationRequest extends Request {

    public String name;
    public String newName;

    public RenameAnnotationRequest(String oldname, String newname) {
        super("renameAnnotationRequest", "/annotation/field", "PUT");
        this.name = oldname;
        this.newName = newname;
    }
    
}
