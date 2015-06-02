package com.blockscore.models;

import com.blockscore.common.AddressRisk;
import com.blockscore.common.MatchRank;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: Break this into Company and Person details... or /something/ 

/**
 * Details breakdown model (Used in Person)
 */
public class Details {
    @Nullable
    @JsonProperty("address")
    private String addressMatch;

    @Nullable
    @JsonProperty("address_risk")
    private String addressRisk;

    @Nullable
    @JsonProperty("identification")
    private String mIdentficationMatch;

    @Nullable
    @JsonProperty("date_of_birth")
    private String dateOfBirthMatch;

    @NotNull
    @JsonProperty("ofac")
    private String mOFACMatch;

    @Nullable
    @JsonProperty("entity_name")
    private String mEntityMatch;

    @Nullable
    @JsonProperty("tax_id")
    private String mTaxId;

    /**
     * Assesses the address match strength.
     *
     * @return the match strength
     */
    @Nullable
    public MatchRank getAddressMatchDetails() {
        return MatchRank.toEnum(addressMatch);
    }

    /**
     * Assesses the entity match strength.
     *
     * @return the match strength
     */
    @Nullable
    public MatchRank getEntityMatch() {
        return MatchRank.toEnum(mEntityMatch);
    }

    /**
     * Assesses the tax ID match strength.
     *
     * @return the match strength
     */
    @Nullable
    public MatchRank getTaxIdMatch() {
        return MatchRank.toEnum(mTaxId);
    }

    /**
     * Gets the risk of this particular address.
     *
     * @return the risk factor
     */
    @Nullable
    public AddressRisk getAddressRisk() {
        if (addressRisk == null) {
            return null;
        }
        return AddressRisk.toEnum(addressRisk.toLowerCase());
    }

    /**
     * Assesses the match for identity.
     *
     * @return Identity match rating.
     */
    @Nullable
    public MatchRank getIdentificationMatch() {
        return MatchRank.toEnum(mIdentficationMatch);
    }

    /**
     * Assesses the date of birth match.
     *
     * @return thee of birth match rating
     */
    @Nullable
    public MatchRank getDateOfBirthMatch() {
        return MatchRank.toEnum(dateOfBirthMatch);
    }

    /**
     * Assesses the person for placement on the OFAC.
     *
     * @return the OFAC match rating.
     */
    @Nullable
    public MatchRank getOFAC() {
        return MatchRank.toEnum(mOFACMatch);
    }
}
