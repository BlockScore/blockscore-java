package com.blockscore.net;

import com.blockscore.common.BlockscoreErrorType;
import com.blockscore.common.Constants;
import com.blockscore.exceptions.APIException;
import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.exceptions.NoApiKeyFoundException;
import com.blockscore.models.Company;
import com.blockscore.models.Person;
import com.blockscore.models.QuestionSet;
import com.blockscore.models.Candidate;
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
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createVerification(com.blockscore.models.Person)
     * @param person Person to verify.
     * @return Verification
     */
    @NotNull
    public Verification createPerson(@NotNull final Person person) {
        return mRestAdapter.createVerification(person);
    }

    /**
     * Pulls up a single verification.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getVerification(String)
     * @param id ID of verification to verify.
     * @return Verification
     */
    @NotNull
    public Verification getPerson(@NotNull final String id) {
        return mRestAdapter.getVerification(id);
    }

    /**
     * Gets a list of verifications.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#listVerifications()
     * @return The list of verification results.
     */
    @NotNull
    public List<Verification> listPeople() {
        return mRestAdapter.listVerifications();
    }

    /**
     * Creates a question set.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createQuestionSet(com.blockscore.models.request.QuestionSetRequest)
     * @param request Question set request.
     * @return The question set.
     */
    @NotNull
    public QuestionSet createQuestionSet(@NotNull final QuestionSetRequest request) {
        return mRestAdapter.createQuestionSet(request);
    }

    /**
     * Scores a question set.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#scoreQuestionSet(String, com.blockscore.models.request.AnswerRequest)
     * @param questionSetId Question set ID
     * @param answers Answers to questions
     * @return Observable containing the question set.
     */
    @NotNull
    public QuestionSet scoreQuestionSet(@NotNull final String questionSetId
            , @NotNull final AnswerRequest answers) {
        return mRestAdapter.scoreQuestionSet(questionSetId, answers);
    }

    /**
     * This allows you to retrieve a question set you have created.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getQuestionSet(String)
     * @param questionSetId Question set ID
     * @return The question set.
     */
    public QuestionSet getQuestionSet(@NotNull final String questionSetId) {
        return mRestAdapter.getQuestionSet(questionSetId);
    }

    /**
     * This allows you to retrieve a question set you have created.
     * @see BlockscoreRetrofitAPI#listQuestionSets()
     * @return List of question sets.
     */
    @NotNull
    public List<QuestionSet> listQuestionSet() {
        return mRestAdapter.listQuestionSets();
    }

    /**
     * Creates a company.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCompany(com.blockscore.models.Company)
     * @param company Company to create.
     * @return Company.
     */
    @NotNull
    public Company createCompany(@NotNull final Company company) {
        return mRestAdapter.createCompany(company);
    }

    /**
     * Retrieves a company.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCompany(com.blockscore.models.Company)
     * @param id Company ID.
     * @return Company.
     */
    @NotNull
    public Company getCompany(@NotNull final String id) {
        return mRestAdapter.getCompany(id);
    }

    /**
     * Lists your verified companies.
     * @see BlockscoreRetrofitAPI#listCompanies()
     * @return List of companies.
     */
    @NotNull
    public List<Company> listCompanies() {
        return mRestAdapter.listCompanies();
    }

    /**
     * Creates a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCandidate(com.blockscore.models.Candidate)
     * @param candidate Watchlist candidate to create.
     * @return Candidate.
     */
    @NotNull
    public Candidate createCandidate(@NotNull final Candidate candidate) {
        return mRestAdapter.createCandidate(candidate);
    }

    /**
     * Updates a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#updateCandidate(String, com.blockscore.models.Candidate)
     * @param id ID for the candidate.
     * @param candidate Watchlist candidate to create.
     * @return Candidate.
     */
    @NotNull
    public Candidate updateCandidate(@NotNull final String id
            , @NotNull final Candidate candidate) {
        return mRestAdapter.updateCandidate(id, candidate);
    }


    /**
     * Gets a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#getCandidate(String)
     * @param id ID for the candidate.
     * @return Candidate
     */
    @NotNull
    public Candidate getCandidate(@NotNull final String id) {
        return mRestAdapter.getCandidate(id);
    }

    /**
     * Lists the watchlist candidates.
     * @see BlockscoreRetrofitAPI#listCandidate()
     * @return Response.
     */
    @NotNull
    public List<Candidate> listCandidates() {
        return mRestAdapter.listCandidate();
    }

    /**
     * Gets the watchlist candidate's history.
     * @param id ID for the candidate.
     * @return List of watchlist candidates.
     */
    @NotNull
    public List<Candidate> getCandidateHistory(@NotNull final String id) {
        return mRestAdapter.getCandidateHistory(id);
    }

    /**
     * Deletes a watchlist candidate.
     * @param id ID for the candidate.
     * @return Watchlist candidate.
     */
    @NotNull
    public Candidate deleteCandidate(@NotNull final String id) {
        return mRestAdapter.deleteCandidate(id);
    }

    /**
     * Gets the hits for a watchlist candidate.
     * @param id ID for the candidate.
     * @return List of watchlist hits.
     */
    @NotNull
    public List<WatchlistHit> getCandidateHits(@NotNull final String id) {
        return mRestAdapter.getCandidateHits(id);
    }

    /**
     * Searches watchlists for a given candidate.
     * @param searchRequest Search request to complete
     * @return Watch list search results.
     */
    @NotNull
    public WatchlistSearchResults searchWatchlists(@NotNull final SearchRequest searchRequest) {
        return mRestAdapter.searchWatchlists(searchRequest);
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
