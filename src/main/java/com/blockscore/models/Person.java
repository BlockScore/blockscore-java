package com.blockscore.models;

import com.blockscore.models.base.BasicResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Model representing a person's identity.
 * Created by Tony Dieppa on 9/29/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person extends BasicResponse {
    @NotNull
    @JsonProperty("date_of_birth")
    private String mDateOfBirth;

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

    public Person(@NotNull Name name, @NotNull Date dateOfBirth, @NotNull Identification identification
            , @NotNull Address address) {
        mName = name;
        setDateOfBirth(dateOfBirth);
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        mDateOfBirth = format.format(dateOfBirth);
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
}
