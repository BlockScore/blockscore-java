package com.blockscore.common;

import org.jetbrains.annotations.NotNull;

/**
 * Corporation type.
 * Created by Tony Dieppa on 9/30/14.
 */
public enum CorporationType {
    CORP("corporation"), LLC("llc"), PARTNERSHIP("partnership"), SoleProprietor("sp"), OTHER("other");

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
}