package com.blockscore.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Search request model for watch lists.
 * Created by Tony Dieppa on 9/30/14.
 */
public class SearchRequest {
    @NotNull
    @JsonProperty("watchlist_candidate_id")
    private String mId;

    @Nullable
    @JsonProperty("match_type")
    private String mMatchType;

    public SearchRequest(@NotNull final String id) {
        mId = id;
    }

    /**
     * Sets the match type.
     * @param matchType Match type to use.
     */
    public void setMatchType(@Nullable final MatchType matchType) {
        if (matchType == null) {
            mMatchType = null;
        } else {
            mMatchType = matchType.toString();
        }
    }

    /**
     * Gets the ID for the candidate to search for.
     * @return ID
     */
    @NotNull
    public String getId() {
        return mId;
    }

    /**
     * Gets the match type (Person or Company)
     * @return Match type.
     */
    @Nullable
    public MatchType getMatchType() {
        return MatchType.toEnum(mMatchType);
    }

    public enum MatchType {
        COMPANY("company"), PERSON("person");

        private final String mValue;

        private MatchType(@NotNull final String value) {
            mValue = value;
        }

        @Override
        public String toString() {
            return mValue;
        }

        /**
         * Converts a string to a match type enum.
         * @param value Value to convert.
         * @return Match type.
         */
        public static MatchType toEnum(final String value) {
            if (value == null) {
                return null;
            }

            if (value.equalsIgnoreCase(COMPANY.toString())) {
                return COMPANY;
            } else if (value.equalsIgnoreCase(PERSON.toString())) {
                return PERSON;
            } else {
                return null;
            }
        }
    }
}
