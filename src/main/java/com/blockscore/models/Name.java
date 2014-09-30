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

    public Name(@NotNull final String firstname, @Nullable final String middleName
            , @NotNull final String lastName) {
        mFirstName = firstname;
        mMiddle = middleName;
        mLast = lastName;
    }
}
