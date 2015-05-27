import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.*;
import com.blockscore.models.request.AnswerRequest;
import com.blockscore.models.results.PaginatedResult;
import com.blockscore.models.Person;
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
public class PersonTest {
    BlockscoreApiClient apiClient = setupBlockscoreApiClient();

    @Test
    public void createPersonTest() throws ParseException {
        Person person = apiClient.createPerson(createTestPerson());
        isPersonValid(person);
    }

    @Test
    public void retrievePersonTest() throws ParseException {
        Person person = apiClient.createPerson(createTestPerson());
        person = apiClient.retrievePerson(person.getId());
        isPersonValid(person);
    }

    @Test
    public void listPeopleTest() throws ParseException {
        PaginatedResult<Person> persons = apiClient.listPeople();
        arePersonsValid(persons.getData());
    }

    @Test
    public void questionSetTest() throws ParseException {
        Person person = apiClient.createPerson(createTestPerson());

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
    public void createBadPersonTest() throws ParseException {
        InvalidRequestException exception = null;

        try {
            Person person = apiClient.createPerson(createBadTestPerson());
            isPersonValid(person);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            Assert.assertNotNull(e.getInvalidParam());
            exception = e;
        }

        Assert.assertNotNull(exception);
    }

    @Test
    public void getNonexistentPerson() {
        InvalidRequestException exception = null;

        try {
            Person person = apiClient.retrievePerson("-1");
            isPersonValid(person);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    public void scoreQuestionSetWithNoAnswers() throws ParseException {
        InvalidRequestException exception = null;

        //Test creation of person
        Person person = apiClient.createPerson(createTestPerson());

        //person = apiClient.retrievePerson(person.getId()); TODO: BUG--the restAdapter dies.
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
    public void scoreQuestionSetWithBadAnswers() throws ParseException {
        InvalidRequestException exception = null;

        Person person = apiClient.createPerson(createTestPerson());
        QuestionSet questionSet = person.createQuestionSet();

        try {
            ArrayList<AnsweredQuestion> answered = new ArrayList<AnsweredQuestion>();
            for (Question question : questionSet.retrieveQuestionSet()) {
                AnsweredQuestion answeredQuestion = new AnsweredQuestion(question.getId(), 1000000);
                answered.add(answeredQuestion);
            }
            AnswerRequest request = new AnswerRequest(answered);
            //Open issue for this: https://github.com/BlockScore/blockscore-api/issues/333
            //This should return an error, but instead allows it through. This code should be updated once the bug
            //is fixed.
            QuestionSet results = questionSet.score(new AnswerRequest());
            isQuestionSetValid(results);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        //Assert.assertNotNull(exception); //Uncomment this once the issue is resolved to confirm the code works.
    }

    @Test
    public void getNonexistentQuestionSet() throws ParseException {
        InvalidRequestException exception = null;
        Person person = apiClient.createPerson(createTestPerson());

        try {
            QuestionSet questionSet = person.retrieveQuestionSet("-1");
            isQuestionSetValid(questionSet);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    /**
     * Generates a sample individual to use for this test suite.
     * @return Person to test with.
     * @throws ParseException
     */
    @NotNull
    private Person createTestPerson() throws ParseException {
        Person person = null;
        try {
         person = new Person(apiClient);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = formatter.parse("1980-08-23");

        person.setFirstName("John")
                .setMiddleName("Pearce")
                .setLastName("Doe")
                .setDocumentType("ssn")
                .setDocumentValue("0000")
                .setAddress(address)
                .setDateOfBirth(dob);
        return person;
    }

    /**
     * Generates a bad sample individual to use for this test suite.
     * @return Person to test with.
     * @throws ParseException
     */
    @NotNull
    private Person createBadTestPerson() throws ParseException {
        Person person = new Person(apiClient);

        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("1980-08-23");
        return person;
    }

    /**
     * Checks for a valid person list.
     * @param personList Persons under test.
     */
    private void arePersonsValid(@Nullable final List<Person> personList) {
        Assert.assertNotNull(personList);
        for (Person person : personList) {
            isPersonValid(person);
        }
    }

    /**
     * Determines if the person is valid.
     * @param person Person under test.
     */
    private void isPersonValid(@NotNull final Person person) {
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getId());
        Assert.assertNotNull(person.getQuestionSets());

        areDetailsValid(person.getDetails());
        isNameValid(person);
        //isAddressValid(person.getAddress());
    }

    /**
     * Checks if the details of this person are complete.
     * @param details Details under test.
     */
    private void areDetailsValid(@NotNull final Details details) {
        Assert.assertNotNull(details);
        Assert.assertNotNull(details.getAddressRisk());
        Assert.assertNotNull(details.getAddressMatchDetails());
        Assert.assertNotNull(details.getIdentfication());
        Assert.assertNotNull(details.getDateOfBirth());
        Assert.assertNotNull(details.getOFAC());
    }

    /**
     * Checks for a valid question set list.
     * @param questionSetList Question sets under test.
     */
    private void areQuestionSetsValid(@Nullable final List<QuestionSet> questionSetList) {
        Assert.assertNotNull(questionSetList);
        for (QuestionSet questionSet : questionSetList) {
            isQuestionSetValid(questionSet);
        }
    }

    /**
     * Validates the question set.
     * @param questionSet Question set to be tested.
     */
    private void isQuestionSetValid(@Nullable final QuestionSet questionSet) {
        Assert.assertNotNull(questionSet);
        Assert.assertNotNull(questionSet.getPersonId());
        areQuestionsValid(questionSet.retrieveQuestionSet());
    }

    /**
     * Determines if the questions are valid.
     * @param questionSet Questions to test.
     */
    private void areQuestionsValid(@Nullable final List<Question> questionSet) {
        Assert.assertNotNull(questionSet);
        for (Question question : questionSet) {
            Assert.assertNotNull(question.getId());
            Assert.assertNotNull(question.getQuestion());
            areAnswersValid(question.getAnswers());
        }
    }

    /**
     * Determines if the answers are valid.
     * @param answers Answers to use.
     */
    private void areAnswersValid(@Nullable final List<Answer> answers) {
        Assert.assertNotNull(answers);
        for (Answer answer : answers) {
            Assert.assertNotNull(answer.getId());
            Assert.assertNotNull(answer.getAnswer());
        }
    }

    /**
     * Examines the address and ensures it is valid.
     * @param address Address to test.
     */
    private void isAddressValid(@Nullable final Address address) {
        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getStreet1());
        Assert.assertNotNull(address.getSubdivision());
        Assert.assertNotNull(address.getPostalCode());
        Assert.assertNotNull(address.getCountryCode());
        Assert.assertNotNull(address.getCity());
    }

    /**
     * Examines the name and ensures it is valid.
     * @param name Name to test.
     */
    private void isNameValid(@Nullable final Person name) {
        Assert.assertNotNull(name);
        Assert.assertNotNull(name.getFirstName());
        Assert.assertNotNull(name.getLastName());
    }

    /**
     * Sets up the API client.
     * @return API client.
     */
    @NotNull
    private BlockscoreApiClient setupBlockscoreApiClient() {
        BlockscoreApiClient.useVerboseLogs(false);
        return new BlockscoreApiClient("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
    }
}
