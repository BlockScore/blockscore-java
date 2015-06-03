package com.blockscore.models;

import com.blockscore.models.base.BasicResponse;
import com.blockscore.net.BlockscoreRestAdapter;

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

  private BlockscoreRestAdapter restAdapter;

  /**
   * Scores a question set.
   *
   * @param answers  the answers to the QuestionSet
   * @return the scored question set
   */
  @NotNull
  public QuestionSet score(@NotNull final AnswerSet answers) {
    return restAdapter.scoreQuestionSet(getId(), answers); //TODO: update instead of return?
  }

  // TODO: remove
  public void setAdapter(BlockscoreRestAdapter restAdapter) {
    this.restAdapter = restAdapter;
  }

  /**
   * The associated person's ID.
   *
   * @return the person ID
   */
  @NotNull
  public String getPersonId() {
    return personId;
  }

  /**
   * The percentage (from 0.0 - 100.0) result of scoring the question set's answers.
   *
   * @return the score
   */
  public int getScore() {
    return score;
  }

  /**
   * Indicates whether the `time_limit` has been passed since the creation of the question set.
   *
   * @return true if expired
   */
  public boolean isExpired() {
    return expired;
  }

  /**
   * Gets the number of seconds after the creation of the question set before the `expired` boolean
   * will switch to `true`.
   *
   * @return the time limit
   */
  public long getTimeLimit() {
    return timeLimit;
  }

  /**
   * Returns the list of questions to use.
   *
   * @return the questions available
   */
  @NotNull
  public List<Question> retrieveQuestionSet() {
    return questionSet;
  }
}
