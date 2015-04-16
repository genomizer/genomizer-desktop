package model;

/**
 * New Exception created to avoid using Exception directly.
 * Does nothing else.
 * @author c12oor
 *
 */
public class DeleteAnnotationException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -5001954473831088220L;

    public DeleteAnnotationException(String responseBody) {
        super(responseBody);
    }

}
