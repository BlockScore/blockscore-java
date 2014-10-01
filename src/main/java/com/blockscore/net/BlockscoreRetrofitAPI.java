package com.blockscore.net;

import com.blockscore.models.*;
import com.blockscore.models.request.AnswerRequest;
import com.blockscore.models.request.QuestionSetRequest;
import com.blockscore.models.request.SearchRequest;
import com.blockscore.models.results.Verification;
import com.blockscore.models.results.WatchlistHit;
import com.blockscore.models.results.WatchlistSearchResults;
import org.jetbrains.annotations.NotNull;
import retrofit.Callback;
import retrofit.http.*;
import rx.Observable;

import java.util.List;

/**
 * Blockscore Java API interface.
 * Created by tealocean on 9/29/14.
 */
public interface BlockscoreRetrofitAPI {
    /**
     * Creates a new verification. The information for the person will be run through the
     * verification process and returned with additional information that will help you
     * determine the authenticity of the information given. <br />
     * Thread: Asynchronous <br />
     * @param person Person to verify.
     * @param callback Callback to use.
     */
    @POST("/verifications")
    void createVerification(@NotNull @Body final Person person, @NotNull final Callback<Verification> callback);

    /**
     * Creates a new verification. The information for the person will be run through the
     * verification process and returned with additional information that will help you
     * determine the authenticity of the information given. <br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param person Person to verify.
     * @return Observable containing the verification results.
     */
    @NotNull
    @POST("/verifications")
    Observable<Verification> createVerification(@NotNull @Body final Person person);

    /**
     * Pulls up a single verification. This will return a record exactly as it was when you created it.
     * This route is useful for auditing purposes as you can provide proof that a verification
     * took place along with all of its associated data. <br />
     * Thread: Asynchronous <br />
     * @param id ID of verification to verify.
     * @param callback Callback to use.
     */
    @GET("/verifications/{id}")
    void getVerification(@NotNull @Path("id") String id, @NotNull final Callback<Verification> callback);

    /**
     * Pulls up a single verification. This will return a record exactly as it was when you created it.
     * This route is useful for auditing purposes as you can provide proof that a verification
     * took place along with all of its associated data. <br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param id ID of verification to verify.
     */
    @NotNull
    @GET("/verifications/{id}")
    Observable<Verification> getVerification(@NotNull @Path("id") String id);

    /**
     * Gets a list of historical records for all verifications you have completed. Sorted
     * newest to oldest. <br />
     * Thread: Asynchronous <br />
     * @param callback Callback to use.
     */
    @GET("/verifications")
    void listVerifications(@NotNull final Callback<List<Verification>> callback);

    /**
     * Gets a list of historical records for all verifications you have completed. Sorted
     * newest to oldest. <br />
     * Thread: Any [Determined by settings on Observable] <br />
     */
    @NotNull
    @GET("/verifications")
    Observable<List<Verification>> listVerifications();

    /**
     * This method will create a new question set to ask your users. You can call this endpoint multiple
     * times with the same verification ID and the questions asked as well as the order that everything
     * is presented in will be randomized. <br />
     * Thread: Asynchronous <br />
     * @param request Question set request.
     * @param callback Callback to use.
     */
    @POST("/questions")
    void createQuestionSet(@NotNull @Body final QuestionSetRequest request
            , @NotNull final Callback<QuestionSet> callback);

    /**
     * This method will create a new question set to ask your users. You can call this endpoint multiple
     * times with the same verification ID and the questions asked as well as the order that everything
     * is presented in will be randomized. <br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param request Question set request.
     */
    @NotNull
    @POST("/questions")
    Observable<QuestionSet> createQuestionSet(@NotNull @Body final QuestionSetRequest request);

    /**
     * The scoring system will score it based on how many questions you submit, so if you would only like
     * to ask 3 questions, only submit the 3 questions which you would like scored. <br />
     * Thread: Asynchronous <br />
     * @param answers Question set request.
     * @param callback Callback to use.
     */
    @POST("/questions/{id}/score")
    void scoreQuestionSet(@Path("id") @NotNull final String questionSetId
            , @NotNull @Body final AnswerRequest answers
            , @NotNull final Callback<QuestionSet> callback);

    /**
     * The scoring system will score it based on how many questions you submit, so if you would only like
     * to ask 3 questions, only submit the 3 questions which you would like scored. <br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param answers Question set request.
     */
    @NotNull
    @POST("/questions/{id}/score")
    Observable<QuestionSet> scoreQuestionSet(@Path("id") @NotNull final String questionSetId
            , @NotNull @Body final AnswerRequest answers);

    /**
     * This allows you to retrieve a question set you have created. If you have already scored the question
     * set, we will also return the last score of your submitted answers. <br />
     * Thread: Asynchronous <br />
     * @param questionSetId Question set ID.
     * @param callback Callback to use.
     */
    @GET("/questions/{id}")
    void getQuestionSet(@Path("id") @NotNull final String questionSetId
            , @NotNull final Callback<QuestionSet> callback);

    /**
     * This allows you to retrieve a question set you have created. If you have already scored the question
     * set, we will also return the last score of your submitted answers. <br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param questionSetId Question set ID.
     */
    @NotNull
    @GET("/questions/{id}")
    Observable<QuestionSet> getQuestionSet(@Path("id") @NotNull final String questionSetId);

    /**
     * Gets a list of historical records for all verifications you have completed. Sorted
     * newest to oldest. <br />
     * Thread: Asynchronous <br />
     * @param callback Callback to use.
     */
    @GET("/questions")
    void listQuestionSets(@NotNull final Callback<List<QuestionSet>> callback);

    /**
     * Allows you to see a historical record of all question sets that you have created.
     * The list is displayed in reverse chronological order (newer question sets appear first). <br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @return Observable holding the list of question sets.
     */
    @NotNull
    @GET("/questions")
    Observable<List<QuestionSet>> listQuestionSets();

    /**
     * Creates a new company. The information will be run through our company verification process
     * and then returned with additional information that will help you determine the authenticity of the information given.
     * Please be aware that the response time can sometimes be more than 6 seconds due to the speed of some
     * government data sources.
     * Thread: Asynchronous <br />
     * @param callback Callback to use.
     */
    @POST("/companies")
    void createCompany(@NotNull @Body final Company company, @NotNull final Callback<Company> callback);

    /**
     * Creates a new company. The information will be run through our company verification process
     * and then returned with additional information that will help you determine the authenticity of the information given.
     * Please be aware that the response time can sometimes be more than 6 seconds due to the speed of some
     * government data sources.
     * Thread: Any [Determined by settings on Observable] <br />
     * @return Observable containing the Company validation.
     */
    @POST("/companies")
    Observable<Company> createCompany(@NotNull @Body final Company company);

    /**
     * You can pull up a single company verification at any time (typically this is used for auditing purposes). <br />
     * Thread: Asynchronous <br />
     * @param companyId Company ID.
     * @param callback Callback to use.
     */
    @GET("/companies/{id}")
    void getCompany(@Path("id") @NotNull final String companyId, @NotNull final Callback<Company> callback);

    /**
     * You can pull up a single company verification at any time (typically this is used for auditing purposes). <br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param companyId Company ID.
     */
    @NotNull
    @GET("/companies/{id}")
    Observable<Company> getCompany(@Path("id") @NotNull final String companyId);

    /**
     * This endpoint will allow you to see a historical record of all company verifications that you have completed.
     * Listed from newest to oldest<br />
     * Thread: Asynchronous <br />
     * @param callback Callback to use.
     */
    @GET("/companies")
    void listCompanies(@NotNull final Callback<List<Company>> callback);

    /**
     * This endpoint will allow you to see a historical record of all company verifications that you have completed.
     * Listed from newest to oldest<br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @return Observable holding the list of companies.
     */
    @NotNull
    @GET("/companies")
    Observable<List<Company>> listCompanies();

    /**
     * Creates a new watchlist candidate<br />
     * Thread: Asynchronous <br />
     * @param candidate Watchlist candidate.
     * @param callback Callback to use.
     */
    @POST("/watchlist_candidates")
    void createWatchlistCandidate(@NotNull @Body final WatchlistCandidate candidate
            , @NotNull final Callback<WatchlistCandidate> callback);

    /**
     * Creates a new watchlist candidate<br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param candidate Watchlist candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @POST("/watchlist_candidates")
    Observable<WatchlistCandidate> createWatchlistCandidate(@NotNull @Body final WatchlistCandidate candidate);

    /**
     * Updates a watchlist candidate<br />
     * Thread: Asynchronous <br />
     * @param id ID of candidate.
     * @param candidate Watchlist candidate.
     * @param callback Callback to use.
     */
    @PATCH("/watchlist_candidates/{id}")
    void updateWatchlistCandidate(@NotNull @Path("id") final String id, @NotNull @Body final WatchlistCandidate candidate
            , @NotNull final Callback<WatchlistCandidate> callback);

    /**
     * Updates a watchlist candidate<br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param id ID of candidate.
     * @param candidate Watchlist candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @PATCH("/watchlist_candidates/{id}")
    Observable<WatchlistCandidate> updateWatchlistCandidate(@NotNull @Path("id") final String id
            , @NotNull @Body final WatchlistCandidate candidate);

    /**
     * Deletes a watchlist candidate<br />
     * Thread: Asynchronous <br />
     * @param id ID of candidate.
     * @param callback Callback to use.
     */
    @DELETE("/watchlist_candidates/{id}")
    void deleteWatchlistCandidate(@NotNull @Path("id") final String id, @NotNull final Callback<WatchlistCandidate> callback);

    /**
     * Deletes a watchlist candidate<br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param id ID of candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @DELETE("/watchlist_candidates/{id}")
    Observable<WatchlistCandidate> deleteWatchlistCandidate(@NotNull @Path("id") final String id);

    /**
     * Gets a watchlist candidate<br />
     * Thread: Asynchronous <br />
     * @param id ID of candidate.
     * @param callback Callback to use.
     */
    @GET("/watchlist_candidates/{id}")
    void getWatchlistCandidate(@NotNull @Path("id") final String id, @NotNull final Callback<WatchlistCandidate> callback);

    /**
     * Gets a watchlist candidate<br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param id ID of candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @GET("/watchlist_candidates/{id}")
    Observable<WatchlistCandidate> getWatchlistCandidate(@NotNull @Path("id") final String id);

    /**
     * Gets all watchlist candidates<br />
     * Thread: Asynchronous <br />
     * @param callback Callback to use.
     */
    @GET("/watchlist_candidates")
    void listWatchlistCandidate(@NotNull final Callback<List<WatchlistCandidate>> callback);

    /**
     * Gets all watchlist candidates<br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @return Observable holding the list of companies.
     */
    @NotNull
    @GET("/watchlist_candidates")
    Observable<List<WatchlistCandidate>> listWatchlistCandidate();

    /**
     * Gets the history for a watchlist candidate.<br />
     * Thread: Asynchronous <br />
     * @param id ID for the candidate.
     * @param callback Callback to use.
     */
    @GET("/watchlist_candidates/{id}/history")
    void getWatchlistCandidateHistory(@NotNull @Path("id") final String id
            , @NotNull final Callback<WatchlistCandidate> callback);

    /**
     * Gets the history for a watchlist candidate.<br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param id ID for the candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @GET("/watchlist_candidates/{id}/history")
    Observable<List<WatchlistCandidate>> getWatchlistCandidateHistory(@NotNull @Path("id") final String id);

    /**
     * Gets the hits for a watchlist candidate.<br />
     * Thread: Asynchronous <br />
     * @param id ID for the candidate.
     * @param callback Callback to use.
     */
    @GET("/watchlist_candidates/{id}/hits")
    void getWatchlistCandidateHits(@NotNull @Path("id") final String id
            , @NotNull final Callback<WatchlistHit> callback);

    /**
     * Gets the hits for a watchlist candidate.<br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param id ID for the candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @GET("/watchlist_candidates/{id}/hits")
    Observable<List<WatchlistHit>> getWatchlistCandidateHits(@NotNull @Path("id") final String id);

    /**
     * Searches the watchlists for the candidate.
     * @param searchRequest Search inquiry
     */
    @POST("/watchlists")
    void searchWatchlists(@NotNull final SearchRequest searchRequest
            , @NotNull final Callback<WatchlistSearchResults> callback);

    /**
     * Searches the watchlists for the candidate.
     * @param searchRequest Search inquiry
     */
    @POST("/watchlists")
    Observable<WatchlistSearchResults> searchWatchlists(@NotNull final SearchRequest searchRequest);
}
