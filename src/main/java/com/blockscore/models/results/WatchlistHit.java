package com.blockscore.models.results;

import com.blockscore.models.WatchlistCandidate;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

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
    @NotNull
    public String[] getMatchingInfo() {
        if (mMatchingInfo == null) {
            mMatchingInfo = new String[0];
        }
        return Arrays.copyOf(mMatchingInfo, mMatchingInfo.length);
    }
}
