package com.blockscore.models.results;

import com.blockscore.models.Candidate;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Watchlist match.
 * Created by Tony Dieppa on 9/30/14.
 */
public class WatchlistMatch {
    @JsonProperty("confidence")
    private double mConfidence;

    @NotNull
    @JsonProperty("watchlist_name")
    private String mWatchList;

    @NotNull
    @JsonProperty("matching_record")
    private Candidate mCandidate;

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

    /**
     * Gets the confidence rating.
     * @return Confidence rating (percentage in decimal)
     */
    public double getConfidence() {
        return mConfidence;
    }

    /**
     * Gets the watchlist associated with this match.
     * @return Watchlist match.
     */
    @NotNull
    public String getWatchList() {
        return mWatchList;
    }

    /**
     * Gets the matching record.
     * @return Matching record.
     */
    @NotNull
    public Candidate getMatchingRecord() {
        return mCandidate;
    }
}
