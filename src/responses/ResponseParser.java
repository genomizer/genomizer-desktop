package responses;

import util.AnnotationDataType;
import util.ExperimentData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;

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

    public static ExperimentData parseRetrieveExp(String json) {
        ExperimentData retrieveExpResponse = null;
        try {
            retrieveExpResponse = gson.fromJson(json, ExperimentData.class);
        } catch (JsonParseException e) {
            return null;
        }
        return retrieveExpResponse;
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

    /** TODO NOT CALLED ANYWHERE YET! */
    public static GenomeReleaseData[] parseGetGenomeReleaseResponse(String json) {

        GenomeReleaseData[] genomeReleaseResponses = null;
        try {
            genomeReleaseResponses = gson.fromJson(json,
                    GenomeReleaseData[].class);

        } catch (JsonParseException e) {
            return null;
        }
        
        return genomeReleaseResponses;
    }

    public static AddFileToExperimentResponse parseUploadResponse(String json) {
        AddFileToExperimentResponse url = null;
        try {
            url = gson.fromJson(json, AddFileToExperimentResponse.class);
        } catch (JsonParseException e) {

        }
        return url;
    }

    public static ProcessFeedbackData[] parseProcessFeedbackResponse(String json) {
        ProcessFeedbackData[] processFeedbackData = null;
        try {
            processFeedbackData = gson.fromJson(json,
                    ProcessFeedbackData[].class);
        } catch (JsonParseException e) {
            return null;
        }
        return processFeedbackData;
    }
}
