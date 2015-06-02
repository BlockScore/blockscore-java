package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public void addAnswer(int questionId, int answerId) {
        QuestionAnswerPair answerPair = new QuestionAnswerPair(questionId, answerId);
        answers.add(answerPair);
    }

    private static class QuestionAnswerPair {
        @JsonProperty("question_id")
        private int questionId;

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
