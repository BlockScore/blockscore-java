package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Model representing a person's identity.
 * Created by Tony Dieppa on 9/29/14.
 */
public class Person {
    @NotNull
    @JsonProperty("date_of_birth")
    private Date mDateOfBirth;

    @NotNull
    @JsonProperty("identification")
    private Identification mIdentification;
}
