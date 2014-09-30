package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Details breakdown model (Used in verification)
 * Created by Tony Dieppa on 9/29/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Details {
    @NotNull
    @JsonProperty("address")
    private String mAddressMatch;

    @NotNull
    @JsonProperty("address_risk")
    private String mAddressRisk;

    @NotNull
    @JsonProperty("identification")
    private String mIdentficationMatch;

    @NotNull
    @JsonProperty("date_of_birth")
    private String mDateOfBirthMatch;

    @NotNull
    @JsonProperty("ofac")
    private String mOFACMatch;

    /**
     * Assesses the address match
     * @return Matching rating with the address.
     */
    @NotNull
    public MatchRank getAddressMatchDetails() {
        return getMatchRank(mAddressMatch);
    }

    /**
     * Gets the risk of this particular address.
     * @return Risk factor.
     */
    @NotNull
    public AddressRisk getAddressRisk() {
        return AddressRisk.valueOf(mAddressRisk.toLowerCase());
    }

    /**
     * Assesses the match for identity.
     * @return Identity match rating.
     */
    @NotNull
    public MatchRank getIdentfication() {
        return getMatchRank(mIdentficationMatch);
    }

    /**
     * Assesses the date of birth match.
     * @return Date of birth match rating.
     */
    @NotNull
    public MatchRank getDateOfBirth() {
        return getMatchRank(mDateOfBirthMatch);
    }

    /**
     * Assesses the person for placement on the OFAC.
     * @return OFAC match rating.
     */
    @NotNull
    public MatchRank getOFAC() {
        return getMatchRank(mOFACMatch);
    }

    /**
     * Parses the string for the match rank.
     * @param matchResult String to parse.
     * @return Match rank.
     */
    @NotNull
    private MatchRank getMatchRank(final String matchResult) {
        return MatchRank.valueOf(matchResult.toLowerCase());
    }

    public enum MatchRank {
        MATCH("match"), NOMATCH("no_match"), PARTIAL_MATCH("partial_match"), MISMATCH("mismatch"),
        NO_DATA("insufficient_data");

        private final String mValue;

        private MatchRank(@NotNull final String value) {
            mValue = value;
        }

        @Override
        public String toString() {
            return mValue;
        }
    }

    public enum AddressRisk {
        HIGH("high"), NOMATCH("no_match"), LOW("low"), NO_DATA("insufficient_data");

        private final String mValue;

        private AddressRisk(@NotNull final String value) {
            mValue = value;
        }

        @Override
        public String toString() {
            return mValue;
        }
    }
}
