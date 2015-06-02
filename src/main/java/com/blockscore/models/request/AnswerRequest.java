package com.blockscore.models.request;

import com.blockscore.models.AnsweredQuestion;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Request object to hold answers. Used when scoring answers. TODO: remove this class
 */
public class AnswerRequest {
    @NotNull
    @JsonProperty("answers")
    private List<AnsweredQuestion> mAnswers;

    public AnswerRequest() {
        //Does nothing.
    }

    public AnswerRequest(@NotNull final List<AnsweredQuestion> answers) {
        mAnswers = answers;
    }

    /**
     * Gets answers currently assigned to this request.
     * @return Answers in request.
     */
    @NotNull
    public List<AnsweredQuestion> getAnswers() {
        return mAnswers;
    }
}
