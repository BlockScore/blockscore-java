import com.blockscore.models.*;
import com.blockscore.models.request.AnswerRequest;
import com.blockscore.models.request.QuestionSetRequest;
import com.blockscore.models.results.Verification;
import com.blockscore.net.BlockscoreApiClient;
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
    public void fullVerificationFlowTest() throws ParseException {
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(false);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();

        //Test creation of verification
        Verification verification = apiClient.createVerification(createTestPerson()).toBlocking().first();
        isVerificationValid(verification);

        //Test getting verification
        verification = apiClient.getVerification(verification.getId()).toBlocking().first();
        isVerificationValid(verification);

        //Test creation of a question set.
        QuestionSetRequest questionSetRequest = new QuestionSetRequest();
        questionSetRequest.setTimeLimit(100000).setVerificationId(verification.getId());
        QuestionSet questionSet = apiClient.createQuestionSet(questionSetRequest).toBlocking().first();
        isQuestionSetValid(questionSet);

        //Test scoring a question set.
        ArrayList<AnsweredQuestion> answered = new ArrayList<AnsweredQuestion>();
        for (Question question : questionSet.getQuestionSet()) {
            AnsweredQuestion answeredQuestion = new AnsweredQuestion(question.getId(), 1);
            answered.add(answeredQuestion);
        }
        AnswerRequest request = new AnswerRequest(answered);
        questionSet = apiClient.scoreQuestionSet(questionSet.getId(), request).toBlocking().first();
        isQuestionSetValid(questionSet);

        //Test getting a question set.
        questionSet = apiClient.getQuestionSet(questionSet.getId()).toBlocking().first();
        isQuestionSetValid(questionSet);

        //Test listing question sets.
        List<QuestionSet> questionSets = apiClient.listQuestionSet().toBlocking().first();
        areQuestionSetsValid(questionSets);
    }

    /**
     * Generates a sample individual to use for this test suite.
     * @return Person to test with.
     * @throws ParseException
     */
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
     * Determines if the verification is valid.
     * @param verification Verification under test.
     */
    private void isVerificationValid(@NotNull final Verification verification) {
        Assert.assertNotNull(verification);
        Assert.assertNotNull(verification.getId());
        Assert.assertNotNull(verification.getDateOfBirth());

        areDetailsValid(verification.getDetails());
        areQuestionSetsValid(verification.getQuestionSets());
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
}
