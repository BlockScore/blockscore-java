package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Answered question model.
 * Created by tealocean on 9/30/14.
 */
public class AnsweredQuestion {
    @JsonProperty("question_id")
    private int mQuestionId;

    @JsonProperty("answer_id")
    private int mAnswerId;

    public AnsweredQuestion() {
        //Nothing is initialized.
    }

    public AnsweredQuestion(int questionId, int answerId) {
        mQuestionId = questionId;
        mAnswerId = answerId;
    }

    /**
     * Sets the question ID.
     * @param questionId Question ID
     * @return this
     */
    public AnsweredQuestion setQuestionId(final int questionId) {
        mQuestionId = questionId;
        return this;
    }

    /**
     * Sets the answer ID.
     * @param answerId ID associated with answer.
     * @return this
     */
    public AnsweredQuestion setAnswerId(final int answerId) {
        mAnswerId = answerId;
        return this;
    }

    /**
     * Gets the question ID.
     * @return Question ID
     */
    public int getQuestionId() {
        return mQuestionId;
    }

    /**
     * Gets the selected answer.
     * @return Answer selected.
     */
    public int getAnswerId() {
        return mAnswerId;
    }
}
