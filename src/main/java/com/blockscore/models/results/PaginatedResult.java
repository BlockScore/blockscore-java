package com.blockscore.models.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaginatedResult<T> {
  private PaginatedResult() {
    // do nothing. No argument constructor is necessary for Retrofit.
  }

  /**
   * Creates a PaginatedResult.
   *
   * @param data  the current page of data
   * @param totalCount  the total number of related items
   * @param hasMore  whether or not there are more related items
   */
  public PaginatedResult(List<T> data, int totalCount, boolean hasMore) {
    this.totalCount = totalCount;
    this.hasMore = hasMore;
    this.data = data;
  }

  @JsonProperty("total_count")
  private int totalCount;

  @NotNull
  @JsonProperty("has_more")
  private boolean hasMore;

  @NotNull
  @JsonProperty("data")
  private List<T> data;

  /**
   * Returns the total number of related items.
   *
   * @return the total number of related items
   */
  public int getTotalCount() {
    return totalCount;
  }

  /**
   * Returns whether or not there are more pages of related items.
   *
   * @return whether or not there are more related items
   */
  public boolean hasMore() {
    return hasMore;
  }

  /**
   * Returns the current 'page' of data.
   *
   * @return the 'page' of data
   */
  @NotNull
  public List<T> getData() {
    return Collections.unmodifiableList(data);
  }

}
