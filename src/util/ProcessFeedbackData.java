package util;

import java.util.Random;

/**
 * @author 1337H4xx0r
 * Created by c11dkn on 2014-05-16.
 */
public class ProcessFeedbackData {

    public String experimentName;
    public String status;
    public String author;
    public String[] outputFiles;
    public long timeAdded;
    public long timeStarted;
    public long timeFinished;

    public ProcessFeedbackData(String experimentName, String status, String author
            , String[] outputFiles, long timeAdded, long timeFinished, long timeStarted) {
        this.experimentName = experimentName;
        this.status = status;
        this.author = author;
        this.outputFiles = outputFiles;
        this.timeAdded = timeAdded;
        this.timeFinished = timeFinished;
        this.timeStarted = timeStarted;
    }
    
    public static ProcessFeedbackData[] getExample() {
        String[] names = { "Kalle", "Pelle", "Anna", "Nils", "Olle" };
        String[] statuses = { "Finished", "Crashed", "Started", "Waiting"};
        Random rand = new Random();
        ProcessFeedbackData[] data = new ProcessFeedbackData[10];
        for(int i=0; i<10; i++) {
            String expName = "experiment" + i;
            String author = names[rand.nextInt(5)];
            String status = statuses[rand.nextInt(4)];
            String[] files = new String[] {"file1","file2","file3"};
            data[i] = new ProcessFeedbackData(expName, status, author, files, 100,100,0);
        }
        return data;
    }
}

