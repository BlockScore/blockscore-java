package com.blockscore.common;

import org.jetbrains.annotations.NotNull;

/**
 * Validity Status
 * Created by Tony Dieppa on 9/30/14.
 */
public enum ValidityStatus {
    VALID("valid"), INVALID("invalid");

    private final String mValue;

    private ValidityStatus(@NotNull final String value) {
        mValue = value;
    }

    /**
     * Returns if this status matches.
     * @return True or false.
     */
    public boolean isEqualTo(final String value) {
        return mValue.equalsIgnoreCase(value);
    }

    /**
     * Converts a string to a corporation type enum.
     * @param value Value to convert.
     * @return Corporation type.
     */
    @NotNull
    public static ValidityStatus toEnum(@NotNull final String value) {
        if (value.equalsIgnoreCase(VALID.toString())) {
            return VALID;
        } else {
            return INVALID;
        }
    }
}