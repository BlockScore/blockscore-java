package com.blockscore.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Search request model for watch lists.
 */
public class SearchRequest {
    @NotNull
    @JsonProperty("candidate_id")
    private String id;

    @Nullable
    @JsonProperty("match_type")
    private String matchType;

    public SearchRequest() {
        //Do nothing.
    }

    public SearchRequest(@NotNull final String id) {
        this.id = id;
    }

    /**
     * Sets the match type.
     * @param matchType Match type to use.
     */
    public void setMatchType(@Nullable final MatchType matchType) {
        if (matchType == null) {
            this.matchType = null;
        } else {
            this.matchType = matchType.toString();
        }
    }

    /**
     * Gets the ID for the candidate to search for.
     * @return ID
     */
    @NotNull
    public String getId() {
        return id;
    }

    /**
     * Gets the match type (Person or Company).
     * @return Match type.
     */
    @Nullable
    public MatchType getMatchType() {
        return MatchType.toEnum(matchType);
    }

    public enum MatchType {
        COMPANY("company"), PERSON("person");

        private final String value;

        private MatchType(@NotNull final String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
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
