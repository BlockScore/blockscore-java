package com.blockscore.net;

import com.blockscore.common.BlockscoreErrorType;
import com.blockscore.common.Constants;
import com.blockscore.exceptions.APIException;
import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.exceptions.NoApiKeyFoundException;
import com.blockscore.models.Company;
import com.blockscore.models.Person;
import com.blockscore.models.QuestionSet;
import com.blockscore.models.WatchlistCandidate;
import com.blockscore.models.error.BlockscoreError;
import com.blockscore.models.error.RequestError;
import com.blockscore.models.request.AnswerRequest;
import com.blockscore.models.request.QuestionSetRequest;
import com.blockscore.models.request.SearchRequest;
import com.blockscore.models.results.Verification;
import com.blockscore.models.results.WatchlistHit;
import com.blockscore.models.results.WatchlistSearchResults;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import org.jetbrains.annotations.NotNull;
import retrofit.*;
import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;
import retrofit.converter.JacksonConverter;
import rx.Observable;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The Blockscore Java API client.
 * Created by Tony Dieppa on 9/29/14.
 */
public class BlockscoreApiClient {
    private static RestAdapter.LogLevel sLogLevel = RestAdapter.LogLevel.NONE;
    private static String sApiKey;

    private final BlockscoreRetrofitAPI mRestAdapter;

    /**
     * Initializes the API client with the API key. Must be done first or else all calls will fail.
     * @param apiKey API key to use.
     */
    public static void init(@NotNull final String apiKey) {
        sApiKey = apiKey + ":";
    }

    /**
     * Turns on/off logging. Should be done after init(), but before API client usage.
     * @param useVerboseLogs True to use verbose network logs.
     */
    public static void useVerboseLogs(final boolean useVerboseLogs) {
        if (useVerboseLogs) {
            sLogLevel = RestAdapter.LogLevel.FULL;
        }
    }

    public BlockscoreApiClient() {
        RestAdapter.Builder restBuilder = new RestAdapter.Builder()
                .setClient(new BlockscoreClient())
                .setEndpoint(Constants.getDomain());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        JacksonConverter converter = new JacksonConverter(mapper);
        restBuilder.setConverter(converter);

        //Sets up the authentication headers and accept headers.
        restBuilder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader(Constants.AUTHORIZATION_HEADER, getEncodedAuthorization());
                request.addHeader(Constants.ACCEPT_HEADER, Constants.getAcceptHeaders());
            }
        });
        restBuilder.setErrorHandler(new BlockscoreErrorHandler());

        restBuilder.setLogLevel(sLogLevel);
        mRestAdapter = restBuilder.build().create(BlockscoreRetrofitAPI.class);
    }

    /**
     * Creates a new verification.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createVerification(com.blockscore.models.Person, retrofit.Callback)
     * @param person Person to verify.
     * @param callback Callback to use.
     */
    public void createVerification(@NotNull final Person person, @NotNull final Callback<Verification> callback) {
        mRestAdapter.createVerification(person, callback);
    }

    /**
     * Creates a new verification.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createVerification(com.blockscore.models.Person)
     * @param person Person to verify.
     * @return Observable containing the verification results.
     */
    @NotNull
    public Observable<Verification> createVerification(@NotNull final Person person) {
        return mRestAdapter.createVerification(person);
    }

    /**
     * Creates a new verification.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createVerification(com.blockscore.models.Person)
     * @param person Person to verify.
     * @return Verification
     */
    @NotNull
    public Verification createVerificationSync(@NotNull final Person person) {
        return mRestAdapter.createVerification(person).toBlocking().first();
    }

    /**
     * Pulls up a single verification.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getVerification(String, retrofit.Callback)
     * @param id ID of verification to verify.
     * @param callback Callback to use.
     */
    public void getVerification(@NotNull final String id, final Callback<Verification> callback) {
        mRestAdapter.getVerification(id, callback);
    }

    /**
     * Pulls up a single verification.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getVerification(String)
     * @param id ID of verification to verify.
     * @return Observable containing the verification results.
     */
    @NotNull
    public Observable<Verification> getVerification(@NotNull final String id) {
        return mRestAdapter.getVerification(id);
    }

    /**
     * Pulls up a single verification.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getVerification(String)
     * @param id ID of verification to verify.
     * @return Verification
     */
    @NotNull
    public Verification getVerificationSync(@NotNull final String id) {
        return mRestAdapter.getVerification(id).toBlocking().first();
    }

    /**
     * Gets a list of verifications.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#listVerifications(retrofit.Callback)
     * @param callback Callback to use.
     */
    public void listVerifications(@NotNull final Callback<List<Verification>> callback) {
        mRestAdapter.listVerifications(callback);
    }

    /**
     * Gets a list of verifications.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#listVerifications()
     * @return Observable containing the list of verification results.
     */
    @NotNull
    public Observable<List<Verification>> listVerifications() {
        return mRestAdapter.listVerifications();
    }

    /**
     * Gets a list of verifications.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#listVerifications()
     * @return The list of verification results.
     */
    @NotNull
    public List<Verification> listVerificationsSync() {
        return mRestAdapter.listVerifications().toBlocking().first();
    }

    /**
     * Creates a question set.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createQuestionSet(com.blockscore.models.request.QuestionSetRequest, retrofit.Callback)
     * @param request Question set request.
     * @param callback Callback to use.
     */
    public void createQuestionSet(@NotNull final QuestionSetRequest request
            , @NotNull final Callback<QuestionSet> callback) {
        mRestAdapter.createQuestionSet(request, callback);
    }

    /**
     * Creates a question set.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createQuestionSet(com.blockscore.models.request.QuestionSetRequest)
     * @param request Question set request.
     * @return Observable containing the question set.
     */
    @NotNull
    public Observable<QuestionSet> createQuestionSet(@NotNull final QuestionSetRequest request) {
        return mRestAdapter.createQuestionSet(request);
    }

    /**
     * Creates a question set.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createQuestionSet(com.blockscore.models.request.QuestionSetRequest)
     * @param request Question set request.
     * @return The question set.
     */
    @NotNull
    public QuestionSet createQuestionSetSync(@NotNull final QuestionSetRequest request) {
        return mRestAdapter.createQuestionSet(request).toBlocking().first();
    }

    /**
     * Scores a question set.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#scoreQuestionSet(String, com.blockscore.models.request.AnswerRequest, retrofit.Callback)
     * @param questionSetId Question set ID
     * @param answers Answers to questions
     * @param callback Callback to use.
     */
    public void scoreQuestionSet(@NotNull final String questionSetId
            , @NotNull final AnswerRequest answers
            , @NotNull final Callback<QuestionSet> callback) {
        mRestAdapter.scoreQuestionSet(questionSetId, answers, callback);
    }

    /**
     * Scores a question set.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#scoreQuestionSet(String, com.blockscore.models.request.AnswerRequest)
     * @param questionSetId Question set ID
     * @param answers Answers to questions
     * @return Observable containing the question set.
     */
    @NotNull
    public Observable<QuestionSet> scoreQuestionSet(@NotNull final String questionSetId
            , @NotNull final AnswerRequest answers) {
        return mRestAdapter.scoreQuestionSet(questionSetId, answers);
    }

    /**
     * Scores a question set.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#scoreQuestionSet(String, com.blockscore.models.request.AnswerRequest)
     * @param questionSetId Question set ID
     * @param answers Answers to questions
     * @return Observable containing the question set.
     */
    @NotNull
    public QuestionSet scoreQuestionSetSync(@NotNull final String questionSetId
            , @NotNull final AnswerRequest answers) {
        return mRestAdapter.scoreQuestionSet(questionSetId, answers).toBlocking().first();
    }

    /**
     * This allows you to retrieve a question set you have created.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getQuestionSet(String, retrofit.Callback)
     * @param questionSetId Question set ID
     * @param callback Callback to use.
     */
    public void getQuestionSet(@NotNull final String questionSetId
            , @NotNull final Callback<QuestionSet> callback) {
        mRestAdapter.getQuestionSet(questionSetId, callback);
    }

    /**
     * This allows you to retrieve a question set you have created.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getQuestionSet(String)
     * @param questionSetId Question set ID
     * @return Observable containing the question set.
     */
    public Observable<QuestionSet> getQuestionSet(@NotNull final String questionSetId) {
        return mRestAdapter.getQuestionSet(questionSetId);
    }

    /**
     * This allows you to retrieve a question set you have created.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getQuestionSet(String)
     * @param questionSetId Question set ID
     * @return The question set.
     */
    public QuestionSet getQuestionSetSync(@NotNull final String questionSetId) {
        return mRestAdapter.getQuestionSet(questionSetId).toBlocking().first();
    }

    /**
     * This allows you to retrieve a question set you have created.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#listQuestionSets(retrofit.Callback)
     * @param callback Callback to use.
     */
    public void listQuestionSet(@NotNull final Callback<List<QuestionSet>> callback) {
        mRestAdapter.listQuestionSets(callback);
    }

    /**
     * This allows you to retrieve a question set you have created.
     * @see BlockscoreRetrofitAPI#listQuestionSets()
     * @return Observable containing the question set.
     */
    @NotNull
    public Observable<List<QuestionSet>> listQuestionSet() {
        return mRestAdapter.listQuestionSets();
    }

    /**
     * This allows you to retrieve a question set you have created.
     * @see BlockscoreRetrofitAPI#listQuestionSets()
     * @return List of question sets.
     */
    @NotNull
    public List<QuestionSet> listQuestionSetSync() {
        return mRestAdapter.listQuestionSets().toBlocking().first();
    }

    /**
     * Creates a company.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCompany(com.blockscore.models.Company, retrofit.Callback)
     * @param company Company to create.
     * @param callback Callback to use.
     */
    public void createCompany(@NotNull final Company company, @NotNull final Callback<Company> callback) {
        mRestAdapter.createCompany(company, callback);
    }

    /**
     * Creates a company.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCompany(com.blockscore.models.Company)
     * @param company Company to create.
     * @return Observable containing the company.
     */
    @NotNull
    public Observable<Company> createCompany(@NotNull final Company company) {
        return mRestAdapter.createCompany(company);
    }

    /**
     * Creates a company.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCompany(com.blockscore.models.Company)
     * @param company Company to create.
     * @return Company.
     */
    @NotNull
    public Company createCompanySync(@NotNull final Company company) {
        return mRestAdapter.createCompany(company).toBlocking().first();
    }

    /**
     * Retrieves a company.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCompany(com.blockscore.models.Company, retrofit.Callback)
     * @param id Company ID.
     * @param callback Callback to use.
     */
    public void getCompany(@NotNull final String id, @NotNull final Callback<Company> callback) {
        mRestAdapter.getCompany(id, callback);
    }

    /**
     * Retrieves a company.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCompany(com.blockscore.models.Company)
     * @param id Company ID.
     * @return Observable containing the company.
     */
    @NotNull
    public Observable<Company> getCompany(@NotNull final String id) {
        return mRestAdapter.getCompany(id);
    }

    /**
     * Retrieves a company.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCompany(com.blockscore.models.Company)
     * @param id Company ID.
     * @return Company.
     */
    @NotNull
    public Company getCompanySync(@NotNull final String id) {
        return mRestAdapter.getCompany(id).toBlocking().first();
    }

    /**
     * Lists your verified companies.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#listCompanies(retrofit.Callback)
     * @param callback Callback to use.
     */
    public void listCompanies(@NotNull final Callback<List<Company>> callback) {
        mRestAdapter.listCompanies(callback);
    }

    /**
     * Lists your verified companies.
     * @see BlockscoreRetrofitAPI#listCompanies()
     * @return Observable containing the list of companies.
     */
    @NotNull
    public Observable<List<Company>> listCompanies() {
        return mRestAdapter.listCompanies();
    }

    /**
     * Lists your verified companies.
     * @see BlockscoreRetrofitAPI#listCompanies()
     * @return List of companies.
     */
    @NotNull
    public List<Company> listCompaniesSync() {
        return mRestAdapter.listCompanies().toBlocking().first();
    }

    /**
     * Creates a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createWatchlistCandidate(com.blockscore.models.WatchlistCandidate, retrofit.Callback)
     * @param candidate Watchlist candidate to create.
     * @param callback Callback to use.
     */
    public void createWatchlistCandidate(@NotNull final WatchlistCandidate candidate
            , @NotNull final Callback<WatchlistCandidate> callback) {
        mRestAdapter.createWatchlistCandidate(candidate, callback);
    }

    /**
     * Creates a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createWatchlistCandidate(com.blockscore.models.WatchlistCandidate)
     * @param candidate Watchlist candidate to create.
     * @return Observable containing the candidate.
     */
    @NotNull
    public Observable<WatchlistCandidate> createWatchlistCandidate(@NotNull final WatchlistCandidate candidate) {
        return mRestAdapter.createWatchlistCandidate(candidate);
    }

    /**
     * Creates a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createWatchlistCandidate(com.blockscore.models.WatchlistCandidate)
     * @param candidate Watchlist candidate to create.
     * @return Candidate.
     */
    @NotNull
    public WatchlistCandidate createWatchlistCandidateSync(@NotNull final WatchlistCandidate candidate) {
        return mRestAdapter.createWatchlistCandidate(candidate).toBlocking().first();
    }

    /**
     * Updates a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#updateWatchlistCandidate(String, com.blockscore.models.WatchlistCandidate, retrofit.Callback)
     * @param id ID for the candidate.
     * @param candidate Watchlist candidate to create.
     * @param callback Callback to use.
     */
    public void updateWatchlistCandidate(@NotNull final String id, @NotNull final WatchlistCandidate candidate
            , @NotNull final Callback<WatchlistCandidate> callback) {
        mRestAdapter.updateWatchlistCandidate(id, candidate, callback);
    }

    /**
     * Updates a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#updateWatchlistCandidate(String, com.blockscore.models.WatchlistCandidate)
     * @param id ID for the candidate.
     * @param candidate Watchlist candidate to create.
     * @return Observable containing the candidate.
     */
    @NotNull
    public Observable<WatchlistCandidate> updateWatchlistCandidate(@NotNull final String id
            , @NotNull final WatchlistCandidate candidate) {
        return mRestAdapter.updateWatchlistCandidate(id, candidate);
    }

    /**
     * Updates a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#updateWatchlistCandidate(String, com.blockscore.models.WatchlistCandidate)
     * @param id ID for the candidate.
     * @param candidate Watchlist candidate to create.
     * @return Candidate.
     */
    @NotNull
    public WatchlistCandidate updateWatchlistCandidateSync(@NotNull final String id
            , @NotNull final WatchlistCandidate candidate) {
        return mRestAdapter.updateWatchlistCandidate(id, candidate).toBlocking().first();
    }

    /**
     * Gets a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getWatchlistCandidate(String, retrofit.Callback)
     * @param id ID for the candidate.
     * @param callback Callback to use.
     */
    public void getWatchlistCandidate(@NotNull final String id, @NotNull final Callback<WatchlistCandidate> callback) {
        mRestAdapter.getWatchlistCandidate(id, callback);
    }

    /**
     * Gets a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getWatchlistCandidate(String)
     * @param id ID for the candidate.
     * @return Observable containing the candidate
     */
    @NotNull
    public Observable<WatchlistCandidate> getWatchlistCandidate(@NotNull final String id) {
        return mRestAdapter.getWatchlistCandidate(id);
    }

    /**
     * Gets a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getWatchlistCandidate(String)
     * @param id ID for the candidate.
     * @return Candidate
     */
    @NotNull
    public WatchlistCandidate getWatchlistCandidateSync(@NotNull final String id) {
        return mRestAdapter.getWatchlistCandidate(id).toBlocking().first();
    }

    /**
     * Lists the watchlist candidates.
     * @see BlockscoreRetrofitAPI#listWatchlistCandidate(retrofit.Callback)
     * @param callback Callback to use.
     */
    public void listWatchlistCandidate(@NotNull final Callback<List<WatchlistCandidate>> callback) {
        mRestAdapter.listWatchlistCandidate(callback);
    }

    /**
     * Lists the watchlist candidates.
     * @see BlockscoreRetrofitAPI#listWatchlistCandidate()
     * @return Observable containing the response.
     */
    @NotNull
    public Observable<List<WatchlistCandidate>> listWatchlistCandidate() {
        return mRestAdapter.listWatchlistCandidate();
    }

    /**
     * Lists the watchlist candidates.
     * @see BlockscoreRetrofitAPI#listWatchlistCandidate()
     * @return Response.
     */
    @NotNull
    public List<WatchlistCandidate> listWatchlistCandidateSync() {
        return mRestAdapter.listWatchlistCandidate().toBlocking().first();
    }

    /**
     * Gets the watchlist candidate's history.
     * @param id ID for the candidate.
     * @param callback Callback to use.
     */
    public void getWatchlistCandidateHistory(@NotNull final String id
            , @NotNull final Callback<WatchlistCandidate> callback) {
        mRestAdapter.getWatchlistCandidateHistory(id, callback);
    }

    /**
     * Gets the watchlist candidate's history.
     * @param id ID for the candidate.
     * @return Observable containing a list of watchlist candidates.
     */
    @NotNull
    public Observable<List<WatchlistCandidate>> getWatchlistCandidateHistory(@NotNull final String id) {
        return mRestAdapter.getWatchlistCandidateHistory(id);
    }

    /**
     * Gets the watchlist candidate's history.
     * @param id ID for the candidate.
     * @return List of watchlist candidates.
     */
    @NotNull
    public List<WatchlistCandidate> getWatchlistCandidateHistorySync(@NotNull final String id) {
        return mRestAdapter.getWatchlistCandidateHistory(id).toBlocking().first();
    }

    /**
     * Deletes a watchlist candidate.
     * @param id ID for the candidate.
     * @param callback Callback to use.
     */
    public void deleteWatchlistCandidate(@NotNull final String id
            , @NotNull final Callback<WatchlistCandidate> callback) {
        mRestAdapter.deleteWatchlistCandidate(id, callback);
    }

    /**
     * Deletes a watchlist candidate.
     * @param id ID for the candidate.
     * @return Observable containing the watchlist candidate.
     */
    @NotNull
    public Observable<WatchlistCandidate> deleteWatchlistCandidate(@NotNull final String id) {
        return mRestAdapter.deleteWatchlistCandidate(id);
    }

    /**
     * Deletes a watchlist candidate.
     * @param id ID for the candidate.
     * @return Watchlist candidate.
     */
    @NotNull
    public WatchlistCandidate deleteWatchlistCandidateSync(@NotNull final String id) {
        return mRestAdapter.deleteWatchlistCandidate(id).toBlocking().first();
    }

    /**
     * Gets the hits for a watchlist candidate.
     * @param id ID for the candidate.
     * @param callback Callback to use.
     */
    public void getWatchlistCandidateHits(@NotNull final String id
            , @NotNull final Callback<WatchlistHit> callback) {
        mRestAdapter.getWatchlistCandidateHits(id, callback);
    }

    /**
     * Gets the hits for a watchlist candidate.
     * @param id ID for the candidate.
     * @return Observable containing the list of watchlist hits.
     */
    @NotNull
    public Observable<List<WatchlistHit>> getWatchlistCandidateHits(@NotNull final String id) {
        return mRestAdapter.getWatchlistCandidateHits(id);
    }

    /**
     * Gets the hits for a watchlist candidate.
     * @param id ID for the candidate.
     * @return List of watchlist hits.
     */
    @NotNull
    public List<WatchlistHit> getWatchlistCandidateHitsSync(@NotNull final String id) {
        return mRestAdapter.getWatchlistCandidateHits(id).toBlocking().first();
    }

    /**
     * Searches watchlists for a given candidate.
     * @param searchRequest Search request to complete
     * @param callback Callback to use.
     */
    public void searchWatchlists(@NotNull final SearchRequest searchRequest
            , @NotNull final Callback<WatchlistSearchResults> callback) {
        mRestAdapter.searchWatchlists(searchRequest, callback);
    }

    /**
     * Searches watchlists for a given candidate.
     * @param searchRequest Search request to complete
     * @return Observable containing the watch list search results.
     */
    @NotNull
    public Observable<WatchlistSearchResults> searchWatchlists(@NotNull final SearchRequest searchRequest) {
        return mRestAdapter.searchWatchlists(searchRequest);
    }

    /**
     * Searches watchlists for a given candidate.
     * @param searchRequest Search request to complete
     * @return Watch list search results.
     */
    @NotNull
    public WatchlistSearchResults searchWatchlistsSync(@NotNull final SearchRequest searchRequest) {
        return mRestAdapter.searchWatchlists(searchRequest).toBlocking().first();
    }

    /**
     * Encodes the API key for Basic authentication.
     * @return API key encoded with Base 64.
     */
    @NotNull
    private String getEncodedAuthorization() {
        if (sApiKey == null || sApiKey.isEmpty()) {
            throw new NoApiKeyFoundException();
        }

        try {
            return "Basic " + Base64.getEncoder().encodeToString(sApiKey.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new NoApiKeyFoundException("UTF-8 encoding is not supported by your configuration. This is required.");
        }
    }

    /**
     * Handles the network layer.
     */
    private static final class BlockscoreClient extends UrlConnectionClient {
        private static final int CONNECT_TIMEOUT_MILLIS = 30 * 1000;
        private static final int READ_TIMEOUT_MILLIS = 30 * 1000;

        private final OkUrlFactory mOkUrlFactory;

        public BlockscoreClient() {
            mOkUrlFactory = new OkUrlFactory(generateHTTPClient());
        }

        public BlockscoreClient(OkHttpClient client) {
            mOkUrlFactory = new OkUrlFactory(client);
        }

        private OkHttpClient generateHTTPClient() {
            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            client.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            return client;
        }

        @Override
        protected HttpURLConnection openConnection(Request request) throws IOException {
            return mOkUrlFactory.open(new URL(request.getUrl()));
        }
    }

    private static final class BlockscoreErrorHandler implements ErrorHandler {
        @Override
        public Throwable handleError(RetrofitError cause) {
            Object rawError = cause.getBodyAs(BlockscoreError.class);
            if (rawError instanceof BlockscoreError) {
                BlockscoreError error = (BlockscoreError) rawError;
                RequestError requestError = error.getError();
                if (requestError.getErrorType() == BlockscoreErrorType.INVALID) {
                    return new InvalidRequestException(error);
                } else if (requestError.getErrorType() == BlockscoreErrorType.API) {
                    return new APIException(error);
                } else {
                    //Theoretically, this should never happen, unless the API has changed to break something.
                    String msg = String.format("An unknown error has occurred. Please contact support. Error type: %s"
                            , requestError.getErrorType().toString());
                    return new RuntimeException(msg);
                }
            }
            return cause;
        }
    }
}
