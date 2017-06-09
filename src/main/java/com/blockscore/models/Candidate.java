package com.blockscore.models;

import com.blockscore.models.base.BasicResponse;
import com.blockscore.net.BlockscoreApiClient;
import com.blockscore.net.BlockscoreRestAdapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The candidate model.
 */
public class Candidate extends BasicResponse {
  @JsonIgnore
  private BlockscoreRestAdapter restAdapter;

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

  protected Candidate() {
    // Restricts access to end user so they must use a Candidate.Builder to create a Person
  }

  /**
   * Updates this candidate.
   *
   * @return this
   */
  public Candidate save() {
    restAdapter.updateCandidate(getId(), this);
    return this;
  }

  /**
   * Deletes this candidate.
   */
  public void delete() {
    restAdapter.deleteCandidate(getId());
  }

  /**
   * Returns a complete revision history of a candidate's edits. This allows you to maintain a full
   * audit trail of when and how you update a client's profile over time. The latest revision is
   * presented at the top of the list, and the original is at the end of the list.
   * @return the list of candidates
   */
  public List<Candidate> getRevisionHistory() {
    List<Candidate> candidates = restAdapter.getCandidateHistory(getId());

    for (Candidate candidate : candidates) {
      candidate.setAdapter(restAdapter);
    }

    return Collections.unmodifiableList(candidates);
  }

  /**
   * Retrieve all historical watchlist hits for this candidate.
   * @return the paginated historical watchlist hits
   */
  public PaginatedResult<WatchlistHit> getPastHits() {
    return restAdapter.getCandidateHits(getId());
  }

  /**
   * Performs a watchlist search for this candidate with default search options.
   * Results may be of entities or individuals.
   *
   * @return the paginated watchlist hit results
   */
  public PaginatedResult<WatchlistHit> searchWatchlists() {
    return searchWatchlists(null, null);
  }

  /**
   * Performs a watchlist search for this candidate with a specified similarity threshold.
   * Results may be of entities or individuals.
   *
   * @param similarityThreshold  the tolerated similarity threshold
   * @return the paginated watchlist hit resultss
   */
  public PaginatedResult<WatchlistHit> searchWatchlists(Double similarityThreshold) {
    return searchWatchlists(null, similarityThreshold);
  }

  /**
   * Performs a watchlist search for this candidate with a specified entity type.
   * The default similarity threshold is used.
   *
   * @param entityType  the type of entity
   * @return the paginated watchlist hit results
   */
  public PaginatedResult<WatchlistHit> searchWatchlists(EntityType entityType) {
    return searchWatchlists(entityType, null);
  }

  /**
   * Performs a watchlist search for this candidate.
   *
   * @param entityType  the type of entity
   * @param similarityThreshold  the accepted threshold of similarity
   * @return the paginated watchlist hit results
   */
  public PaginatedResult<WatchlistHit> searchWatchlists(EntityType entityType, Double similarityThreshold) {
    Map<String, String> queryOptions = new HashMap<String, String>();
    queryOptions.put("candidate_id", getId());

    if (entityType != null) {
      queryOptions.put("match_type", String.valueOf(entityType));
    }

    if (similarityThreshold != null) {
      queryOptions.put("similarity_threshold", String.valueOf(similarityThreshold));
    }

    WatchlistSearchResults results = restAdapter.searchWatchlists(queryOptions);

    //wrap the result's data with PaginatedResult to make v5.0 transition simpler
    return new PaginatedResult<WatchlistHit>(results.getMatches(), results.getCount(), false);
  }

  /**
   * Sets the legal first name of the customer.
   *
   * @param firstName the first name
   * @return this
   */
  @NotNull
  public Candidate setFirstName(@NotNull final String firstName) {
    this.firstName = firstName;
    return this;
  }

   /**
   * Sets the legal middle name of the customer.
  *
   * @param middleName the middle name
   * @return this
   */
  @NotNull
  public Candidate setMiddleName(@NotNull final String middleName) {
    this.middleName = middleName;
    return this;
  }

  /**
   * Sets the legal last name.
   *
   * @param lastName the last name
   * @return this
   */
  @NotNull
  public Candidate setLastName(@NotNull final String lastName) {
    this.lastName = lastName;
    return this;
  }
  /**
   * You can store additional information about the candidate here such as your internal system's
   * identifier for this individual. This will allow you to keep track of them.
   *
   * @param note the additional information to store.]
   * @return this
   */
  @NotNull
  public Candidate setNote(@Nullable final String note) {
    this.note = note;
    return this;
  }

  /**
   * Can be either the last 4 digits of the US Social Security Number or the whole SSN.
   *
   * @param ssn  the SSN to use
   * @return this
   */
  @NotNull
  public Candidate setSsn(@Nullable final String ssn) {
    this.socialSecurityNumber = ssn;
    return this;
  }

  /**
   * Sets the passport number of the individual being verified. is only used for verifying non-US customers.
   *
   * @param passport  the passport number
   * @return this
   */
  @NotNull
  public Candidate setPassport(@Nullable final String passport) {
    this.passport = passport;
    return this;
  }

  /**
   * Set the date of birth of your candidate.
   *
   * @param dateOfBirth  the date of birth
   * @return this
   */
  @NotNull
  public Candidate setDateOfBirth(@Nullable final Date dateOfBirth) {
    if (dateOfBirth == null) {
      this.dateOfBirth = null;
      return this;
    }

    this.dateOfBirth = new Date(dateOfBirth.getTime());
    return this;
  }

  /**
   * Sets the primary street address for this person.
   *
   * @param address  the primary street address
   * @return this
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
   *
   * @return the first name
   */
  @Nullable
  public String getFirstName() {
    return firstName;
  }

  /**
   * The legal middle name of the customer.
   *
   * @return the middle name
   */
  @Nullable
  public String getMiddleName() {
    return middleName;
  }

  /**
   * Gets the legal last name of the customer.
   *
   * @return the last name
   */
  @Nullable
  public String getLastName() {
    return lastName;
  }

  /**
   * Gets any note data you have associated with the Candidate.
   *
   * @return the stored note
   */
  @Nullable
  public String getNote() {
    return note;
  }

  /**
   * Gets either the 4 digits of the US Social Security Number or the whole SSN.
   *
   * @return the SSN
   */
  @Nullable
  public String getSsn() {
    return socialSecurityNumber;
  }

  /**
   * The passport number of the individual being verified. is only used for verifying non-US customers.
   *
   * @return the passport number
   */
  @Nullable
  public String getPassport() {
    return passport;
  }

  /**
   * The date of birth of your candidate.
   *
   * @return the date of birth
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
   *
   * @return the address
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

  /**
   * Sets the internal REST api adapter needed to complete Blockscore API requests.
   *
   * @param restAdapter  the REST adapter
   */
  public void setAdapter(BlockscoreRestAdapter restAdapter) {
    this.restAdapter = restAdapter;
  }

  /**
   * The builder used for constructing a {@link Candidate}.
   */
  public static class Builder {
    private BlockscoreRestAdapter restAdapter;
    private Candidate candidate;

    public Builder(BlockscoreApiClient client) {
      this.restAdapter = client.getAdapter();
      candidate = new Candidate();
    }

     /**
      * Sets the legal first name of the customer.
      *
      * @param firstName the first name
      * @return this
      */
    @NotNull
    public Builder setFirstName(@NotNull final String firstName) {
      candidate.setFirstName(firstName);
      return this;
    }

    /**
     * Sets the legal middle name of the candidate.
     *
     * @param middleName the middle name
     * @return this
     */
    @NotNull
    public Builder setMiddleName(@NotNull final String middleName) {
      candidate.setMiddleName(middleName);
      return this;
    }

    /**
     * Sets the legal last name of the candidate.
     *
     * @param lastName the last name
     * @return this
     */
    @NotNull
    public Builder setLastName(@NotNull final String lastName) {
      candidate.setLastName(lastName);
      return this;
    }

    /**
     * You can store additional information about the candidate here such as your internal system's
     * identifier for this individual. This will allow you to keep track of them.
     *
     * @param note the additional information to store
     * @return this
     */
    @NotNull
    public Builder setNote(@Nullable final String note) {
      candidate.setNote(note);
      return this;
    }

    /**
     * Can be either the last 4 digits of the US Social Security Number or the whole SSN.
     *
     * @param ssn the SSN to use
     * @return this
     */
    @NotNull
    public Builder setSsn(@Nullable final String ssn) {
      candidate.setSsn(ssn);
      return this;
    }

    /**
     * Sets the passport number of the individual being verified. is only used for verifying non-US customers.
     *
     * @param passport the passport data
     * @return this
     */
    @NotNull
    public Builder setPassport(@Nullable final String passport) {
      candidate.setPassport(passport);
      return this;
    }

    /**
     * Set the date of birth of your candidate.
     *
     * @param dateOfBirth  the date of birth
     * @return this
     */
    @NotNull
    public Builder setDateOfBirth(@Nullable final Date dateOfBirth) {
      candidate.setDateOfBirth(dateOfBirth);
      return this;
    }

    /**
     * Sets the primary street address for this person.
     *
     * @param address the address
     * @return this
     */
    public Builder setAddress(@NotNull final Address address) {
      candidate.setAddress(address);
      return this;
    }

    /**
     * Creates a new {@link Candidate}.
     *
     * @return this
     */
    public Candidate create() {
      candidate = restAdapter.createCandidate(candidate);
      candidate.setAdapter(restAdapter);
      return candidate;
    }
  }
}
