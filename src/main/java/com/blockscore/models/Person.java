package com.blockscore.models;

import com.blockscore.models.base.BasicResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

/**
 * Model representing a person's identity.
 * Created by Tony Dieppa on 9/29/14.
 */
public class Person extends BasicResponse {
    @Nullable
    @JsonProperty("date_of_birth")
    private Date mDateOfBirth;

    @NotNull
    @JsonProperty("identification")
    private Identification mIdentification;

    @NotNull
    @JsonProperty("name")
    private Name mName;

    @NotNull
    @JsonProperty("address")
    private Address mAddress;

    @Nullable
    @JsonProperty("phone_number")
    private String mPhoneNumber;

    @Nullable
    @JsonProperty("ip_address")
    private String mIPAddress;

    public Person() {
        //No initialization.
    }

    public Person(@NotNull Name name, @Nullable Date dateOfBirth, @NotNull Identification identification
            , @NotNull Address address) {
        mName = name;
        mDateOfBirth = dateOfBirth;
        mIdentification = identification;
        mAddress = address;
    }

    /**
     * Set the date of birth for this individual.
     * @param dateOfBirth Date of birth
     * @return this.
     */
    @NotNull
    public Person setDateOfBirth(@NotNull final Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    /**
     * Sets the identification to be used for this individual.
     * @param identification Identification (SSN or Passport)
     * @return this.
     */
    @NotNull
    public Person setIdentification(@NotNull final Identification identification) {
        mIdentification = identification;
        return this;
    }

    /**
     * Sets a person's address.
     * @param address Address to use.
     * @return this.
     */
    @NotNull
    public Person setAddress(@NotNull final Address address) {
        mAddress = address;
        return this;
    }

    /**
     * Sets a person's phone number.
     * @param phoneNumber Phone number for this individual.
     * @return this.
     */
    @NotNull
    public Person setPhoneNumber(@Nullable final String phoneNumber) {
        mPhoneNumber = phoneNumber;
        return this;
    }

    /**
     * Sets a person's IP address.
     * @param ipAddress IP address to associate with this individual.
     * @return this.
     */
    @NotNull
    public Person setIPAddress(@Nullable final String ipAddress) {
        mIPAddress = ipAddress;
        return this;
    }

    /**
     * Sets the person's name.
     * @param name Name.
     * @return this.
     */
    @NotNull
    public Person setName(@NotNull final Name name) {
        mName = name;
        return this;
    }

    /**
     * Get the date of birth for this individual.
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
     * Gets the identification for this person.
     * @return Identification used.
     */
    @NotNull
    public Identification getIdentification() {
        return mIdentification;
    }

    /**
     * Gets the name of this individual.
     * @return Name.
     */
    @NotNull
    public Name getName() {
        return mName;
    }

    /**
     * Gets the address for this individual.
     * @return Address.
     */
    @NotNull
    public Address getAddress() {
        return mAddress;
    }

    /**
     * Gets phone number of the individual (if available)
     * @return Phone number
     */
    @Nullable
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    /**
     * Gets the IP address for this person. (if available)
     * @return IP address.
     */
    @Nullable
    public String getIPAddress() {
        return mIPAddress;
    }
}
