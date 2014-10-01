package com.blockscore.models.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

/**
 * Basic parameters common to models
 * Created by Tony Dieppa on 9/30/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public abstract class BasicResponse {
    @Nullable
    @JsonProperty("id")
    private String mId;

    @JsonProperty("created_at")
    private long mCreatedAtDate;

    @JsonProperty("updated_at")
    private long mUpdatedAtDate;

    @JsonProperty("livemode")
    private boolean mLiveMode;

    /**
     * Gets the ID for this record.
     * @return ID
     */
    @Nullable
    public String getId() {
        return mId;
    }

    /**
     * Gets the creation date. (ms)
     * @return Creation date.
     */
    public long getCreatedAtDate() {
        return mCreatedAtDate;
    }

    /**
     * Gets the updated date. (ms)
     * @return Updated date.
     */
    public long getUpdatedAtDate() {
        return mUpdatedAtDate;
    }

    /**
     * Indicates whether the company was created using a live or test API key.
     * @return True if live.
     */
    public boolean isLiveMode() {
        return mLiveMode;
    }
}
