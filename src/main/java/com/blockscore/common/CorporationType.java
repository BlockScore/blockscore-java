package com.blockscore.common;

import org.jetbrains.annotations.NotNull;

/**
 * Corporation type.
 * Created by Tony Dieppa on 9/30/14.
 */
public enum CorporationType {
    CORP("corporation"), LLC("llc"), PARTNERSHIP("partnership"), SOLEPROPRIETORSHIP("sp"), OTHER("other");

    private final String mValue;

    private CorporationType(@NotNull final String value) {
        mValue = value;
    }

    /**
     * Returns if this status matches.
     * @return True or false.
     */
    public boolean isEqualTo(final String value) {
        return mValue.equalsIgnoreCase(value);
    }

    @Override
    public String toString() {
        return mValue;
    }

    /**
     * Converts a string to a corporation type enum.
     * @param value Value to convert.
     * @return Corporation type.
     */
    public static CorporationType toEnum(@NotNull final String value) {
        if (value.equalsIgnoreCase(CORP.toString())) {
            return CORP;
        } else if (value.equalsIgnoreCase(LLC.toString())) {
            return LLC;
        } else if (value.equalsIgnoreCase(PARTNERSHIP.toString())) {
            return PARTNERSHIP;
        } else if (value.equalsIgnoreCase(SOLEPROPRIETORSHIP.toString())) {
            return SOLEPROPRIETORSHIP;
        } else {
            return OTHER;
        }
    }
}