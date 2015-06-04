package com.blockscore.models;

import static com.blockscore.models.PersonTest.createTestPerson;
import static com.blockscore.models.TestUtils.assertBasicResponseIsValid;
import static com.blockscore.models.TestUtils.assertBasicResponsesAreEquivalent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.blockscore.exceptions.InvalidRequestException;

import org.junit.Test;

import java.util.List;

/**
 * QuestionSet unit tests.
 */
public class QuestionSetTest {
  private static Person person = createTestPerson();

  @Test
  public void testQuestionSetCreation() {
    QuestionSet questionSet = createQuestionSet();
    assertQuestionSetIsValid(questionSet);
  }

  @Test
  public void testQuestionSetRetrieval() {
    QuestionSet questionSet = createQuestionSet();

    QuestionSet retrievedQuestionSet = person.retrieveQuestionSet(questionSet.getId());
    assertQuestionSetIsValid(retrievedQuestionSet);

    // Make sure the two question sets are equivalent
    assertQuestionSetsAreEquivalent(questionSet, retrievedQuestionSet);
  }

  @Test
  public void testQuestionSetRetrieval_InvalidId() {
    InvalidRequestException expected = null;

    try {
      person.retrieveQuestionSet("-1");
    } catch (InvalidRequestException e) {
      assertNotNull(e.getMessage());
      expected = e;
    }

    assertNotNull(expected);
  }

  @Test
  public void testQuestionSetListing() {
    List<String> questionSetIds = person.getQuestionSetIds();

    for (String questionSetId : questionSetIds) {
      assertQuestionSetIsValid(person.retrieveQuestionSet(questionSetId));
    }
  }

  @Test
  public void testQuestionSetScoring() {
    QuestionSet questionSet = createQuestionSet();

    AnswerSet answers = new AnswerSet();
    for (Question question : questionSet.getQuestions()) {
      answers.addAnswer(question.getId(), 1);
    }

    assertNull(questionSet.getScore());

    questionSet.score(answers);

    assertQuestionSetIsValid(questionSet);
    assertNotNull(questionSet.getScore());
  }

  @Test
  public void testQuestionSetScoring_NoAnswers() {
    InvalidRequestException expected = null;

    QuestionSet questionSet = createQuestionSet();

    try {
      questionSet.score(new AnswerSet());
    } catch (InvalidRequestException e) {
      assertNotNull(e.getMessage());
      expected = e;
    }

    assertNotNull(expected);
  }

// Invalid question ids & answer ids are not currently validated. They should be in the future.
//  @Test
//  public void testQuestionSetScoring_InvalidAnswers() {
//    InvalidRequestException expected = null;
//
//    QuestionSet questionSet = createQuestionSet();
//
//    AnswerSet answers = new AnswerSet();
//    answers.addAnswer(-1, 1000);
//
//    try {
//      questionSet.score(answers);
//    } catch (InvalidRequestException e) {
//      assertNotNull(e.getMessage());
//      expected = e;
//    }
//
//    assertNotNull(expected);
//  }

  /*------------------*/
  /* Helper Functions */
  /*------------------*/

  private QuestionSet createQuestionSet() {
    return person.createQuestionSet();
  }

  private void assertQuestionSetsAreEquivalent(QuestionSet expected, QuestionSet actual) {
    assertBasicResponsesAreEquivalent(expected, actual);
    assertEquals(expected.getScore(), actual.getScore());
    assertQuestionsAreEquivalent(expected.getQuestions(), expected.getQuestions());
  }

  private void assertQuestionsAreEquivalent(List<Question> expected, List<Question> actual) {
    assertEquals(expected.size(), actual.size());

    for (int i = 0; i < expected.size(); ++i) {
      assertQuestionIsEquivalent(expected.get(i), actual.get(i));
    }
  }

  private void assertQuestionIsEquivalent(Question expected, Question actual) {
    assertEquals(expected.getQuestion(), actual.getQuestion());

    assertEquals(expected.getAnswers().size(), expected.getAnswers().size());
    for (int i = 0; i < expected.getAnswers().size(); ++i) {
      Answer expectedAnswer = expected.getAnswers().get(i);
      Answer actualAnswer = actual.getAnswers().get(i);
      assertEquals(expectedAnswer.getId(), actualAnswer.getId());
      assertEquals(expectedAnswer.getAnswerText(), actualAnswer.getAnswerText());
    }
   }

  private void assertQuestionSetIsValid(final QuestionSet questionSet) {
    assertBasicResponseIsValid(questionSet);
    assertNotNull(questionSet.getTimeLimit());
    assertNotNull(questionSet.getPersonId());
    assertQuestionsAreValid(questionSet.getQuestions());
  }

  private void assertQuestionsAreValid(final List<Question> questions) {
    assertNotNull(questions);
    assertNotEquals(0, questions.size());

    for (Question question : questions) {
      assertNotNull(question.getId());
      assertNotNull(question.getQuestion());
      assertAnswersAreValid(question.getAnswers());
    }
  }

  private void assertAnswersAreValid(final List<Answer> answers) {
    assertNotNull(answers);
    assertNotEquals(0, answers.size());

    for (Answer answer : answers) {
      assertNotNull(answer.getId());
      assertNotNull(answer.getAnswerText());
    }
  }
}
