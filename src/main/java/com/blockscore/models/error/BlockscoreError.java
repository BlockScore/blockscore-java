package com.blockscore.models.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit.RetrofitError;

/**
 * Blockscore Error model.
 * Created by Tony Dieppa on 10/1/14.
 */
public class BlockscoreError {
    @NotNull
    @JsonProperty("error")
    private RequestError mRequestError;

    /**
     * Converts a Retrofit Error into a Blockscore Error.
     * @param cause Retrofit error to convert.
     * @return Blockscore Error
     */
    @Nullable
    public static BlockscoreError getBlockscoreError(@NotNull final RetrofitError cause) {
        Object rawError = cause.getBodyAs(BlockscoreError.class);
        if (rawError instanceof BlockscoreError) {
            return (BlockscoreError) rawError;
        } else {
            return null;
        }
    }

    /**
     * Returns the error details.
     * @return Error details.
     */
    @NotNull
    public RequestError getError() {
        return mRequestError;
    }
}
