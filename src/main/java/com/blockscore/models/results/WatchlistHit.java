package com.blockscore.models.results;

import com.blockscore.models.WatchlistCandidate;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

/**
 * Model representing a watchlist hit.
 * Created by Tony Dieppa on 9/30/14.
 */
public class WatchlistHit extends WatchlistCandidate {
    @Nullable
    @JsonProperty("matching_info")
    private String[] mMatchingInfo;

    /**
     * Gets matching info for this hit.
     * @return Matching info.
     */
    @Nullable
    public String[] getMatchingInfo() {
        return mMatchingInfo;
    }
}
