package util;

public class AnnotationData {
    public String name;
    
    public AnnotationData(String name) {
        this.name = UTF8Converter.convertFromUTF8(name);
    }
    
    public String getName() {
        return name;
    }
}
