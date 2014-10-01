package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Address model. Used to identify the address for an individual.
 * Created by Tony Dieppa on 9/29/14.
 */
public class Address {
    @NotNull
    @JsonProperty("street1")
    private String mStreet1;

    @Nullable
    @JsonProperty("street2")
    private String mStreet2;

    @NotNull
    @JsonProperty("city")
    private String mCity;

    @NotNull
    @JsonProperty("state")
    private String mState;

    @NotNull
    @JsonProperty("postal_code")
    private String mPostalCode;

    @NotNull
    @JsonProperty("country_code")
    private String mCountryCode;

    public Address() {
        //Do nothing.
    }

    public Address(@NotNull final String street1, @Nullable final String street2, @NotNull final String city
            , @NotNull final String state, @NotNull final String postalCode, @NotNull final String countryCode) {
        mStreet1 = street1;
        mStreet2 = street2;
        mCity = city;
        mState = state;
        mPostalCode = postalCode;
        mCountryCode = countryCode;
    }

    /**
     * The primary street address of the customer. This is automatically normalized.
     * @param street1 Street (Line 1)
     * @return this
     */
    @NotNull
    public Address setStreet1(@NotNull final String street1) {
        mStreet1 = street1;
        return this;
    }

    /**
     * The second address line typically used for apartment or suite numbers. This is automatically normalized.
     * @param street2 Street (Line 2)
     * @return this
     */
    @Nullable
    public Address setStreet2(@NotNull final String street2) {
        mStreet2 = street2;
        return this;
    }

    /**
     * The city name of the customer. This is automatically normalized.
     * @param city City
     * @return this
     */
    @NotNull
    public Address setCity(@NotNull final String city) {
        mCity = city;
        return this;
    }

    /**
     * The state of the customer. Should be of the FIPS code form. For example California would be CA.
     * @param state State (FIPS code format)
     * @return this
     */
    @NotNull
    public Address setState(@NotNull final String state) {
        mState = state;
        return this;
    }

    /**
     * The postal code, also known as the ZIP code of the address.
     * @param postalCode ZIP code
     * @return this
     */
    @NotNull
    public Address setPostalCode(@NotNull final String postalCode) {
        mPostalCode = postalCode;
        return this;
    }

    /**
     * The country of the customer. Should be of the ISO code form.
     * @param countryCode Country code of the individual.
     * @return this
     */
    @NotNull
    public Address setCountryCode(@NotNull final String countryCode) {
        mCountryCode = countryCode;
        return this;
    }
}
