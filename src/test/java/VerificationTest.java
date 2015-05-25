import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.*;
import com.blockscore.models.request.AnswerRequest;
import com.blockscore.models.request.QuestionSetRequest;
import com.blockscore.models.results.Verification;
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
 * Simple test for the verification process.
 * Created by Tony Dieppa on 9/30/14.
 */
public class VerificationTest {

    @Test
    public void verificationTest() throws ParseException {
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        //Test creation of person
        Verification verification = apiClient.createPerson(createTestPerson());
        isVerificationValid(verification);

        //Test getting a person
        verification = apiClient.getPerson(verification.getId());
        isVerificationValid(verification);

        //Test listing people
        List<Verification> verifications = apiClient.listPeople();
        areVerificationsValid(verifications);

        //Test creation of a question set.
        QuestionSetRequest questionSetRequest = new QuestionSetRequest();
        questionSetRequest.setTimeLimit(100000).setVerificationId(verification.getId());
        QuestionSet questionSet = apiClient.createQuestionSet(questionSetRequest);
        isQuestionSetValid(questionSet);

        //Test scoring a question set.
        ArrayList<AnsweredQuestion> answered = new ArrayList<AnsweredQuestion>();
        for (Question question : questionSet.getQuestionSet()) {
            AnsweredQuestion answeredQuestion = new AnsweredQuestion(question.getId(), 1);
            answered.add(answeredQuestion);
        }
        AnswerRequest request = new AnswerRequest(answered);
        questionSet = apiClient.scoreQuestionSet(questionSet.getId(), request);
        isQuestionSetValid(questionSet);

        //Test getting a question set.
        questionSet = apiClient.getQuestionSet(questionSet.getId());
        isQuestionSetValid(questionSet);

        //Test listing question sets.
        List<QuestionSet> questionSets = apiClient.listQuestionSet();
        areQuestionSetsValid(questionSets);
    }

    @Test
    public void createBadPersonTest() throws ParseException {
        InvalidRequestException exception = null;
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        try {
            Verification verification = apiClient.createPerson(createBadTestPerson());
            isVerificationValid(verification);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            Assert.assertNotNull(e.getInvalidParam());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    public void getNonexistentVerification() {
        InvalidRequestException exception = null;
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        try {
            Verification verification = apiClient.getPerson("-1");
            isVerificationValid(verification);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    public void createQuestionSetWithFakeVerification() {
        InvalidRequestException exception = null;
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        try {
            QuestionSetRequest questionSetRequest = new QuestionSetRequest();
            questionSetRequest.setTimeLimit(0).setVerificationId("-1");
            QuestionSet questionSet = apiClient.createQuestionSet(questionSetRequest);
            isQuestionSetValid(questionSet);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    public void scoreNonExistentQuestionSet() {
        InvalidRequestException exception = null;
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        try {
            QuestionSet questionSet = apiClient.scoreQuestionSet("-1", new AnswerRequest());
            isQuestionSetValid(questionSet);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    public void scoreQuestionSetWithNoAnswers() throws ParseException {
        InvalidRequestException exception = null;
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        //Test creation of verification
        Verification verification = apiClient.createPerson(createTestPerson());
        isVerificationValid(verification);

        //Test getting verification
        verification = apiClient.getPerson(verification.getId());
        isVerificationValid(verification);

        //Test creation of a question set.
        QuestionSetRequest questionSetRequest = new QuestionSetRequest();
        questionSetRequest.setTimeLimit(100000).setVerificationId(verification.getId());
        QuestionSet questionSet = apiClient.createQuestionSet(questionSetRequest);
        isQuestionSetValid(questionSet);

        try {
            QuestionSet results = apiClient.scoreQuestionSet(questionSet.getId(), new AnswerRequest());
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
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        //Test creation of verification
        Verification verification = apiClient.createPerson(createTestPerson());
        isVerificationValid(verification);

        //Test getting verification
        verification = apiClient.getPerson(verification.getId());
        isVerificationValid(verification);

        //Test creation of a question set.
        QuestionSetRequest questionSetRequest = new QuestionSetRequest();
        questionSetRequest.setTimeLimit(100000).setVerificationId(verification.getId());
        QuestionSet questionSet = apiClient.createQuestionSet(questionSetRequest);
        isQuestionSetValid(questionSet);

        try {
            ArrayList<AnsweredQuestion> answered = new ArrayList<AnsweredQuestion>();
            for (Question question : questionSet.getQuestionSet()) {
                AnsweredQuestion answeredQuestion = new AnsweredQuestion(question.getId(), 1000000);
                answered.add(answeredQuestion);
            }
            AnswerRequest request = new AnswerRequest(answered);
            //Open issue for this: https://github.com/BlockScore/blockscore-api/issues/333
            //This should return an error, but instead allows it through. This code should be updated once the bug
            //is fixed.
            QuestionSet results = apiClient.scoreQuestionSet(questionSet.getId(), new AnswerRequest());
            isQuestionSetValid(results);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        //Assert.assertNotNull(exception); //Uncomment this once the issue is resolved to confirm the code works.
    }

    @Test
    public void getNonexistentQuestionSet() {
        InvalidRequestException exception = null;
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        try {
            QuestionSet questionSet = apiClient.getQuestionSet("-1");
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
        Person person = new Person();
        Name name = new Name("John", "Pearce", "Doe");
        Identification identification = new Identification();
        identification.setSSN("0000");
        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("1980-08-23");
        person.setName(name).setAddress(address).setDateOfBirth(date)
                .setIdentification(identification);
        return person;
    }

    /**
     * Generates a bad sample individual to use for this test suite.
     * @return Person to test with.
     * @throws ParseException
     */
    @NotNull
    private Person createBadTestPerson() throws ParseException {
        Person person = new Person();
        Identification identification = new Identification();
        identification.setSSN("0000");
        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("1980-08-23");
        person.setAddress(address).setDateOfBirth(date)
                .setIdentification(identification);
        return person;
    }

    /**
     * Checks for a valid verification list.
     * @param verificationList Verifications under test.
     */
    private void areVerificationsValid(@Nullable final List<Verification> verificationList) {
        Assert.assertNotNull(verificationList);
        for (Verification verification : verificationList) {
            isVerificationValid(verification);
        }
    }

    /**
     * Determines if the verification is valid.
     * @param verification Verification under test.
     */
    private void isVerificationValid(@NotNull final Verification verification) {
        Assert.assertNotNull(verification);
        Assert.assertNotNull(verification.getId());
        Assert.assertNotNull(verification.getDateOfBirth());
        Assert.assertNotNull(verification.getQuestionSets());

        areDetailsValid(verification.getDetails());
        isIdentificationValid(verification.getIdentification());
        isNameValid(verification.getName());
        isAddressValid(verification.getAddress());
    }

    /**
     * Checks if the details of this verification are complete.
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
        Assert.assertNotNull(questionSet.getVerificationId());
        areQuestionsValid(questionSet.getQuestionSet());
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
     * Checks to ensure a piece of identification is present.
     * @param identification Identification under test.
     */
    private void isIdentificationValid(@Nullable final Identification identification) {
        Assert.assertNotNull(identification);

        //This test could use some work, but this gets the job done for now.
        Assert.assertNotEquals("Passport and SSN cannot both be null!", identification.getPassport()
                , identification.getSSN());
    }

    /**
     * Examines the address and ensures it is valid.
     * @param address Address to test.
     */
    private void isAddressValid(@Nullable final Address address) {
        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getStreet1());
        Assert.assertNotNull(address.getState());
        Assert.assertNotNull(address.getPostalCode());
        Assert.assertNotNull(address.getCountryCode());
        Assert.assertNotNull(address.getCity());
    }

    /**
     * Examines the name and ensures it is valid.
     * @param name Name to test.
     */
    private void isNameValid(@Nullable final Name name) {
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
        BlockscoreApiClient.init("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
        BlockscoreApiClient.useVerboseLogs(true);
        return new BlockscoreApiClient();
    }
}
