package com.blockscore.models.results;

import com.blockscore.common.EntityType;
import com.blockscore.models.Address;
import com.blockscore.models.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Model representing a watchlist hit.
 */
public class WatchlistHit {
  @NotNull
  @JsonProperty("watchlist_name")
  private String watchlist;

  @NotNull
  @JsonProperty("entry_type")
  private String entryType;

  @Nullable
  @JsonProperty("matching_info")
  private String[] matchingInfo;

  @NotNull
  @JsonProperty("confidence")
  private Double confidence;

  @Nullable
  @JsonProperty("url")
  private String url;

  @Nullable
  @JsonProperty("notes")
  private String notes;

  @Nullable
  @JsonProperty("title")
  private String title;

  @NotNull
  @JsonProperty("name_full")
  private String fullName;

  @Nullable
  @JsonProperty("alternate_names")
  private String alternateNames;

  @Nullable
  @JsonProperty("date_of_birth")
  private Date dateOfBirth;

  @Nullable
  @JsonProperty("passport")
  private String passportNumbers;

  @Nullable
  @JsonProperty("ssn")
  private String ssn;

  @Nullable
  @JsonProperty("address_street1")
  private String addressStreet1;
  
  @Nullable
  @JsonProperty("address_street2")
  private String addressStreet2;
  
  @Nullable
  @JsonProperty("address_city")
  private String addressCity;

  @Nullable
  @JsonProperty("address_state")
  private String addressState;

  @Nullable
  @JsonProperty("address_postal_code")
  private String addressPostalCode;
  
  @Nullable
  @JsonProperty("address_country_code")
  private String addressCountryCode;

  @Nullable
  @JsonProperty("address_raw")
  private String rawAddress;

  @NotNull
  @JsonProperty("names")
  private List<NameResult> names;

  @NotNull
  @JsonProperty("births")
  private List<BirthRange> births;

  @NotNull
  @JsonProperty("documents")
  private List<Document> documents;

  @NotNull
  @JsonProperty("addresses")
  private List<Address> addresses;

  /**
   * Gets the watchlist on which this match took place.
   *
   * @return the watchlist
   */
  @NotNull
  public String getWatchlist() {
    return watchlist;
  }

  /**
   * Returns the entry type of this watchlist hit.
   *
   * @return the entry type
   */
  @NotNull
  public EntityType getEntryType() {
    return EntityType.toEnum(entryType);
  }

  /**
   * Gets a list of elements in your request that match this watchlist hit.
   *
   * @return an array of strings containing the matching info
   */
  @NotNull
  public String[] getMatchingInfo() {
    if (matchingInfo == null) {
      return new String[0];
    }

    return Arrays.copyOf(matchingInfo, matchingInfo.length);
  }

  /**
   * Gets the confidence rating (between 0 and 1.0).
   *
   * @return the confidence value
   */
  public double getConfidence() {
    return confidence;
  }

  /**
   * Returns the url for this candidate.
   *
   * @return the url
   */
  @Nullable
  public String getUrl() {
    return url;
  }

  /**
   * Returns the notes for this candidate.
   *
   * @return the notes data
   */
  @Nullable
  public String getNotes() {
    return notes;
  }

  /**
   * Returns the title for this candidate.
   *
   * @return the title
   */
  @Nullable
  public String getTitle() {
    return title;
  }

  /**
   * Returns the full name for this candidate.
   *
   * @return the full name
   */
  @Nullable
  public String getName() {
    return fullName;
  }

  /**
   * Returns the set of alternate names of a candidate.
   *
   * @return the array of alternate names, not null
   */
  @NotNull
  public String getAlternateNames() {
    return alternateNames;
  }

  /**
   * Returns the candidate's date of birth.
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
   * Gets the ssn for this candidate.
   *
   * @return the ssn
   */
  @Nullable
  public String getSsn() {
    return ssn;
  }

  /**
   * Gets the list of passport numbers for a candidate.
   *
   * @return the array of passport numbers
   */
  @Nullable
  public String getPassports() {
    return passportNumbers;
  }

  /**
   * Gets the address of the candidate.
   *
   * @return the address
   */
  @Nullable
  public Address getAddress() {
    return new Address(addressStreet1,
                       addressStreet2,
                       addressCity,
                       addressState,
                       addressPostalCode,
                       addressCountryCode);
  }

  /**
   * Returns the address of this candidate in raw form.
   *
   * @return the address in raw form
   */
  @Nullable
  public String getRawAddress() {
    return rawAddress;
  }

  /**
   * Returns the list of names matched against this candidate.
   *
   * @return the list of names
   */
  @NotNull
  public List<NameResult> getNames() {
    return Collections.unmodifiableList(names);
  }

  /**
   * Returns the list of date of birth ranges matched against this candidate.
   *
   * @return the list of birth ranges
   */
  @NotNull
  public List<BirthRange> getBirths() {
    return Collections.unmodifiableList(births);
  }

  /**
   * Returns the list of documents matched against this candidate.
   *
   * @return the list of documents
   */
  @NotNull
  public List<Document> getDocuments() {
    return Collections.unmodifiableList(documents);
  }

  /**
   * Gets the addresses from the watchlist.
   *
   * @return the list of addresses
   */
  @NotNull
  public List<Address> getAddresses() {
    return Collections.unmodifiableList(addresses);
  }
}
