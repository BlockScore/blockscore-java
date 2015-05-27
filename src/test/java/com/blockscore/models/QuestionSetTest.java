package com.blockscore.models;

import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.request.AnswerRequest;
import com.blockscore.models.results.PaginatedResult;

import com.blockscore.net.BlockscoreApiClient;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple test for the person process.
 */
public class QuestionSetTest {
    BlockscoreApiClient apiClient = setupBlockscoreApiClient();

    @Test
    public void questionSetTest() {
        Person person = createTestPerson();

        QuestionSet questionSet = person.createQuestionSet(100000);
        isQuestionSetValid(questionSet);

        //Test scoring a question set.
        ArrayList<AnsweredQuestion> answered = new ArrayList<AnsweredQuestion>();
        for (Question question : questionSet.retrieveQuestionSet()) {
            AnsweredQuestion answeredQuestion = new AnsweredQuestion(question.getId(), 1);
            answered.add(answeredQuestion);
        }
        AnswerRequest request = new AnswerRequest(answered);
        questionSet = questionSet.score(request);
        isQuestionSetValid(questionSet);

        //Test getting a question set.
        questionSet = person.retrieveQuestionSet(questionSet.getId());
        isQuestionSetValid(questionSet);

        //Test listing question sets.
        PaginatedResult<QuestionSet> questionSets = person.listQuestionSet();
        areQuestionSetsValid(questionSets.getData());
    }

    @Test
    public void scoreQuestionSetWithNoAnswers() {
        InvalidRequestException exception = null;

        //Test creation of person
        Person person = createTestPerson();

        QuestionSet questionSet = person.createQuestionSet(100000);

        try {
            QuestionSet results = questionSet.score(new AnswerRequest());
            isQuestionSetValid(results);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @SuppressFBWarnings(value = {"DLS"})
    @Test
    public void scoreQuestionSetWithBadAnswers() {
        InvalidRequestException exception = null;

        Person person = createTestPerson();
        QuestionSet questionSet = person.createQuestionSet();

        try {
            ArrayList<AnsweredQuestion> answered = new ArrayList<AnsweredQuestion>();
            for (Question question : questionSet.retrieveQuestionSet()) {
                AnsweredQuestion answeredQuestion = new AnsweredQuestion(question.getId(), 1000000);
                answered.add(answeredQuestion);
            }
            AnswerRequest request = new AnswerRequest(answered);
            QuestionSet results = questionSet.score(new AnswerRequest());
            isQuestionSetValid(results);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    public void getNonexistentQuestionSet() {
        InvalidRequestException exception = null;
        Person person = createTestPerson();

        try {
            QuestionSet questionSet = person.retrieveQuestionSet("-1");
            isQuestionSetValid(questionSet);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @NotNull
    private Person createTestPerson() {
        Person.Builder builder = new Person.Builder(apiClient);

        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = null;
        try {
            dateOfBirth = formatter.parse("1980-08-23");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        builder.setFirstName("John")
               .setMiddleName("Pearce")
               .setLastName("Doe")
               .setDocumentType("ssn")
               .setDocumentValue("0000")
               .setAddress(address)
               .setDateOfBirth(dateOfBirth);
        return builder.create();
    }

    private void areQuestionSetsValid(@Nullable final List<QuestionSet> questionSetList) {
        Assert.assertNotNull(questionSetList);
        for (QuestionSet questionSet : questionSetList) {
            isQuestionSetValid(questionSet);
        }
    }

    private void isQuestionSetValid(@Nullable final QuestionSet questionSet) {
        Assert.assertNotNull(questionSet);
        Assert.assertNotNull(questionSet.getPersonId());
        areQuestionsValid(questionSet.retrieveQuestionSet());
    }

    private void areQuestionsValid(@Nullable final List<Question> questionSet) {
        Assert.assertNotNull(questionSet);
        for (Question question : questionSet) {
            Assert.assertNotNull(question.getId());
            Assert.assertNotNull(question.getQuestion());
            areAnswersValid(question.getAnswers());
        }
    }

    private void areAnswersValid(@Nullable final List<Answer> answers) {
        Assert.assertNotNull(answers);
        for (Answer answer : answers) {
            Assert.assertNotNull(answer.getId());
            Assert.assertNotNull(answer.getAnswer());
        }
    }

    @NotNull
    private BlockscoreApiClient setupBlockscoreApiClient() {
        BlockscoreApiClient.useVerboseLogs(false);
        return new BlockscoreApiClient("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
    }
}
