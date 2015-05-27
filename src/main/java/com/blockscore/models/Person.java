package com.blockscore.models;

import com.blockscore.models.base.BasicResponse;
import com.blockscore.common.ValidityStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Model representing a person's identity.
 */
public class Person extends BasicResponse {
    // Request Fields
    @NotNull
    @JsonProperty("name_first")
    private String firstName;

    @Nullable
    @JsonProperty("name_middle")
    private String middleName;

    @NotNull
    @JsonProperty("name_last")
    private String lastName;

    @NotNull
    @JsonProperty("document_type")
    private String documentType;

    @NotNull
    @JsonProperty("document_value")
    private String documentValue;

    @NotNull
    @JsonProperty("birth_day")
    private Integer birthDay;

    @NotNull
    @JsonProperty("birth_month")
    private Integer birthMonth;

    @NotNull
    @JsonProperty("birth_year")
    private Integer birthYear;

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

    @Nullable
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Nullable
    @JsonProperty("ip_address")
    private String ipAddress;

    @NotNull
    @JsonProperty("note")
    private String note;
    @NotNull

    // Response Fields
    @JsonProperty("status")
    private String status;

    @NotNull
    @JsonProperty("details")
    private Details details;

    @NotNull
    @JsonProperty("question_sets")
    private List<String> questionSets;

    public Person() { //TODO: privatize & use a Builder
        //No initialization.
    }

    /**
     * Sets the legal first name of the customer.
     * @param firstName First name.
     */
    @NotNull
    public Person setFirstName(@NotNull final String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Sets the  legal middle name of the customer (optional).
     * @param middle Middle name.
     */
    @NotNull
    public Person setMiddleName(@Nullable final String middleName) {
        this.middleName = middleName;
        return this;
    }

    /**
     * Sets the legal last name of the customer.
     * @param last Last name.
     */
    @NotNull
    public Person setLastName(@NotNull final String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Sets the identifying document type.
     * @param documentType The Document type.
     */
    @NotNull
    public Person setDocumentType(@NotNull final String documentType) {
        this.documentType = documentType;
        return this;
    }

    /**
     * Sets the identifying document value.
     * @param documentValue The Document value.
     */
    @NotNull
    public Person setDocumentValue(@NotNull final String documentValue) {
        this.documentValue = documentValue;
        return this;
    }

    /**
     * Sets the date of birth
     * @param dateOfBirth The date of birth.
     */
    @NotNull
    public Person setDateOfBirth(@NotNull final Date dateOfBirth) {
        this.birthDay = dateOfBirth.getDay();
        this.birthMonth = dateOfBirth.getMonth();
        this.birthYear = dateOfBirth.getYear();
        return this;
    }

    /**
     * Sets the person's address.
     * @param address The address.
     */
    @NotNull
    public Person setAddress(@NotNull final Address address) {
        this.addressStreet1 = address.getStreet1();
        this.addressStreet2 = address.getStreet2();
        this.addressCity = address.getCity();
        this.addressSubdivision = address.getSubdivision();
        this.addressPostalCode = address.getPostalCode();
        this.addressCountryCode = address.getCountryCode();
        return this;
    }


    /**
     * Sets a person's phone number. If you set the phone number, we will use it as an additional
     * 'positive' data point for the consumer. That is, if it is provided, it will help us identify 
     * them, but if we cannot, they will not be penalized.
     * @param phoneNumber Phone number for this individual.
     * @return this.
     */
    @NotNull
    public Person setPhoneNumber(@Nullable final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Sets a person's IP address. Your customers' IP address can be passed to us for storage
     * purposes. Soon we will be using this information for anti-fraud and verification purposes.
     * With this information we will be able to back-test your verifications when this feature is 
     * released.
     * @param ipAddress IP address to associate with this individual.
     * @return this.
     */
    @NotNull
    public Person setIPAddress(@Nullable final String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    /**
     * You can store additional information about the person here such as your internal system's
     * identifier for this individual. This will allow you to keep track of them.
     * @param note Note to store.
     * @return this.
     */
    @NotNull
    public Person setNote(@Nullable final String note) {
        this.note = note;
        return this;
    }

    /**
     * Gets the first name.
     * @return First name.
     */
    @NotNull
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the middle name. (Optional)
     * @return Middle name.
     */
    @Nullable
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Gets the last name.
     * @return Last name.
     */
    @NotNull
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the identifying document type.
     * @param documentType The Document type.
     */
    @NotNull
    public String getDocumentType() {
        return this.documentType;
    }

    /**
     * Gets the identifying document value.
     * @param documentValue The Document value.
     */
    @NotNull
    public String getDocumentValue() {
        return this.documentValue;
    }

    /**
     * Gets the date of birth for this individual.
     * @return Date of birth.
     */
    @NotNull
    public Date getDateOfBirth() {
        GregorianCalendar calendar = new GregorianCalendar(birthYear, birthMonth, birthDay);
        return calendar.getTime();
    }

    /**
     * Gets the address for this individual.
     * @return Address.
     */
    @NotNull
    public Address getAddress() {
        Address addressObject = new Address(addressStreet1,
                                            addressStreet2,
                                            addressCity, 
                                            addressSubdivision,
                                            addressPostalCode,
                                            addressCountryCode);
        return addressObject;
    }

    /**
     * Gets phone number of the individual (if available).
     * @return Phone number
     */
    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the IP address for this person. (if available)
     * @return IP address.
     */
    @Nullable
    public String getIPAddress() {
        return ipAddress;
    }

    /**
     * Gets any note data you have associated with the Person.
     * @return Note stored.
     */
    @Nullable
    public String getNote() {
        return note;
    }

    /**
     * Returns either valid or invalid and is the culmination of whether or not the passed
     * in information is valid against various databases and signals.
     * @return True if valid.
     */
    public boolean isValid() {
        return ValidityStatus.VALID.isEqualTo(status);
    }

    /**
     * Contains a breakdown of how the status (validity) was determined. It will let you diagnose 
     * problems like address inconsistencies.
     * @return Details breakdown.
     */
    @NotNull
    public Details getDetails() {
        return details;
    }

    /**
     * Gets the question set ids associated with this Person record.
     * @return Question sets.
     */
    @NotNull
    public List<String> getQuestionSets() {
        return questionSets;
    }
}
