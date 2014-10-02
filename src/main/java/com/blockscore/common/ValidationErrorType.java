package com.blockscore.common;

import org.jetbrains.annotations.NotNull;

/**
 * Validation error enum
 * Created by Tony Dieppa on 10/1/14.
 */
public enum ValidationErrorType {
    INVALID("is_invalid"), CANNOT_BE_BLANK("cant_be_blank"), UNKNOWN("unknown");

    private final String mValue;

    private ValidationErrorType(@NotNull final String value) {
        mValue = value;
    }

    /**
     * Returns if this matches.
     * @param value Value to test
     * @return True or false.
     */
    public boolean isEqualTo(final String value) {
        return mValue.equalsIgnoreCase(value);
    }

    /**
     * Converts a string to a enum.
     * @param value Value to convert.
     * @return Validation error type
     */
    @NotNull
    public static ValidationErrorType toEnum(@NotNull final String value) {
        if (value.equalsIgnoreCase(INVALID.toString())) {
            return INVALID;
        } else if (value.equalsIgnoreCase(CANNOT_BE_BLANK.toString())) {
            return CANNOT_BE_BLANK;
        } else {
            return UNKNOWN;
        }
    }

    /**
     * Converts an enum to the string value.
     * @return String value for enum.
     */
    @Override
    public String toString() {
        return mValue;
    }
}
