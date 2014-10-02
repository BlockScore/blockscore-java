package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

/**
 * Identification model. (Stores either SSN or passport information)
 * Created by Tony Dieppa on 9/29/14.
 */
public class Identification {

    @Nullable
    @JsonProperty("passport")
    private String mPassport;

    @Nullable
    @JsonProperty("ssn")
    private String mSSN;

    /**
     * Sets the passport number identifying a person.
     * @param passport Passport number.
     */
    public void setPassport(@Nullable String passport) {
        mPassport = passport;
    }

    /**
     * Sets the last 4 of the person's SSN.
     * @param ssn SSN (last 4)
     */
    public void setSSN(@Nullable String ssn) {
        mSSN = ssn;
    }

    /**
     * Gets the passport number for this person.
     * @return Passport number.
     */
    @Nullable
    public String getPassport() {
        return mPassport;
    }

    /**
     * Gets the SSN of this person.
     * @return SSN.
     */
    @Nullable
    public String getSSN() {
        return mSSN;
    }
}
