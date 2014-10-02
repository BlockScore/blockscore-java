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
     * Returns if this matches.
     * @return True or false.
     */
    public boolean isEqualTo(final String value) {
        return mValue.equalsIgnoreCase(value);
    }

    /**
     * Converts a string to an enum.
     * @param value Value to convert.
     * @return Validity status type.
     */
    @NotNull
    public static ValidityStatus toEnum(@NotNull final String value) {
        if (value.equalsIgnoreCase(VALID.toString())) {
            return VALID;
        } else {
            return INVALID;
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