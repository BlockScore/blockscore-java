package com.blockscore.models;

import com.blockscore.models.base.BasicResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

/**
 * Watch list candidate model.
 * Created by tealocean on 9/30/14.
 */
public class WatchlistCandidate extends BasicResponse {
    @Nullable
    @JsonProperty("note")
    private String mNote;

    @Nullable
    @JsonProperty("ssn")
    private String mSSN;

    @Nullable
    @JsonProperty("passport")
    private String mPassport;

    @Nullable
    @JsonProperty("date_of_birth")
    private Date mDateOfBirth;

    @NotNull
    @JsonProperty("first_name")
    private String mFirstName;

    @Nullable
    @JsonProperty("middle_name")
    private String mMiddleName;

    @NotNull
    @JsonProperty("last_name")
    private String mLastName;

    @Nullable
    @JsonProperty("address_street1")
    private String mStreet1;

    @Nullable
    @JsonProperty("address_street2")
    private String mStreet2;

    @Nullable
    @JsonProperty("address_city")
    private String mCity;

    @Nullable
    @JsonProperty("address_state")
    private String mState;

    @Nullable
    @JsonProperty("address_postal_code")
    private String mPostalCode;

    @Nullable
    @JsonProperty("address_country_code")
    private String mCountryCode;

    /**
     * An additional field which can be used for arbitrary storage. is typically used for
     * storing your internal identifiers for customer.
     * @return Note stored.
     */
    @Nullable
    public String getNote() {
        return mNote;
    }

    /**
     * Sets an additional field which can be used for arbitrary storage. is typically used for
     * storing your internal identifiers for customer.
     * @param note Note to store.
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setNote(@Nullable final String note) {
        mNote = note;
        return this;
    }

    /**
     * Gets either the 4 digits of the US Social Security Number or the whole SSN.
     * @return SSN
     */
    @Nullable
    public String getSSN() {
        return mSSN;
    }

    /**
     * Can be either the last 4 digits of the US Social Security Number or the whole SSN.
     * @param ssn SSN to use.
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setSSN(@Nullable final String ssn) {
        mSSN = ssn;
        return this;
    }

    /**
     * The passport number of the individual being verified. is only used for verifying non-US customers.
     * @return Passport number.
     */
    @Nullable
    public String getPassport() {
        return mPassport;
    }

    /**
     * Sets the passport number of the individual being verified. is only used for verifying non-US customers.
     * @param passport Passport data.
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setPassport(@Nullable final String passport) {
        mPassport = passport;
        return this;
    }

    /**
     * The date of birth of your candidate.
     * @return Date of birth.
     */
    @Nullable
    public Date getDateOfBirth() {
        if (mDateOfBirth == null) {
            return null;
        }
        return new Date(mDateOfBirth.getTime());
    }

    /**
     * Set the date of birth of your candidate.
     * @param dateOfBirth Date of birth
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setDateOfBirth(@Nullable final Date dateOfBirth) {
        if (dateOfBirth == null) {
            return this;
        }
        mDateOfBirth = new Date(dateOfBirth.getTime());
        return this;
    }

    /**
     * The legal first name of the customer.
     * @return First name.
     */
    @NotNull
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * Sets the legal first name of the customer.
     * @param firstName First name.
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setFirstName(@NotNull final String firstName) {
        mFirstName = firstName;
        return this;
    }

    /**
     * The legal middle name of the customer.
     * @return Middle name.
     */
    @Nullable
    public String getMiddleName() {
        return mMiddleName;
    }

    /**
     * Sets the legal middle name of the customer.
     * @param middleName Middle name.
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setMiddleName(@NotNull final String middleName) {
        mMiddleName = middleName;
        return this;
    }

    /**
     * Gets the legal last name of the customer.
     * @return Last name.
     */
    @NotNull
    public String getLastName() {
        return mLastName;
    }

    /**
     * Sets the legal last name.
     * @param lastName Last name.
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setLastName(@NotNull final String lastName) {
        mLastName = lastName;
        return this;
    }

    /**
     * The primary street address of the customer. This is automatically normalized.
     * @return Street address.
     */
    @Nullable
    public String getStreet1() {
        return mStreet1;
    }

    /**
     * Sets the primary street address of the customer. This is automatically normalized.
     * @param street1 Street address.
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setStreet1(@NotNull final String street1) {
        mStreet1 = street1;
        return this;
    }

    /**
     * The second address line typically used for apartment or suite numbers. This is automatically normalized.
     * @return Street address (line 2)
     */
    @Nullable
    public String getStreet2() {
        return mStreet2;
    }

    /**
     * Sets the second address line typically used for apartment or suite numbers. This is automatically normalized.
     * @param street2 Street address (line 2)
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setStreet2(@NotNull final String street2) {
        mStreet2 = street2;
        return this;
    }

    /**
     * The city name of the customer. This is automatically normalized.
     * @return City name.
     */
    @Nullable
    public String getCity() {
        return mCity;
    }

    /**
     * Sets the city name of the customer. This is automatically normalized.
     * @param city City name.
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setCity(@NotNull final String city) {
        mCity = city;
        return this;
    }

    /**
     * Gets the state of the customer. Should be of the FIPS code form. For example California would be CA.
     * @return State
     */
    @Nullable
    public String getState() {
        return mState;
    }

    /**
     * Sets the state of the customer. Should be of the FIPS code form. For example California would be CA.
     * @param state State
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setState(@Nullable final String state) {
        mState = state;
        return this;
    }

    /**
     * Gets the postal code.
     * @return Postal code.
     */
    @Nullable
    public String getPostalCode() {
        return mPostalCode;
    }

    /**
     * Sets the postal code.
     * @param postalCode Postal code.
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setPostalCode(@Nullable final String postalCode) {
        mPostalCode = postalCode;
        return this;
    }

    /**
     * The country of the customer. Should be of the ISO code form.
     * @return Country code.
     */
    @Nullable
    public String getCountryCode() {
        return mCountryCode;
    }

    /**
     * Sets the country of the customer. Should be of the ISO code form.
     * @param countryCode Country code.
     * @return this.
     */
    @NotNull
    public WatchlistCandidate setCountryCode(@Nullable final String countryCode) {
        mCountryCode = countryCode;
        return this;
    }
}
