package com.blockscore.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Question set request model. Used in creation of question sets.
 */
public class QuestionSetRequest {

    @NotNull
    @JsonProperty("person_id")
    private String personId;

    @JsonProperty("time_limit")
    private long timeLimit = 0L;

    /**
     * The ID of the Person, and thus the identity, on which to base the question sets.
     * You can create multiple question sets using the same question set ID.
     * @param PersonId Person ID
     */
    @NotNull
    public QuestionSetRequest setPersonId(@NotNull final String personId) {
        this.personId = personId;
        return this;
    }

    /**
     * How long after creating a question set that the expired boolean will remain false.
     * This is inputted in seconds and defaults to 0, which means no time limit.
     * This allows you to make sure that your customer does not take too long to answer a question set.
     * @param timeLimit Time limit.
     */
    @NotNull
    public QuestionSetRequest setTimeLimit(final long timeLimit) {
        this.timeLimit = timeLimit;
        return this;
    }

    /**
     * Gets the Person ID set to this request.
     */
    @Nullable
    public String getPersonId() {
        return personId;
    }

    /**
     * Gets the time limit set to this request.
     */
    public long getTimeLimit() {
        return timeLimit;
    }
}
