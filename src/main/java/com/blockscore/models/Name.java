package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Name model. Used to identify the name of an individual.
 * Created by tealocean on 9/29/14.
 */
public class Name {
    @NotNull
    @JsonProperty("first")
    private String mFirstName;

    @Nullable
    @JsonProperty("middle")
    private String mMiddle;

    @NotNull
    @JsonProperty("last")
    private String mLast;

    public Name() {
        //Do nothing.
    }

    public Name(@NotNull final String firstname, @Nullable final String middleName
            , @NotNull final String lastName) {
        mFirstName = firstname;
        mMiddle = middleName;
        mLast = lastName;
    }

    /**
     * Sets the individual's first name.
     * @param firstName First name.
     */
    public void setFirstName(@NotNull final String firstName) {
        mFirstName = firstName;
    }

    /**
     * Sets the middle name (optional)
     * @param middle Middle name.
     */
    public void setMiddle(@Nullable final String middle) {
        mMiddle = middle;
    }

    /**
     * Sets the last name.
     * @param last Last name.
     */
    public void setLast(@NotNull final String last) {
        mLast = last;
    }
}
