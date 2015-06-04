package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * The model for a question in a {@link QuestionSet}.
 */
public class Question {
  @JsonProperty("id")
  private int id;

  @NotNull
  @JsonProperty("question")
  private String question;

  @NotNull
  @JsonProperty("answers")
  private List<Answer> answers;

  /**
   * Gets the unique ID for this question.
   *
   * @return the ID
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the question text to be asked.
   *
   * @return the question
   */
  @NotNull
  public String getQuestion() {
    return question;
  }

  /**
   * Gets all possible answers to show to the user.
   *
   * @return the possible answers
   */
  @NotNull
  public List<Answer> getAnswers() {
    return Collections.unmodifiableList(answers);
  }
}
