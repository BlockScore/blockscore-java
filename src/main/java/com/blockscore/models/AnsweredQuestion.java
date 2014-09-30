package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Answered question model.
 * Created by tealocean on 9/30/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
     */
    public AnsweredQuestion setQuestionId(final int questionId) {
        mQuestionId = questionId;
        return this;
    }

    /**
     * Sets the answer ID.
     * @param answerId ID associated with answer.
     */
    public AnsweredQuestion setAnswerId(final int answerId) {
        mAnswerId = answerId;
        return this;
    }
}
