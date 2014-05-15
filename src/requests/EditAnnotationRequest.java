package requests;

import java.util.HashMap;

public class EditAnnotationRequest extends Request {
    public String name;
    public String annotation;

    public EditAnnotationRequest(String name,
            String annotation) {
        super("changeannotation", "/file/changeAnnotation", "POST");
        this.name = name;
        this.annotation = annotation;
    }
}
