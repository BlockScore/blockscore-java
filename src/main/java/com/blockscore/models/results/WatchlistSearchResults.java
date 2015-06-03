package com.blockscore.models.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Watchlist search results.
 */
public class WatchlistSearchResults {
  @NotNull
  @JsonProperty("searched_lists")
  private String[] searchedLists;

  @NotNull
  @JsonProperty("count")
  private int count;

  @NotNull
  @JsonProperty("matches")
  private List<WatchlistHit> matches;

  public int getCount() {
    return count;
  }

  @NotNull
  public String[] getSearchedLists() {
    return Arrays.copyOf(searchedLists, searchedLists.length);
  }

  @NotNull
  public List<WatchlistHit> getMatches() {
    return matches;
  }
}
