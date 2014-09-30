package com.blockscore.models.request;

import com.blockscore.models.AnsweredQuestion;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Request object to hold answers. Used when scoring answers.
 * Created by tealocean on 9/30/14.
 */
public class AnswerRequest {

    @JsonProperty("answers")
    private List<AnsweredQuestion> mAnswers;

    public AnswerRequest(@NotNull final List<AnsweredQuestion> answers) {
        mAnswers = answers;
    }
}
