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
 * Created by Tony Dieppa on 9/29/14.
 */
public interface BlockscoreRetrofitAPI {
    /**
     * <p>Creates a new verification. The information for the person will be run through the
     * verification process and returned with additional information that will help you
     * determine the authenticity of the information given. </p>
     * Thread: Any [Determined by settings on Observable]
     * @param person Person to verify.
     * @return Observable containing the verification results.
     */
    @NotNull
    @POST("/verifications")
    Verification createVerification(@NotNull @Body final Person person);

   /**
     * <p>Pulls up a single verification. This will return a record exactly as it was when you created it.
     * This route is useful for auditing purposes as you can provide proof that a verification
     * took place along with all of its associated data. </p>
     * Thread: Any [Determined by settings on Observable]
     * @param id ID of verification to verify.
     * @return Observable containing a verification.
     */
    @NotNull
    @GET("/verifications/{id}")
    Verification getVerification(@NotNull @Path("id") String id);

    /**
     * <p>Gets a list of historical records for all verifications you have completed. Sorted
     * newest to oldest. </p>
     * Thread: Any [Determined by settings on Observable]
     * @return Observable containing a list of verifications.
     */
    @NotNull
    @GET("/verifications")
    List<Verification> listVerifications();

    /**
     * <p>This method will create a new question set to ask your users. You can call this endpoint multiple
     * times with the same verification ID and the questions asked as well as the order that everything
     * is presented in will be randomized. </p>
     * Thread: Any [Determined by settings on Observable]
     * @param request Question set request.
     * @return Observable containing a question set
     */
    @NotNull
    @POST("/questions")
    QuestionSet createQuestionSet(@NotNull @Body final QuestionSetRequest request);

    /**
     * <p>The scoring system will score it based on how many questions you submit, so if you would only like
     * to ask 3 questions, only submit the 3 questions which you would like scored. </p>
     * Thread: Any [Determined by settings on Observable]
     * @param questionSetId Question set ID.
     * @param answers Question set request.
     * @return Observable containing the question set
     */
    @NotNull
    @POST("/questions/{id}/score")
    QuestionSet scoreQuestionSet(@Path("id") @NotNull final String questionSetId
            , @NotNull @Body final AnswerRequest answers);

    /**
     * <p>This allows you to retrieve a question set you have created. If you have already scored the question
     * set, we will also return the last score of your submitted answers. </p>
     * Thread: Any [Determined by settings on Observable]
     * @param questionSetId Question set ID.
     * @return Observable containing the question set
     */
    @NotNull
    @GET("/questions/{id}")
    QuestionSet getQuestionSet(@Path("id") @NotNull final String questionSetId);

 
    /**
     * <p>Allows you to see a historical record of all question sets that you have created.
     * The list is displayed in reverse chronological order (newer question sets appear first). </p>
     * Thread: Any [Determined by settings on Observable]
     * @return Observable holding the list of question sets.
     */
    @NotNull
    @GET("/questions")
    List<QuestionSet> listQuestionSets();

    /**
     * <p>Creates a new company. The information will be run through our company verification process
     * and then returned with additional information that will help you determine the authenticity of the information given.
     * Please be aware that the response time can sometimes be more than 6 seconds due to the speed of some
     * government data sources. </p>
     * Thread: Any [Determined by settings on Observable]
     * @param company Company to create
     * @return Observable containing the Company validation.
     */
    @POST("/companies")
    Company createCompany(@NotNull @Body final Company company);

    /**
     * <p>You can pull up a single company verification at any time (typically this is used for auditing purposes). </p>
     * Thread: Any [Determined by settings on Observable]
     * @param companyId Company ID.
     * @return Observable containing the company
     */
    @NotNull
    @GET("/companies/{id}")
    Company getCompany(@Path("id") @NotNull final String companyId);

    /**
     * <p>This endpoint will allow you to see a historical record of all company verifications that you have completed.
     * Listed from newest to oldest</p>
     * Thread: Any [Determined by settings on Observable]
     * @return Observable holding the list of companies.
     */
    @NotNull
    @GET("/companies")
    List<Company> listCompanies();

    /**
     * <p>Creates a new watchlist candidate. </p>
     * Thread: Any [Determined by settings on Observable]
     * @param candidate Watchlist candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @POST("/watchlist_candidates")
    Candidate createCandidate(@NotNull @Body final Candidate candidate);

    /**
     * <p>Updates a watchlist candidate. </p>
     * Thread: Any [Determined by settings on Observable]
     * @param id ID of candidate.
     * @param candidate Watchlist candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @PATCH("/watchlist_candidates/{id}")
    Candidate updateCandidate(@NotNull @Path("id") final String id
            , @NotNull @Body final Candidate candidate);

    /**
     * <p>Deletes a watchlist candidate. </p>
     * Thread: Any [Determined by settings on Observable]
     * @param id ID of candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @DELETE("/watchlist_candidates/{id}")
    Candidate deleteCandidate(@NotNull @Path("id") final String id);

    /**
     * <p>Gets a watchlist candidate. </p>
     * Thread: Any [Determined by settings on Observable]
     * @param id ID of candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @GET("/watchlist_candidates/{id}")
    Candidate getCandidate(@NotNull @Path("id") final String id);

    /**
     * <p>Gets all watchlist candidates.</p>
     * Thread: Any [Determined by settings on Observable]
     * @return Observable holding the list of companies.
     */
    @NotNull
    @GET("/watchlist_candidates")
    List<Candidate> listCandidate();

    /**
     * <p>Gets the history for a watchlist candidate.</p>
     * Thread: Any [Determined by settings on Observable]
     * @param id ID for the candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @GET("/watchlist_candidates/{id}/history")
    List<Candidate> getCandidateHistory(@NotNull @Path("id") final String id);

    /**
     * <p>Gets the hits for a watchlist candidate.</p>
     * Thread: Any [Determined by settings on Observable]
     * @param id ID for the candidate.
     * @return Observable holding the list of companies.
     */
    @NotNull
    @GET("/watchlist_candidates/{id}/hits")
    List<WatchlistHit> getCandidateHits(@NotNull @Path("id") final String id);

    /**
     * Searches the watchlists for the candidate.
     * @param searchRequest Search inquiry
     * @return Observable containing search results.
     */
    @NotNull
    @POST("/watchlists")
    WatchlistSearchResults searchWatchlists(@NotNull @Body final SearchRequest searchRequest);
}
