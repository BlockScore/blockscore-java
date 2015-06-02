package com.blockscore.exceptions;

import com.blockscore.common.ValidationErrorType;
import com.blockscore.models.error.BlockscoreError;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Exception thrown when an invalid request is returned from the server.
 */
public class InvalidRequestException extends RuntimeException {
    private final BlockscoreError mError;

    public InvalidRequestException(@NotNull final BlockscoreError error) {
        super(error.getError().getMessage());
        mError = error;
    }

    /**
     * Gets the invalid parameters.
     *
     * @return the invalid parameter
     */
    @Nullable
    public String getInvalidParam() {
        return mError.getError().getParam();
    }

    /**
     * Gets the validation error type. (If any)
     * @return the validation error type
     */
    @Nullable
    public ValidationErrorType getValidationErrorCode() {
        return mError.getError().getValidationErrorCode();
    }
}
