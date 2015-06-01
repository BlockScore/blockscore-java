package com.blockscore.net;

import com.blockscore.common.Constants;
import com.blockscore.models.Candidate;
import com.blockscore.models.Company;
import com.blockscore.models.Person;
import com.blockscore.models.results.PaginatedResult;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import retrofit.converter.JacksonConverter;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;


/**
 * The Blockscore Java API client.
 */
public class BlockscoreApiClient {
    private static RestAdapter.LogLevel logLevel = RestAdapter.LogLevel.NONE;
    private String apiKey;

    private final BlockscoreRestAdapter restAdapter;

    /**
     * Turns on/off logging. Must be set before creating API client to take effect.
     * @param useVerboseLogs  whether or not to use verbose network logs.
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

        RestAdapter.Builder restBuilder = new RestAdapter.Builder().setClient(new BlockscoreHttpClient())
                                                                   .setEndpoint(Constants.getDomain());
        restBuilder.setConverter(getDefaultConverter());
        restBuilder.setRequestInterceptor(getDefaultRequestInterceptor());
        restBuilder.setErrorHandler(new BlockscoreErrorHandler());
        restBuilder.setLogLevel(logLevel);

        restAdapter = restBuilder.build().create(BlockscoreRestAdapter.class);
    }

    /**
     * Gets a single person exactly as it was when you created it.
     * <p>
     * This route is useful for auditing purposes as you can provide proof that a verification took place 
     * along with all of its associated data.
     * @param id  ID of Person.
     * @return the person, not null
     */
    @NotNull
    public Person retrievePerson(@NotNull final String id) {
        Person person = restAdapter.retrievePerson(id);
        person.setAdapter(restAdapter);
        return person;
    }

    /**
     * Lists verified people.
     * <p>
     * This allows you to see a historical record of all verifications that you have completed.
     * The list is displayed in reverse chronological order (newer people appear first).
     * @return the historical listing of created people, not null
     */
    @NotNull
    public PaginatedResult<Person> listPeople() {
        PaginatedResult<Person> result = restAdapter.listPeople();

        for(Person person : result.getData()) //TODO: FIX COPY METHOD. See listCandidates()
            person.setAdapter(restAdapter);
        
        return result;
    }

    /**
     * Gets a single company exactly as it was when you created it.
     * <p>
     * This route is useful for auditing purposes as you can provide proof that a company verification took place
     * along with all of its associated data.
     * @param id  ID of the Company.
     * @return the company, not null
     */
    @NotNull
    public Company retrieveCompany(@NotNull final String id) {
        Company company = restAdapter.retrieveCompany(id);
        company.setAdapter(restAdapter);
        return company;
    }

    /**
     * Lists verified companies.
     * <p>
     * This endpoint will allow you to see a historical record of all company verifications that you have completed.
     * The list is displayed in reverse chronological order (newer company verifications appear first).
     * @return the historical listing of created companies, not null
     */
    @NotNull
    public PaginatedResult<Company> listCompanies() {
        PaginatedResult<Company> result = restAdapter.listCompanies();

        for(Company company : result.getData()) //TODO: FIX COPY METHOD. See listCandidates()
            company.setAdapter(restAdapter);
        
        return result;
    }

    /**
     * Retrieves a candidate.
     * @param id  ID of the candidate.
     * @return the candidate, not null
     */
    @NotNull
    public Candidate retrieveCandidate(@NotNull final String id) {
        Candidate candidate = restAdapter.retrieveCandidate(id);
        candidate.setAdapter(restAdapter);
        return candidate;
    }

    /**
     * Lists a historical record of all candidates you have created.
     * <p>
     * The list is displayed in reverse chronological order (newer candidates appear first).
     * @return the historical listing of created candidates, not null
     */
    @NotNull
    public PaginatedResult<Candidate> listCandidates() {
        PaginatedResult<Candidate> result = restAdapter.listCandidates();

        for(Candidate candidate : result.getData()) //TODO: FIX COPY METHOD. Clearly a deep copy is not being returned since this
                                                    //      correctly updates internal restAdapters (evidenced by passing tests)
            candidate.setAdapter(restAdapter);
        
        return result;
    }

    /**
     * Encodes the API key for Basic authentication.
     * @return the API key with Base 64 encoding
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

    private JacksonConverter getDefaultConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibilityChecker(mapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return new JacksonConverter(mapper);
    }

    private RequestInterceptor getDefaultRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader(Constants.AUTHORIZATION_HEADER, getEncodedAuthorization());
                request.addHeader(Constants.ACCEPT_HEADER, Constants.getAcceptHeaders());
            }
        };
    }

    //TODO: remove method
    public BlockscoreRestAdapter getAdapter() {
        return restAdapter;
    }
}
