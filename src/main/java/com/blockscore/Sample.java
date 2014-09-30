package com.blockscore;

import com.blockscore.models.*;
import com.blockscore.models.request.AnswerRequest;
import com.blockscore.models.request.QuestionSetRequest;
import com.blockscore.net.BlockscoreApiClient;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Example class on how to use the Blockscore API client.
 */
public class Sample {
    public static void main(final String[] args) {
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(true);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();

        Person person = new Person();
        Name name = new Name("John", "Pearce", "Doe");
        Identification identification = new Identification();
        identification.setSSN("0000");
        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse("1980-08-23");
        } catch (ParseException e) {
            //Do nothing.
        }

        person.setName(name).setAddress(address).setDateOfBirth(date)
                .setIdentification(identification);

        Observable<Verification> step1 = apiClient.createVerification(person);
        Observable<Verification> step2 = step1.map(new Func1<Verification, Verification>() {
            @Override
            public Verification call(Verification verification) {
                System.out.println("Getting verification");
                return apiClient.getVerification(verification.getId()).toBlocking().first();
            }
        });
        Observable<QuestionSet> step3 = step2.map(new Func1<Verification, QuestionSet>() {
            @Override
            public QuestionSet call(Verification verification) {
                System.out.println("Creating question set.");
                QuestionSetRequest questionSetRequest = new QuestionSetRequest();
                questionSetRequest.setTimeLimit(100000).setVerificationId(verification.getId());
                return apiClient.createQuestionSet(questionSetRequest).toBlocking().first();
            }
        });
        Observable<QuestionSet> step4 = step3.map(new Func1<QuestionSet, QuestionSet>() {
            @Override
            public QuestionSet call(QuestionSet questionSet) {
                List<Question> questionList = questionSet.getQuestionSet();
                ArrayList<AnsweredQuestion> answered = new ArrayList<AnsweredQuestion>();
                for (Question question : questionList) {
                    AnsweredQuestion answeredQuestion = new AnsweredQuestion(question.getId(), 1);
                    answered.add(answeredQuestion);
                }
                AnswerRequest request = new AnswerRequest(answered);
                return apiClient.scoreQuestionSet(questionSet.getId(), request).toBlocking().first();
            }
        });


        step4.subscribe(new Observer<QuestionSet>() {
            @Override
            public void onCompleted() {
                System.out.println("Victory");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(QuestionSet questionSet) {

            }
        });
    }
}
