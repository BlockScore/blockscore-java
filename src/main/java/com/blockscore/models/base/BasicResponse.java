package com.blockscore.models.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

/**
 * Basic parameters common to models
 */
public abstract class BasicResponse {
    @Nullable
    @JsonProperty("id")
    private String id;

    @JsonProperty("created_at") // TODO: Parse as Date way
    private long createAtDate;

    @JsonProperty("updated_at") // TODO: ""
    private long updatedAtDate;

    @JsonProperty("livemode")
    private boolean liveMode;

    /**
     * Gets the ID for this record.
     *
     * @return the ID associated with this record
     */
    @Nullable
    public String getId() {
        return id;
    }

    /**
     * Gets the creation date. (ms)
     *
     * @return the record's creation date
     */
    public long getCreatedAtDate() {
        return createAtDate;
    }

    /**
     * Gets the updated date. (ms)
     *
     * @return the last updated date
     */
    public long getUpdatedAtDate() {
        return updatedAtDate;
    }

    /**
     * Indicates whether the company was created using a live or test API key.
     *
     * @return whether or not the company was created with a live or test API key
     */
    public boolean isLiveMode() {
        return liveMode;
    }
}
