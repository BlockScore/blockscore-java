package com.blockscore.net;

import com.blockscore.common.BlockscoreErrorType;
import com.blockscore.common.Constants;
import com.blockscore.exceptions.APIException;
import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.Company;
import com.blockscore.models.Person;
import com.blockscore.models.QuestionSet;
import com.blockscore.models.Candidate;
import com.blockscore.models.error.BlockscoreError;
import com.blockscore.models.error.RequestError;
import com.blockscore.models.request.AnswerRequest;
import com.blockscore.models.request.SearchRequest;
import com.blockscore.models.results.WatchlistHit;
import com.blockscore.models.results.PaginatedResult;
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
 */
public class BlockscoreApiClient {
    private static RestAdapter.LogLevel logLevel = RestAdapter.LogLevel.NONE;
    private static String apiKey;

    private final BlockscoreRetrofitAPI restAdapter;

    /**
     * Turns on/off logging. Should be done before API client usage. // TODO: investigate making non-static? Also previous ordering of setting up /after/ calling init()
     * @param useVerboseLogs True to use verbose network logs.
     */
    public static void useVerboseLogs(final boolean useVerboseLogs) {
        if (useVerboseLogs) {
            logLevel = RestAdapter.LogLevel.FULL;
        } else {
            logLevel = RestAdapter.LogLevel.NONE;
        }
    }

    public BlockscoreApiClient(@NotNull final String apiKey) {
        this.apiKey = apiKey + ":";

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

        restBuilder.setLogLevel(logLevel);
        restAdapter = restBuilder.build().create(BlockscoreRetrofitAPI.class);
    }

    /**
     * Gets a single Person.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#retrievePerson(String)
     * @param id ID of Person to verify.
     */
    @NotNull
    public Person retrievePerson(@NotNull final String id) {
        return restAdapter.retrievePerson(id);
    }

    /**
     * Gets a list of Persons.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#listPersons()
     */
    @NotNull
    public PaginatedResult<Person> listPeople() {
        return restAdapter.listPeople();
    }

    /**
     * Creates a company.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCompany(com.blockscore.models.Company)
     * @param company Company to create.
     */
    @NotNull
    public Company createCompany(@NotNull final Company company) {
        return restAdapter.createCompany(company);
    }

    /**
     * Gets a company.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCompany(com.blockscore.models.Company)
     * @param id Company ID.
     */
    @NotNull
    public Company retrieveCompany(@NotNull final String id) {
        return restAdapter.retrieveCompany(id);
    }

    /**
     * Lists your verified companies.
     * @see BlockscoreRetrofitAPI#listCompanies()
     */
    @NotNull
    public PaginatedResult<Company> listCompanies() {
        return restAdapter.listCompanies();
    }

    /**
     * Creates a watchlist candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#createCandidate(com.blockscore.models.Candidate)
     * @param candidate Watchlist candidate to create.
     */
    @NotNull
    public Candidate createCandidate(@NotNull final Candidate candidate) {
        return restAdapter.createCandidate(candidate);
    }

    /**
     * Updates a candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#updateCandidate(String, com.blockscore.models.Candidate)
     * @param id ID for the candidate.
     * @param candidate Watchlist candidate to create.
     */
    @NotNull
    public Candidate updateCandidate(@NotNull final String id
            , @NotNull final Candidate candidate) {
        return restAdapter.updateCandidate(id, candidate);
    }

    /**
     * Retrieves a candidate.
     * @see com.blockscore.net.BlockscoreRetrofitAPI#retrieveCandidate(String)
     * @param id ID for the candidate.
     */
    @NotNull
    public Candidate retrieveCandidate(@NotNull final String id) {
        return restAdapter.retrieveCandidate(id);
    }

    /**
     * Lists the candidates.
     * @see BlockscoreRetrofitAPI#listCandidate()
     */
    @NotNull
    public PaginatedResult<Candidate> listCandidates() {
        return restAdapter.listCandidates();
    }

    /**
     * Gets the candidate's history.
     * @param id ID for the candidate.
     */
    @NotNull
    public List<Candidate> getCandidateHistory(@NotNull final String id) {
        return restAdapter.getCandidateHistory(id);
    }

    /**
     * Deletes a candidate.
     * @param id ID for the candidate.
     */
    @NotNull
    public Candidate deleteCandidate(@NotNull final String id) {
        return restAdapter.deleteCandidate(id);
    }

    /**
     * Gets the watchlist hits for a candidate.
     * @param id ID for the candidate.
     */
    @NotNull
    public PaginatedResult<WatchlistHit> getCandidateHits(@NotNull final String id) {
        return restAdapter.getCandidateHits(id);
    }

    /**
     * Searches watchlists for a given candidate.
     * @param searchRequest Search request to complete
     */
    @NotNull
    public WatchlistSearchResults searchWatchlists(@NotNull final SearchRequest searchRequest) {
        return restAdapter.searchWatchlists(searchRequest);
    }

    /**
     * Encodes the API key for Basic authentication.
     * @return API key encoded with Base 64.
     */
    @NotNull
    private String getEncodedAuthorization() {
        try {
            return "Basic " + Base64.getEncoder().encodeToString(apiKey.getBytes("utf-8"));
        } catch(UnsupportedEncodingException e) {
            //TODO: change to an appropriate response
            return null;
        }
    }



    public BlockscoreRetrofitAPI getAdapter() {
        return restAdapter;
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
