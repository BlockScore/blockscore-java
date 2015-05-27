package com.blockscore.models;

import com.blockscore.models.base.BasicResponse;
import com.blockscore.models.request.AnswerRequest;
import com.blockscore.net.BlockscoreRetrofitAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Question set model.
 */
public class QuestionSet extends BasicResponse {
    @NotNull
    @JsonProperty("person_id")
    private String personId;

    @JsonProperty("score")
    private int score;

    @JsonProperty("expired")
    private boolean expired;

    @JsonProperty("time_limit")
    private long timeLimit;

    @NotNull
    @JsonProperty("questions")
    private List<Question> questionSet;

    private BlockscoreRetrofitAPI restAdapter;

    /**
     * Scores a question set.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#scoreQuestionSet(String, com.blockscore.models.request.AnswerRequest)
     * @param questionSetId Question set ID
     * @param answers Answers to questions
     */
    @NotNull
    public QuestionSet score(@NotNull final AnswerRequest answers) {
        return restAdapter.scoreQuestionSet(getId(), answers);
    }

    public void setAdapter(BlockscoreRetrofitAPI restAdapter) {
        this.restAdapter = restAdapter;
    }

    /**
     * The associated person's ID.
     * @return Person ID for this question set.
     */
    @NotNull
    public String getPersonId() {
        return personId;
    }

    /**
     * The percentage (from 0.0 - 100.0) result of scoring the question set's answers.
     * @return Score
     */
    public int getScore() {
        return score;
    }

    /**
     * Indicates whether the `time_limit` has been passed since the creation of the question set.
     * @return True if expired.
     */
    public boolean isExpired() {
        return expired;
    }

    /**
     * Gets the number of seconds after the creation of the question set before the `expired` boolean will switch to `true`.
     * @return Time limit.
     */
    public long getTimeLimit() {
        return timeLimit;
    }

    /**
     * Returns the list of questions to use.
     * @return Questions available.
     */
    @NotNull
    public List<Question> retrieveQuestionSet() {
        return questionSet;
    }
}
