package requests;

public class RenameAnnotationRequest extends Request {

    public String name;
    public String newname;

    public RenameAnnotationRequest(String oldname, String newname) {
        super("renameAnnotationRequest", "/annotation/field", "PUT");
        this.name = oldname;
        this.newname = newname;
    }
    
}
