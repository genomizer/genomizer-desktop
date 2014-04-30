package model;

import java.util.ArrayList;

import responses.SearchResponse;

public class SearchHistory {

    private ArrayList<SearchResponse[]> searchHistory;

    public void searchHistory() {
	searchHistory = new ArrayList<SearchResponse[]>();
    }

    public void addSearchToHistory(SearchResponse[] searchResponses) {
	// searchHistory.add(searchResponses);
    }

    public void getSearchFromHistory(int i) {
	searchHistory.get(i);
    }

}
