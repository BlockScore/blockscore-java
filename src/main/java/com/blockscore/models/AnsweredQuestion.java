package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Answered question model.
 * TODO: investigate merging with Answer or differentiating the names
 */
public class AnsweredQuestion {
    @JsonProperty("question_id")
    private int mQuestionId;

    @JsonProperty("answer_id")
    private int mAnswerId;

    private AnsweredQuestion() {
        // Do nothing. Necessary for Retrofit to be able to populate an AnsweredQuestion.
    }

    public AnsweredQuestion(int questionId, int answerId) {
        mQuestionId = questionId;
        mAnswerId = answerId;
    }

    /**
     * Gets the question ID.
     *
     * @return the question ID
     */
    public int getQuestionId() {
        return mQuestionId;
    }

    /**
     * Gets the selected answer.
     *
     * @return the answer selected
     */
    public int getAnswerId() {
        return mAnswerId;
    }
}
