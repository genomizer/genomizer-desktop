package model;

import java.util.ArrayList;

import util.ExperimentData;

public class SearchHistory {

    private ArrayList<String> searchHistory;

    public SearchHistory() {
        searchHistory = new ArrayList<String>();
    }

    public void addSearchToHistory(String searchQuery) {
        if (searchHistory.size() > 20){
            searchHistory.remove(0);
        }
        searchHistory.add(searchQuery);
    }

    public ArrayList<String> getSearchHistory() {
       return searchHistory;
    }

}
