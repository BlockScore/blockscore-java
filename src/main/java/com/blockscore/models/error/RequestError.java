package com.blockscore.models.error;

import com.blockscore.common.BlockscoreErrorType;
import com.blockscore.common.ValidationErrorType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Request Error model.
 * Created by Tony Dieppa on 10/1/14.
 */
public class RequestError {

    @Nullable
    @JsonProperty("param")
    private String mParam;

    @Nullable
    @JsonProperty("message")
    private String mMessage;

    @Nullable
    @JsonProperty("type")
    private String mType;

    @Nullable
    @JsonProperty("code")
    private String mCode;

    /**
     * Gets the invalid parameter.
     * @return Invalid parameter.
     */
    @Nullable
    public String getParam() {
        return mParam;
    }

    /**
     * Gets the error message.
     * @return Error message.
     */
    @NotNull
    public String getMessage() {
        return mMessage;
    }

    /**
     * Gets the error type.
     * @return Error type.
     */
    @NotNull
    public BlockscoreErrorType getErrorType() {
        if (mType == null) {
            return BlockscoreErrorType.UNKNOWN;
        } else {
            return BlockscoreErrorType.toEnum(mType);
        }
    }

    /**
     * Gets the validation error type.
     * @return Validation error type.
     */
    @NotNull
    public ValidationErrorType getValidationErrorCode() {
        if (mCode == null) {
            return ValidationErrorType.UNKNOWN;
        } else {
            return ValidationErrorType.toEnum(mCode);
        }
    }
}
