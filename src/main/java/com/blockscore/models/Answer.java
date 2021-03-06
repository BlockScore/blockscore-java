package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * The answer model for potential answers to a {@link QuestionSet}.
 */
public class Answer {
  @JsonProperty("id")
  private int id;

  @NotNull
  @JsonProperty("answer")
  private String answer;

  /**
   * Gets the ID for this answer.
   *
   * @return the answer's ID
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the answer text to display to the user.
   *
   * @return the possible answer
   */
  @NotNull
  public String getAnswerText() {
    return answer;
  }
}
