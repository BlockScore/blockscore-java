package com.blockscore.models;

import com.blockscore.models.base.BasicResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Candidate model.
 */
public class Candidate extends BasicResponse {
    @NotNull
    @JsonProperty("name_first")
    private String firstName;

    @Nullable
    @JsonProperty("name_middle")
    private String middleName;

    @NotNull
    @JsonProperty("name_last")
    private String lastName;
    
    @Nullable
    @JsonProperty("note")
    private String note;

    @Nullable
    @JsonProperty("ssn")
    private String socialSecurityNumber;

    @Nullable
    @JsonProperty("passport")
    private String passport;

    @Nullable
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @NotNull
    @JsonProperty("address_street1")
    private String addressStreet1;
    
    @Nullable
    @JsonProperty("address_street2")
    private String addressStreet2;
    
    @NotNull
    @JsonProperty("address_city")
    private String addressCity;

    @NotNull
    @JsonProperty("address_subdivision")
    private String addressSubdivision;

    @NotNull
    @JsonProperty("address_postal_code")
    private String addressPostalCode;
    
    @NotNull
    @JsonProperty("address_country_code")
    private String addressCountryCode;

    /**
     * Sets the legal first name of the customer.
     * @param firstName First name.
     * @return this.
     */
    @NotNull
    public Candidate setFirstName(@NotNull final String firstName) {
        this.firstName = firstName;
        return this;
    }

   /**
     * Sets the legal middle name of the customer.
     * @param middleName Middle name.
     * @return this.
     */
    @NotNull
    public Candidate setMiddleName(@NotNull final String middleName) {
        this.middleName = middleName;
        return this;
    }

    /**
     * Sets the legal last name.
     * @param lastName Last name.
     * @return this.
     */
    @NotNull
    public Candidate setLastName(@NotNull final String lastName) {
        this.lastName = lastName;
        return this;
    }
    /**
     * You can store additional information about the candidate here such as your internal system's
     * identifier for this individual. This will allow you to keep track of them.
     * @param note Note to store.
     * @return this.
     */
    @NotNull
    public Candidate setNote(@Nullable final String note) {
        this.note = note;
        return this;
    }
    
    /**
     * Can be either the last 4 digits of the US Social Security Number or the whole SSN.
     * @param ssn SSN to use.
     * @return this.
     */
    @NotNull
    public Candidate setSSN(@Nullable final String ssn) {
        this.socialSecurityNumber = ssn;
        return this;
    }

    /**
     * Sets the passport number of the individual being verified. is only used for verifying non-US customers.
     * @param passport Passport data.
     * @return this.
     */
    @NotNull
    public Candidate setPassport(@Nullable final String passport) {
        this.passport = passport;
        return this;
    }

    /**
     * Set the date of birth of your candidate.
     * @param dateOfBirth Date of birth
     * @return this.
     */
    @NotNull
    public Candidate setDateOfBirth(@Nullable final Date dateOfBirth) {
        if (dateOfBirth == null) {
            return this;
        }
        this.dateOfBirth = new Date(dateOfBirth.getTime());
        return this;
    }

    /**
     * Sets the primary street address for this person.
     * @param address Primary street address.
     */
    public Candidate setAddress(@NotNull final Address address) {
        this.addressStreet1 = address.getStreet1();
        this.addressStreet2 = address.getStreet2();
        this.addressCity = address.getCity();
        this.addressSubdivision = address.getSubdivision();
        this.addressPostalCode = address.getPostalCode();
        this.addressCountryCode = address.getCountryCode();
        return this;
    }

    /**
     * The legal first name of the customer.
     * @return First name.
     */
    @Nullable
    public String getFirstName() {
        return firstName;
    }

    /**
     * The legal middle name of the customer.
     * @return Middle name.
     */
    @Nullable
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Gets the legal last name of the customer.
     * @return Last name.
     */
    @Nullable
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets any note data you have associated with the Candidate.
     * @return Note stored.
     */
    @Nullable
    public String getNote() {
        return note;
    }

    /**
     * Gets either the 4 digits of the US Social Security Number or the whole SSN.
     * @return SSN
     */
    @Nullable
    public String getSSN() {
        return socialSecurityNumber;
    }

    /**
     * The passport number of the individual being verified. is only used for verifying non-US customers.
     * @return Passport number.
     */
    @Nullable
    public String getPassport() {
        return passport;
    }

    /**
     * The date of birth of your candidate.
     * @return Date of birth.
     */
    @Nullable
    public Date getDateOfBirth() {
        if (dateOfBirth == null) {
            return null;
        }
        return new Date(dateOfBirth.getTime());
    }

    /**
     * Gets the primary street address for this person.
     * @return Address.
     */
    @Nullable
    public Address getAddress() {
        Address addressObject = new Address(addressStreet1,
                                            addressStreet2,
                                            addressCity, 
                                            addressSubdivision,
                                            addressPostalCode,
                                            addressCountryCode);
        return addressObject;
    }

}
