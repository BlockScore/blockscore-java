package com.blockscore.models.results;

import com.blockscore.models.Address;
import com.blockscore.models.BirthRange;
import com.blockscore.models.Document;
import com.blockscore.common.EntityType;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
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

    @Nullable//?
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

    @Nullable
    @JsonProperty("names")
    private List<NameResult> names;

    @Nullable
    @JsonProperty("births")
    private List<BirthRange> births;

    @Nullable
    @JsonProperty("documents")
    private List<Document> documents;

    //?
    @JsonProperty("addresses")
    private List<Address> addresses;

    /**
     * Gets the watchlist on which this match took place.
     */
    @NotNull
    public String getWatchlist() {
        return watchlist;
    }

    @NotNull
    public EntityType getEntryType() {
      return EntityType.toEnum(entryType);
    }

    /**
     * Gets a list of elements in your request that match this watchlist hit.
     */
    @NotNull
    public String[] getMatchingInfo() {
        if (matchingInfo == null) {
            return new String[0];
        }

        return Arrays.copyOf(matchingInfo, matchingInfo.length);
    }

    /**
     * Gets the confidence rating.
     */
    public double getConfidence() {
        return confidence;
    }

    @Nullable
    public String getUrl() {
      return url;
    }

    @Nullable
    public String getNotes() {
      return notes;
    }

    @Nullable
    public String getTitle() {
      return title;
    }

    @Nullable
    public String getName() {
      return fullName;
    }

    @Nullable
    public String[] getAlternateNames() {
      if(alternateNames == null)
        return null;

      if(!alternateNames.contains(";"))
        return new String[] { alternateNames };

      String[] namesArray = alternateNames.split(";");
      return trimArray(namesArray);
    }

    @Nullable
    public Date getDateOfBirth() {
      if (dateOfBirth == null) {
          return null;
      }

      return new Date(dateOfBirth.getTime());
    }

    @Nullable
    public String getSSN() {
      return ssn;
    }

    @Nullable
    public String[] getPassports() {
      if(passportNumbers == null)
        return null;

      if(!passportNumbers.contains(";"))
        return new String[] { passportNumbers };

      return passportNumbers.split(";");
    }

    @Nullable
    public Address getAddress() {
        Address addressObject = new Address(addressStreet1,
                                            addressStreet2,
                                            addressCity, 
                                            addressState, // TODO: check if state is appropriate for subdivision
                                            addressPostalCode,
                                            addressCountryCode);
        return addressObject;
    }

    @Nullable
    public String getRawAddress() {
      return rawAddress;
    }

    @Nullable
    public List<NameResult> getNames() {
      return names;
    }

    @Nullable
    public List<BirthRange> getBirths() {
      return births;
    }

    @Nullable
    public List<Document> getDocuments() {
      return documents;
    }

    /**
     * Gets the addresses from the watchlist.
     */
    @Nullable
    public List<Address> getAddresses() {
      return addresses;
    }

    private String[] trimArray(String[] arr) {
      for(int i = 0; i < arr.length; ++i)
        arr[i] = arr[i].trim();

      return arr;
    }
}
