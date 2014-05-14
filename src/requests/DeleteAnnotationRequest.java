package requests;

import util.DeleteAnnoationData;

public class DeleteAnnotationRequest extends Request {
    
    private DeleteAnnoationData[] deleteId;
    
    public DeleteAnnotationRequest(DeleteAnnoationData deleteAnnoationData) {
        super("deleteAnnotation", "/annotation", "DELETE");
        this.deleteId = new DeleteAnnoationData[] { deleteAnnoationData };
    }
}
