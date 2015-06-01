package com.blockscore.models.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ArrayList;

public class PaginatedResult<T> {
    public PaginatedResult() {
        // do nothing. No argument constructor is necessary for Retrofit.
    }

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

    public int getTotalCount() {
    	return totalCount;
    }

    public boolean hasMore() {
    	return hasMore;
    }

    @NotNull
    public List<T> getData() {
    	return new ArrayList<T>(data); //TODO: [-double-check-] FIX copy method
    }

}