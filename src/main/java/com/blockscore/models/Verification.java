package com.blockscore.models;

import com.blockscore.models.base.BasicResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Verification model.
 * Created by tealocean on 9/29/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Verification extends BasicResponse {
    @NotNull
    @JsonProperty("status")
    private String mStatus;

    @NotNull
    @JsonProperty("date_of_birth")
    private String mDateOfBirth;

    @Nullable
    @JsonProperty("phone_number")
    private String mPhoneNumber;

    @Nullable
    @JsonProperty("ip_address")
    private String mIPAddress;

    @NotNull
    @JsonProperty("identification")
    private Identification mIdentification;

    @NotNull
    @JsonProperty("details")
    private Details mDetails;

    @NotNull
    @JsonProperty("name")
    private Name mName;

    @NotNull
    @JsonProperty("address")
    private Address mAddress;

    @NotNull
    @JsonProperty("question_sets")
    private List<QuestionSet> mQuestionSets;

    /**
     * Returns either valid or invalid and is the culmination of whether or not the passed
     * in information is valid against various databases and signals.
     * @return True if valid.
     */
    public boolean isValid() {
        return Status.VALID.isEqualTo(mStatus);
    }

    /**
     * Gets the date of birth. (yyyy-MM-dd)
     * @return Date of birth.
     */
    @NotNull
    public String getDateOfBirth() {
        return mDateOfBirth;
    }

    /**
     * Gets the phone number (If available)
     * @return Phone number.
     */
    @Nullable
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    /**
     * Gets the IP Address (If available).
     * @return IP address.
     */
    @Nullable
    public String getIPAddress() {
        return mIPAddress;
    }

    /**
     * Gets the form of identification used.
     * @return Form of identification used.
     */
    @NotNull
    public Identification getIdentification() {
        return mIdentification;
    }

    /**
     * A breakdown of some of the information that determines the status element.
     * @return Details breakdown.
     */
    @NotNull
    public Details getDetails() {
        return mDetails;
    }

    /**
     * Gets the person's name.
     * @return Name
     */
    @NotNull
    public Name getName() {
        return mName;
    }

    /**
     * Gets the address for this individual.
     * @return Address.
     */
    @NotNull
    public Address getAddress() {
        return mAddress;
    }

    /**
     * Gets the question sets associated with this verification record.
     * @return Question sets.
     */
    @NotNull
    public List<QuestionSet> getQuestionSets() {
        return mQuestionSets;
    }

    public enum Status {
        VALID("valid"), INVALID("invalid");

        private final String mValue;

        private Status(@NotNull final String value) {
            mValue = value;
        }

        /**
         * Returns if this status matches.
         * @return True or false.
         */
        public boolean isEqualTo(final String value) {
            return mValue.equalsIgnoreCase(value);
        }
    }
}
