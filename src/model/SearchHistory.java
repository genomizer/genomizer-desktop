package model;

import java.util.ArrayList;

import util.ExperimentData;

public class SearchHistory {

	private ArrayList<ExperimentData[]> searchHistory;

	public void searchHistory() {
		searchHistory = new ArrayList<ExperimentData[]>();
	}

	public void addSearchToHistory(ExperimentData[] searchResponses) {
		// searchHistory.add(searchResponses);
	}

	public void getSearchFromHistory(int i) {
		searchHistory.get(i);
	}

}
