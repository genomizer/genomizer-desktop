package util;

public class AnnotationDataType extends AnnotationData {

    private String[] values;
    private boolean forced;

    public AnnotationDataType(String name, String[] values, Boolean forced) {
        super(name);
        this.values = values;
        this.forced = forced;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String[] getValues() {
        return values;
    }

    public boolean isForced() {
        return forced;
    }

    public boolean getForced() {
        return forced;
    }
}
