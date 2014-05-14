package responses;

import util.AnnotationDataType;
import util.ExperimentData;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class ResponseParser {
    
    private static Gson gson = new Gson();
    
    public static LoginResponse parseLoginResponse(String json) {
        LoginResponse loginResponse = null;
        try {
            loginResponse = gson.fromJson(json, LoginResponse.class);
        } catch (JsonParseException e) {
            return null;
        }
        return loginResponse;
    }
    
    public static ExperimentData[] parseSearchResponse(String json) {
        ExperimentData[] searchResponses = null;
        try {
            searchResponses = gson.fromJson(json, ExperimentData[].class);
        } catch (JsonParseException e) {
            return null;
        }
        return searchResponses;
    }
    
    public static AnnotationDataType[] parseGetAnnotationResponse(String json) {
        AnnotationDataType[] annotationResponses = null;
        try {
            annotationResponses = gson.fromJson(json,
                    AnnotationDataType[].class);
        } catch (JsonParseException e) {
            return null;
        }
        return annotationResponses;
    }
    
    public static AddFileToExperimentResponse parseUploadResponse(String json) {
        AddFileToExperimentResponse url = null;
        try {
            url = gson.fromJson(json, AddFileToExperimentResponse.class);
        } catch (JsonParseException e) {
            
        }
        return url;
    }
}
