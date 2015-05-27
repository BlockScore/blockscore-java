package com.blockscore.models;

import com.blockscore.models.base.BasicResponse;
import com.blockscore.common.ValidityStatus;
import com.blockscore.models.results.PaginatedResult;
import com.blockscore.net.BlockscoreApiClient;
import com.blockscore.net.BlockscoreRetrofitAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * Model representing a person's identity.
 */
public class Person extends BasicResponse {
    private transient BlockscoreRetrofitAPI restAdapter;

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

    private Person() {
        // Restricts access to end user so they must use a Person.Builder to create a Person
    }

    /**
     * Sets the internal REST api adapter.
     */
    public void setAdapter(BlockscoreRetrofitAPI restAdapter) {
        this.restAdapter = restAdapter;
    }

    /**
     * Creates a question set with no time limit.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createQuestionSet(com.blockscore.models.request.QuestionSetRequest)
     * @param request Question set request.
     */
    @NotNull
    public QuestionSet createQuestionSet() {
        return createQuestionSet(0L);
    }

    /**
     * Creates a question set with a set time limit in seconds.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createQuestionSet(com.blockscore.models.request.QuestionSetRequest)
     * @param request Question set request.
     */
    public QuestionSet createQuestionSet(long timeLimit) {
        Map<String, String> queryOptions = new HashMap<String,String>();
        queryOptions.put("person_id", getId());
        queryOptions.put("time_limit", String.valueOf(timeLimit));

        QuestionSet questionSet = restAdapter.createQuestionSet(queryOptions);
        questionSet.setAdapter(restAdapter);
        return questionSet;
    }

    /**
     * This allows you to retrieve a question set you have created.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#retrieveQuestionSet(String)
     * @param questionSetId Question set ID
     */
    public QuestionSet retrieveQuestionSet(@NotNull final String questionSetId) {
        return restAdapter.retrieveQuestionSet(questionSetId);
        //TODO: BUGFIX restadapter that QuestionSet holds
    }

    /**
     * This allows you to retrieve a question set you have created.
     * @see BlockscoreRetrofitAPI#listQuestionSets()
     */
    @NotNull
    public PaginatedResult<QuestionSet> listQuestionSet() {
        return restAdapter.listQuestionSets();
        //TODO: BUGFIX restadapter that QuestionSet holds
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

    public static class Builder {
        private transient BlockscoreRetrofitAPI restAdapter; // TODO: Discover if transient is neccesary
        private transient Map<String, String> queryOptions;

        public Builder(BlockscoreApiClient client) {
            this.restAdapter = client.getAdapter();
            queryOptions = new HashMap<String, String>();
        }

        /**
         * Sets the legal first name of the customer.
         * @param firstName First name.
         */
        @NotNull
        public Builder setFirstName(@NotNull final String firstName) {
            queryOptions.put("name_first", firstName);
            return this;
        }

        /**
         * Sets the  legal middle name of the customer (optional).
         * @param middle Middle name.
         */
        @NotNull
        public Builder setMiddleName(@Nullable final String middleName) {
            queryOptions.put("name_middle", middleName);
            return this;
        }

        /**
         * Sets the legal last name of the customer.
         * @param last Last name.
         */
        @NotNull
        public Builder setLastName(@NotNull final String lastName) {
            queryOptions.put("name_last", lastName);
            return this;
        }

        /**
         * Sets the identifying document type.
         * @param documentType The Document type.
         */
        @NotNull
        public Builder setDocumentType(@NotNull final String documentType) {
            queryOptions.put("document_type", documentType);
            return this;
        }

        /**
         * Sets the identifying document value.
         * @param documentValue The Document value.
         */
        @NotNull
        public Builder setDocumentValue(@NotNull final String documentValue) {
            queryOptions.put("document_value", documentValue);
            return this;
        }

        /**
         * Sets the date of birth
         * @param dateOfBirth The date of birth.
         */
        @NotNull
        public Builder setDateOfBirth(@NotNull final Date dateOfBirth) {
            queryOptions.put("birth_day", String.valueOf(dateOfBirth.getDay()));
            queryOptions.put("birth_month", String.valueOf(dateOfBirth.getMonth()));
            queryOptions.put("birth_year", String.valueOf(dateOfBirth.getYear()));
            return this;
        }

        /**
         * Sets the person's address.
         * @param address The address.
         */
        @NotNull
        public Builder setAddress(@NotNull final Address address) {
            queryOptions.put("address_street1", address.getStreet1());
            queryOptions.put("address_street2", address.getStreet2());
            queryOptions.put("address_city", address.getCity());
            queryOptions.put("address_subdivision", address.getSubdivision());
            queryOptions.put("address_postal_code", address.getPostalCode());
            queryOptions.put("address_country_code", address.getCountryCode());
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
        public Builder setPhoneNumber(@Nullable final String phoneNumber) {
            queryOptions.put("phone_number", phoneNumber);
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
        public Builder setIPAddress(@Nullable final String ipAddress) {
            queryOptions.put("ip_address", ipAddress);
            return this;
        }

        /**
         * You can store additional information about the person here such as your internal system's
         * identifier for this individual. This will allow you to keep track of them.
         * @param note Note to store.
         * @return this.
         */
        @NotNull
        public Builder setNote(@Nullable final String note) {
            queryOptions.put("note", note);
            return this;
        }


        /**
         * Creates a new Person.
         */
        public Person create() {
            Person person = restAdapter.createPerson(queryOptions);
            person.setAdapter(restAdapter);
            return person;
        }
    }
}
