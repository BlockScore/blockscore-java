package com.blockscore.net;

import com.blockscore.models.*;
import com.blockscore.models.request.AnswerRequest;
import com.blockscore.models.request.SearchRequest;
import com.blockscore.models.results.WatchlistHit;
import com.blockscore.models.results.PaginatedResult;
import com.blockscore.models.results.WatchlistSearchResults;
import org.jetbrains.annotations.NotNull;
import retrofit.Callback;
import retrofit.http.*;
import rx.Observable;

import java.util.List;
import java.util.Map;

/**
 * Blockscore Java API interface.
 */
public interface BlockscoreRetrofitAPI {
    /**
     * <p>Creates a new Person. The information for the person will be run through the
     * Person process and returned with additional information that will help you
     * determine the authenticity of the information given. </p>
     * @param person Person to verify.
     */
    @NotNull
    @POST("/people")
    Person createPerson(@NotNull @Body final Person person);

   /**
     * <p>Retrieves a single Person. This will return a record exactly as it was when you created it.
     * This route is useful for auditing purposes as you can provide proof that a Person
     * took place along with all of its associated data. </p>
     * @param id ID of Person to verify.
     */
    @NotNull
    @GET("/people/{id}")
    Person retrievePerson(@NotNull @Path("id") String id);

    /**
     * <p>Gets a list of historical records for all Persons you have completed. Sorted
     * in reverse chronological order (newer people appear first). </p>
     */
    @NotNull
    @GET("/people")
    PaginatedResult<Person> listPeople();

    /**
     * <p>This method will create a new question set to ask your users. You can call this endpoint multiple
     * times with the same Person ID and the questions asked as well as the order that everything
     * is presented in will be randomized. </p>
     * @param request Question set request.
     */
    @NotNull
    @POST("/question_sets")
    QuestionSet createQuestionSet(@QueryMap Map<String, String> options);

    /**
     * <p>The scoring system will score it based on how many questions you submit, so if you would only like
     * to ask 3 questions, only submit the 3 questions which you would like scored. </p>
     * @param questionSetId Question set ID.
     * @param answers Question set request.
     */
    @NotNull
    @POST("/question_sets/{id}/score")
    QuestionSet scoreQuestionSet(@Path("id") @NotNull final String questionSetId
            , @NotNull @Body final AnswerRequest answers);

    /**
     * <p>This allows you to retrieve a question set you have created. If you have already scored the question
     * set, we will also return the last score of your submitted answers. </p>
     */
    @NotNull
    @GET("/question_sets/{id}")
    QuestionSet retrieveQuestionSet(@Path("id") @NotNull final String questionSetId);

 
    /**
     * <p>Allows you to see a historical record of all question sets that you have created.
     * The list is displayed in reverse chronological order (newer question sets appear first). </p>
     */
    @NotNull
    @GET("/question_sets")
    PaginatedResult<QuestionSet> listQuestionSets();

    /**
     * <p>Creates a new company. The information will be run through our company Person process
     * and then returned with additional information that will help you determine the authenticity of the information given.
     * Please be aware that the response time can sometimes be more than 6 seconds due to the speed of some
     * government data sources. </p>
     * @param company Company to create
     */
    @POST("/companies")
    Company createCompany(@NotNull @Body final Company company);

    /**
     * <p>You can pull up a single company Person at any time (typically this is used for auditing purposes). This will return a
     * record exactly as it was when you created it. This route is useful for auditing purposes as you can provide proof that a 
     * company verification took place along with all of its associated data.</p>
     * @param companyId Company ID.
     */
    @NotNull
    @GET("/companies/{id}")
    Company retrieveCompany(@Path("id") @NotNull final String companyId);

    /**
     * <p>This endpoint will allow you to see a historical record of all company Persons that you have completed.
     * Sorted in reverse chronological order (newer company verifications appear first). </p>
     */
    @NotNull
    @GET("/companies")
    PaginatedResult<Company> listCompanies();

    /**
     * <p>Creates a new candidate. </p>
     * @param candidate Candidate.
     */
    @NotNull
    @POST("/candidates")
    Candidate createCandidate(@NotNull @Body final Candidate candidate);

    /**
     * <p>Updates a candidate. </p>
     * @param id ID of candidate.
     * @param candidate Candidate.
     */
    @NotNull
    @PATCH("/candidates/{id}")
    Candidate updateCandidate(@NotNull @Path("id") final String id
            , @NotNull @Body final Candidate candidate);

    /**
     * <p>Deletes a candidate. </p>
     * @param id ID of candidate.
     */
    @NotNull
    @DELETE("/candidates/{id}")
    Candidate deleteCandidate(@NotNull @Path("id") final String id);

    /**
     * <p>Retrieves a candidate. </p>
     * @param id ID of candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @GET("/candidates/{id}")
    Candidate retrieveCandidate(@NotNull @Path("id") final String id);

    /**
     * <p>Retrieves a list of all candidates.</p>
     */
    @NotNull
    @GET("/candidates")
    PaginatedResult<Candidate> listCandidates();

    /**
     * <p>Retrieves the history for a candidate.</p>
     * @param id ID for the candidate.
     */
    @NotNull
    @GET("/candidates/{id}/history")
    List<Candidate> getCandidateHistory(@NotNull @Path("id") final String id);

    /**
     * <p>Gets the hits for a candidate.</p>
     * @param id ID for the candidate.
     */
    @NotNull
    @GET("/candidates/{id}/hits")
    PaginatedResult<WatchlistHit> getCandidateHits(@NotNull @Path("id") final String id);

    /**
     * Searches the watchlists for the candidate.
     * @param searchRequest Search inquiry
     */
    @NotNull
    @POST("/watchlists")
    WatchlistSearchResults searchWatchlists(@NotNull @Body final SearchRequest searchRequest);
}
