package com.blockscore.models.error;

import com.blockscore.common.BlockscoreErrorType;
import com.blockscore.common.ValidationErrorType;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Request Error model.
 */
public class RequestError {
    @Nullable
    @JsonProperty("param")
    private String param;

    @Nullable
    @JsonProperty("message")
    private String message;

    @Nullable
    @JsonProperty("type")
    private String type;

    @Nullable
    @JsonProperty("code")
    private String code;

    /**
     * Gets the invalid parameter.
     * @return the invalid parameter
     */
    @Nullable
    public String getParam() {
        return param;
    }

    /**
     * Gets the error message.
     * @return the error message
     */
    @Nullable
    public String getMessage() {
        return message;
    }

    /**
     * Gets the error type.
     * @return the error type
     */
    @NotNull
    public BlockscoreErrorType getErrorType() {
        if (type == null) {
            return BlockscoreErrorType.UNKNOWN;
        } else {
            return BlockscoreErrorType.toEnum(type);
        }
    }

    /**
     * Gets the validation error type.
     *
     * @return the validation error type
     */
    @NotNull
    public ValidationErrorType getValidationErrorCode() {
        if (code == null) {
            return ValidationErrorType.UNKNOWN;
        } else {
            return ValidationErrorType.toEnum(code);
        }
    }
}
