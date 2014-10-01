package com.blockscore.models.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Watchlist search results.
 * Created by Tony Dieppa on 9/30/14.
 */
public class WatchlistSearchResults {
    @JsonProperty("search_time")
    private double mSearchTime;

    @NotNull
    @JsonProperty("searched_lists")
    private String[] mSearchedLists;

    @NotNull
    @JsonProperty("matches")
    private List<WatchlistMatch> mMatches;

    /**
     * Gets the time for the search.
     * @return Search time.
     */
    public double getSearchTime() {
        return mSearchTime;
    }

    /**
     * Gets a list of the watch lists examined.
     * @return Watchlists examined.
     */
    @NotNull
    public String[] getSearchedLists() {
        return mSearchedLists;
    }

    /**
     * Gets a list of all matching entries.
     * @return Matching entries.
     */
    @NotNull
    public List<WatchlistMatch> getMatches() {
        return mMatches;
    }
}
