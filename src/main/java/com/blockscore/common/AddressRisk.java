package com.blockscore.common;

import org.jetbrains.annotations.NotNull;

public enum AddressRisk {
    HIGH("high"),
    LOW("low"),
    NO_MATCH("no_match"),
    NO_DATA("insufficient_data");

    private final String mValue;

    AddressRisk(@NotNull final String value) {
        mValue = value;
    }

    @Override
    public String toString() {
        return mValue;
    }

    /**
     * Converts a string to a enum.
     *
     * @param value  the value to convert
     * @return the matching AddressRisk enum value
     */
    public static AddressRisk toEnum(@NotNull final String value) {
        if (value.equalsIgnoreCase(HIGH.toString())) {
            return HIGH;
        } else if (value.equalsIgnoreCase(LOW.toString())) {
            return LOW;
        } else if (value.equalsIgnoreCase(NO_MATCH.toString())) {
            return NO_MATCH;
        } else {
            return NO_DATA;
        }
    }
}