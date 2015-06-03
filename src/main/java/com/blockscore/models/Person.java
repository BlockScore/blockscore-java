package com.blockscore.models;

import com.blockscore.common.ValidityStatus;
import com.blockscore.models.base.BasicResponse;
import com.blockscore.models.results.PaginatedResult;
import com.blockscore.net.BlockscoreApiClient;
import com.blockscore.net.BlockscoreRestAdapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model representing a person.
 */
public class Person extends BasicResponse {
  @JsonIgnore
  private BlockscoreRestAdapter restAdapter;

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
  private PersonDetails details;

  @NotNull
  @JsonProperty("question_sets")
  private List<String> questionSetIds;

  private Person() {
    // Restricts access to end user so they must use a Person.Builder to create a Person
  }

  /**
   * Sets the internal REST api adapter needed to complete Blockscore API requests.
   */
  public void setAdapter(BlockscoreRestAdapter restAdapter) {
    this.restAdapter = restAdapter;
  }

  /**
   * Creates a question set with no time limit.
   *
   * @return the new question set
   */
  @NotNull
  public QuestionSet createQuestionSet() {
    return createQuestionSet(0L);
  }

  /**
   * Creates a question set with a set time limit in seconds.
   *
   * @param timeLimit  the time limit
   * @return the new question set
   */
  public QuestionSet createQuestionSet(long timeLimit) {
    Map<String, String> queryOptions = new HashMap<String, String>();
    queryOptions.put("person_id", getId());
    queryOptions.put("time_limit", String.valueOf(timeLimit));

    QuestionSet questionSet = restAdapter.createQuestionSet(queryOptions);
    questionSet.setAdapter(restAdapter);
    return questionSet;
  }

  /**
   * Retrieve a question set you have created.
   *
   * @param questionSetId  Question set ID
   * @return the retrieved question set
   */
  public QuestionSet retrieveQuestionSet(@NotNull final String questionSetId) {
    QuestionSet questionSet = restAdapter.retrieveQuestionSet(questionSetId);
    questionSet.setAdapter(restAdapter);
    return questionSet;
  }

  /**
   * Lists a historical record of all question sets that you have created.
   * The list is displayed in reverse chronological order (newer question sets appear first).
   *
   * @return paginated results of created question sets
   */
  @NotNull
  public PaginatedResult<QuestionSet> listQuestionSet() {
    PaginatedResult<QuestionSet> result = restAdapter.listQuestionSets();

    for (QuestionSet questionSet : result.getData()) {
      questionSet.setAdapter(restAdapter);
    }

    return result;
  }

  /**
   * Gets the first name.
   *
   * @return the first name
   */
  @NotNull
  public String getFirstName() {
    return firstName;
  }

  /**
   * Gets the middle name. (Optional)
   *
   * @return the middle name
   */
  @Nullable
  public String getMiddleName() {
    return middleName;
  }

  /**
   * Gets the last name.
   *
   * @return the last name
   */
  @NotNull
  public String getLastName() {
    return lastName;
  }

  /**
   * Gets the identifying document type.
   *
   * @return the document type
   */
  @NotNull
  public String getDocumentType() {
    return this.documentType;
  }

  /**
   * Gets the identifying document value.
   *
   * @return the document value
   */
  @NotNull
  public String getDocumentValue() {
    return this.documentValue;
  }

  /**
   * Gets the date of birth for this individual.
   *
   * @return the date of birth
   */
  @NotNull
  public Date getDateOfBirth() {
    GregorianCalendar calendar = new GregorianCalendar(birthYear, birthMonth, birthDay);
    return calendar.getTime();
  }

  /**
   * Gets the address for this individual.
   *
   * @return the address
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
   *
   * @return the phone number
   */
  @Nullable
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Gets the IP address for this person. (if available)
   *
   * @return the IP address
   */
  @Nullable
  public String getIpAddress() {
    return ipAddress;
  }

  /**
   * Gets any note data you have associated with the Person.
   *
   * @return the stored note data
   */
  @Nullable
  public String getNote() {
    return note;
  }

  /**
   * Returns either valid or invalid and is the culmination of whether or not the passed
   * in information is valid against various databases and signals.
   *
   * @return true if valid
   */
  public boolean isValid() {
    return ValidityStatus.VALID.isEqualTo(status);
  }

  /**
   * Contains a breakdown of how the status (validity) was determined. It will let you diagnose 
   * problems like address inconsistencies.
   *
   * @return the details breakdown
   */
  @NotNull
  public PersonDetails getDetails() {
    return details;
  }

  /**
   * Gets the question set ids associated with this Person record.
   *
   * @return the question sets
   */
  @NotNull
  public List<String> getQuestionSetIds() {
    return Collections.unmodifiableList(questionSetIds);
  }

  public static class Builder {
    private BlockscoreRestAdapter restAdapter;
    private Map<String, String> queryOptions;

    public Builder(BlockscoreApiClient client) {
      this.restAdapter = client.getAdapter();
      queryOptions = new HashMap<String, String>();
    }

    /**
     * Sets the legal first name of the customer.
     *
     * @param firstName  the legal first name
     * @return this
     */
    @NotNull
    public Builder setFirstName(@NotNull final String firstName) {
      queryOptions.put("name_first", firstName);
      return this;
    }

    /**
     * Sets the  legal middle name of the customer (optional).
     *
     * @param middleName  the legal middle name
     * @return this
     */
    @NotNull
    public Builder setMiddleName(@Nullable final String middleName) {
      queryOptions.put("name_middle", middleName);
      return this;
    }

    /**
     * Sets the legal last name of the customer.
     *
     * @param lastName  the legal last name
     * @return this
     */
    @NotNull
    public Builder setLastName(@NotNull final String lastName) {
      queryOptions.put("name_last", lastName);
      return this;
    }

    /**
     * Sets the identifying document type.
     *
     * @param documentType  the document type
     * @return this
     */
    @NotNull
    public Builder setDocumentType(@NotNull final String documentType) {
      queryOptions.put("document_type", documentType);
      return this;
    }

    /**
     * Sets the identifying document value.
     *
     * @param documentValue  the document value
     * @return this
     */
    @NotNull
    public Builder setDocumentValue(@NotNull final String documentValue) {
      queryOptions.put("document_value", documentValue);
      return this;
    }

    /**
     * Sets the date of birth.
     *
     * @param dateOfBirth  the date of birth
     * @return this
     */
    @NotNull
    public Builder setDateOfBirth(@NotNull final Date dateOfBirth) {
      Calendar calendar = new GregorianCalendar();
      calendar.setTime(dateOfBirth);

      queryOptions.put("birth_day", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
      queryOptions.put("birth_month", String.valueOf(calendar.get(Calendar.MONTH) + 1)); // Months begin at 0
      queryOptions.put("birth_year", String.valueOf(calendar.get(Calendar.YEAR)));

      return this;
    }

    /**
     * Sets the person's address.
     *
     * @param address  the address
     * @return this
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
     * Sets a person's phone number.
     *
     * <p>
     * If you set the phone number, we will use it as an additional
     * 'positive' data point for the consumer. That is, if it is provided, it will help us identify 
     * them, but if we cannot, they will not be penalized.
     *
     * @param phoneNumber  the phone number for this individual.
     * @return this
     */
    @NotNull
    public Builder setPhoneNumber(@Nullable final String phoneNumber) {
      queryOptions.put("phone_number", phoneNumber);
      return this;
    }

    /**
     * Sets a person's IP address.
     *
     * <p>
     * Your customers' IP address can be passed to us for storage
     * purposes. Soon we will be using this information for anti-fraud and verification purposes.
     * With this information we will be able to back-test your verifications when this feature is 
     * released.
     *
     * @param ipAddress  the IP address
     * @return this
     */
    @NotNull
    public Builder setIpAddress(@Nullable final String ipAddress) {
      queryOptions.put("ip_address", ipAddress);
      return this;
    }

    /**
     * You can store additional information about the person here such as your internal system's
     * identifier for this individual. This will allow you to keep track of them.
     *
     * @param note Note to store.
     * @return this
     */
    @NotNull
    public Builder setNote(@Nullable final String note) {
      queryOptions.put("note", note);
      return this;
    }

    /**
     * Creates a new {@code Person}.
     *
     * @return the new person
     */
    public Person create() {
      Person person = restAdapter.createPerson(queryOptions);
      person.setAdapter(restAdapter);
      return person;
    }
  }
}
