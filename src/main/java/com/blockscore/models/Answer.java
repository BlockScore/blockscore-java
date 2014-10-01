package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Answer model.
 * Created by Tony Dieppa on 9/30/14.
 */
public class Answer {
    @JsonProperty("id")
    private int mId;

    @NotNull
    @JsonProperty("answer")
    private String mAnswer;

    /**
     * Gets the ID for this answer.
     * @return Answer ID
     */
    public int getId() {
        return mId;
    }

    /**
     * Gets the answer to display to the user.
     * @return Possible answer.
     */
    @NotNull
    public String getmAnswer() {
        return mAnswer;
    }
}
