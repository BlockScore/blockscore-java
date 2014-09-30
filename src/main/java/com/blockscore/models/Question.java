package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Question model.
 * Created by Tony Dieppa on 9/30/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question {
    @JsonProperty("id")
    private int mId;

    @NotNull
    @JsonProperty("question")
    private String mQuestion;

    @NotNull
    @JsonProperty("answers")
    private List<Answer> mAnswers;

    /**
     * Gets the unique ID for this question.
     * @return ID.
     */
    public int getId() {
        return mId;
    }

    /**
     * Gets the question to be asked.
     * @return Question
     */
    @NotNull
    public String getQuestion() {
        return mQuestion;
    }

    /**
     * Gets all possible answers to show to the user.
     * @return Answers.
     */
    @NotNull
    public List<Answer> getAnswers() {
        return mAnswers;
    }
}
