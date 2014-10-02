package com.blockscore.exceptions;

import com.blockscore.models.error.BlockscoreError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Exception thrown when an API error occurs.
 * Created by Tony Dieppa on 9/29/14.
 */
public class APIException extends RuntimeException {
    private final BlockscoreError mError;

    public APIException(@NotNull final BlockscoreError error) {
        super(error.getError().getMessage());
        mError = error;
    }

    /**
     * Gets the invalid parameters.
     * @return Invalid parameter.
     */
    @Nullable
    public String getInvalidParam() {
        return mError.getError().getParam();
    }
}
