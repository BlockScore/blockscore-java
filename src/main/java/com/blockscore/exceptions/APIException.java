package com.blockscore.exceptions;

import com.blockscore.models.error.BlockscoreError;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Exception thrown when an API error occurs.
 */
public class APIException extends RuntimeException {
    private final BlockscoreError error;
    private static final long serialVersionUID = 0L;

    public APIException(@NotNull final BlockscoreError error) {
        super(error.getError().getMessage());
        this.error = error;
    }

    /**
     * Gets the invalid parameters.
     *
     * @return the invalid parameter
     */
    @Nullable
    public String getInvalidParam() {
        return error.getError().getParam();
    }
}
