package com.blockscore.models.results;

import com.blockscore.common.ValidityStatus;
import com.blockscore.models.Details;
import com.blockscore.models.Person;
import com.blockscore.models.QuestionSet;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Verification model.
 * Created by tealocean on 9/29/14.
 */
public class Verification extends Person {
    @NotNull
    @JsonProperty("status")
    private String mStatus;

    @NotNull
    @JsonProperty("details")
    private Details mDetails;

    @NotNull
    @JsonProperty("question_sets")
    private List<QuestionSet> mQuestionSets;

    /**
     * Returns either valid or invalid and is the culmination of whether or not the passed
     * in information is valid against various databases and signals.
     * @return True if valid.
     */
    public boolean isValid() {
        return ValidityStatus.VALID.isEqualTo(mStatus);
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
     * Gets the question sets associated with this verification record.
     * @return Question sets.
     */
    @NotNull
    public List<QuestionSet> getQuestionSets() {
        return mQuestionSets;
    }
}
