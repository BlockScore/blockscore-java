package com.blockscore.models.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Blockscore Error model.
 * Created by Tony Dieppa on 10/1/14.
 */
public class BlockscoreError {
    @NotNull
    @JsonProperty("error")
    private RequestError mRequestError;

    /**
     * Returns the error details.
     * @return Error details.
     */
    @NotNull
    public RequestError getError() {
        return mRequestError;
    }
}
