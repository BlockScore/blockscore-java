package com.blockscore.common;

import org.jetbrains.annotations.NotNull;

/**
 * Validation error enum
 */
public enum ValidationErrorType {
    INVALID("is_invalid"),
    CANNOT_BE_BLANK("cant_be_blank"),
    UNKNOWN("unknown");

    private final String value;

    ValidationErrorType(@NotNull final String value) {
        this.value = value;
    }

    /**
     * Returns if this matches.
     * @param value  the value to test
     * @return whether the value is equivalent to the enum
     */
    public boolean isEqualTo(final String value) {
        return this.value.equalsIgnoreCase(value);
    }

    /**
     * Converts a string to a enum.
     *
     * @param value  the value to convert
     * @return the validation error type
     */
    @NotNull
    public static ValidationErrorType toEnum(@NotNull final String value) {
        if (value.equalsIgnoreCase(INVALID.value)) {
            return INVALID;
        } else if (value.equalsIgnoreCase(CANNOT_BE_BLANK.value)) {
            return CANNOT_BE_BLANK;
        } else {
            return UNKNOWN;
        }
    }
}
