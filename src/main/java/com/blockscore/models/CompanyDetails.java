package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The details model for {@link Company}.
 */
public class CompanyDetails {
    @NotNull
    @JsonProperty("ofac")
    private String ofacMatch;

    @NotNull
    @JsonProperty("state")
    private String stateMatch;

    @Nullable
    @JsonProperty("tax_id")
    private String taxIdMatch;

    @Nullable
    @JsonProperty("address")
    private String addressMatch;

    @Nullable
    @JsonProperty("entity_name")
    private String entityMatch;

    @Nullable
    @JsonProperty("incorp_date")
    private String incorporationDateMatch;

    @Nullable
    @JsonProperty("country_code")
    private String countryCodeMatch;

    /**
     * Assesses the company for placement on the OFAC.
     *
     * @return the OFAC match rating
     */
    @Nullable
    public MatchRating getOfacMatch() {
        return MatchRating.toEnum(ofacMatch);
    }

    /**
     * Assesses the company's state.
     *
     * @return the state match rating
     */
    @Nullable
    public MatchRating getStateMatch() {
        return MatchRating.toEnum(stateMatch);
    }

    /**
     * Assesses the tax ID match.
     *
     * @return the tax id match rating
     */
    @Nullable
    public MatchRating getTaxIdMatch() {
        return MatchRating.toEnum(taxIdMatch);
    }

    /**
     * Assesses the company's address match.
     *
     * @return the address match rating
     */
    @Nullable
    public MatchRating getAddressMatch() {
        return MatchRating.toEnum(taxIdMatch);
    }

    /**
     * Assesses the entity name match.
     *
     * @return the entity name match rating
     */
    @Nullable
    public MatchRating getEntityMatch() {
        return MatchRating.toEnum(entityMatch);
    }

    /**
     * Assesses incorporation date match.
     *
     * @return the incorporation date match rating
     */
    @Nullable
    public MatchRating getIncorporationDateMatch() {
        return MatchRating.toEnum(incorporationDateMatch);
    }

    /**
     * Assesses the country code match.
     *
     * @return the country code match rating
     */
    @Nullable
    public MatchRating getCountryCodeMatch() {
        return MatchRating.toEnum(countryCodeMatch);
    }
}
