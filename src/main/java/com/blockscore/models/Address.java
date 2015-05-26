package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Address {
    @NotNull
    private String street1;
    
    @Nullable
    private String street2;
    
    @NotNull
    private String city;

    @NotNull
    private String subdivision;

    @NotNull
    private String postalCode;
    
    @NotNull
    private String countryCode;


    public Address() {
        // do nothing
    }

    public Address(@NotNull final String street1, @Nullable final String street2, @NotNull final String city
            , @NotNull final String subdivision, @NotNull final String postalCode, @NotNull final String countryCode) {
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.subdivision = subdivision;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
    }

    /**
     * The primary street address of the customer. This is automatically normalized.
     * @param street1 Street (Line 1)
     * @return this
     */
    @NotNull
    public Address setStreet1(@NotNull final String street1) {
        this.street1 = street1;
        return this;
    }

    /**
     * The second address line typically used for apartment or suite numbers. This is automatically normalized.
     * @param street2 Street (Line 2)
     * @return this
     */
    @Nullable
    public Address setStreet2(@NotNull final String street2) {
        this.street2 = street2;
        return this;
    }

    /**
     * The city name of the customer. This is automatically normalized.
     * @param city City
     * @return this
     */
    @NotNull
    public Address setCity(@NotNull final String city) {
        this.city = city;
        return this;
    }

    /**
     * The subdivision of the entered country. For instance in the United States, this would be a 'state'.
     * Ex: The subdivision for California would be CA.
     * @param subdivision Subdivision (FIPS code format)
     * @return this
     */
    @NotNull
    public Address setSubdivision(@NotNull final String subdivision) {
        this.subdivision = subdivision;
        return this;
    }

    /**
     * The postal code, also known as the ZIP code of the address.
     * @param postalCode ZIP code
     * @return this
     */
    @NotNull
    public Address setPostalCode(@NotNull final String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    /**
     * The country of the customer. Should be of the ISO code form.
     * @param countryCode Country code of the individual.
     * @return this
     */
    @NotNull
    public Address setCountryCode(@NotNull final String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    /**
     * Gets the primary street address of the customer. This is automatically normalized.
     * @return Line 1 of the address.
     */
    @NotNull
    public String getStreet1() {
        return street1;
    }

    /**
     * The second address line typically used for apartment or suite numbers. This is automatically normalized.
     * @return Line 2 of the address.
     */
    @Nullable
    public String getStreet2() {
        return street2;
    }

    /**
     * The city name of the customer. This is automatically normalized.
     * @return City of the address.
     */
    @NotNull
    public String getCity() {
        return city;
    }

    /**
     * The subdivision of the entered country. For instance in the United States, this would be a 'state'.
     * @return The state of the customer.
     */
    @NotNull
    public String getSubdivision() {
        return subdivision;
    }

    /**
     * The postal code, also known as the ZIP code of the address.
     * @return Postal code for the customer.
     */
    @NotNull
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * The country of the customer. Should be of the ISO code form.
     * @return Country code of the customer
     */
    @NotNull
    public String getCountryCode() {
        return countryCode;
    }
}
