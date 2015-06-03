package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.List;

/**
 * The model representing a set of answered questions used for scoring a QuestionSet.
 */
public class AnswerSet {
  @JsonProperty("answers")
  private List<QuestionAnswerPair> answers;

  public AnswerSet() {
    answers = new ArrayList<QuestionAnswerPair>();
  }

  /**
   * Adds an answer to a question to the AnswerSet.
   *
   * @param questionId  the id of the question to be answered
   * @param answerId  the id of the answer selected
   */
  public void addAnswer(int questionId, int answerId) {
    QuestionAnswerPair answerPair = new QuestionAnswerPair(questionId, answerId);
    answers.add(answerPair);
  }

  private static class QuestionAnswerPair {
    @SuppressFBWarnings("URF_UNREAD_FIELD")
    @JsonProperty("question_id")
    private int questionId;

    @SuppressFBWarnings("URF_UNREAD_FIELD")
    @JsonProperty("answer_id")
    private int answerId;

    private QuestionAnswerPair() {
      // Do nothing. No-argument constructor required for Retrofit.
    }

    public QuestionAnswerPair(int questionId, int answerId) {
      this.questionId = questionId;
      this.answerId = answerId;
    }
  }
}
