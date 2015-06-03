package com.blockscore.models;

import com.blockscore.common.MatchRating;
import com.blockscore.common.ResultStrength;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Details model used by Person.
 */
public class PersonDetails {
  @Nullable
  @JsonProperty("address")
  private String addressMatch;

  @Nullable
  @JsonProperty("address_risk")
  private String addressRisk;

  @Nullable
  @JsonProperty("identification")
  private String identficationMatch;

  @Nullable
  @JsonProperty("date_of_birth")
  private String dateOfBirthMatch;

  @NotNull
  @JsonProperty("ofac")
  private String ofacMatch;

  @NotNull
  @JsonProperty("pep")
  private String pepMatch;

  /**
   * Assesses the address match strength.
   *
   * @return the match strength
   */
  @Nullable
  public MatchRating getAddressMatchDetails() {
    return MatchRating.toEnum(addressMatch);
  }

  /**
   * Gets the risk of this particular address.
   *
   * @return the risk factor
   */
  @Nullable
  public ResultStrength getAddressRisk() {
    if (addressRisk == null) {
      return null;
    }
    return ResultStrength.toEnum(addressRisk.toLowerCase());
  }

  /**
   * Assesses the match for identity.
   *
   * @return Identity match rating
   */
  @Nullable
  public MatchRating getIdentificationMatch() {
    return MatchRating.toEnum(identficationMatch);
  }

  /**
   * Assesses the date of birth match.
   *
   * @return thee of birth match rating
   */
  @Nullable
  public MatchRating getDateOfBirthMatch() {
    return MatchRating.toEnum(dateOfBirthMatch);
  }

  /**
   * Assesses the person for placement on the OFAC.
   *
   * @return the OFAC match rating
   */
  @Nullable
  public MatchRating getOfac() {
    return MatchRating.toEnum(ofacMatch);
  }

  /**
   * Assesses the person for placement on the PEP.
   *
   * @return the PEP match rating
   */
  @Nullable
  public MatchRating getPep() {
    return MatchRating.toEnum(pepMatch);
  }
}
