package com.blockscore.net;

import com.blockscore.common.Constants;
import com.blockscore.exceptions.NoApiKeyFoundException;
import org.jetbrains.annotations.NotNull;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;

import java.util.Base64;

/**
 * The Blockscore Java API client.
 * Created by tealocean on 9/29/14.
 */
public class BlockscoreApiClient {
    private static String sApiKey;

    /**
     * Initializes the API client with the API key. Must be done first or else all calls will fail.
     * @param apiKey API key to use.
     */
    public static void init(@NotNull final String apiKey) {
        sApiKey = apiKey + ":";
    }

    public BlockscoreApiClient() {
        RestAdapter.Builder restBuilder = new RestAdapter.Builder()
                .setClient(new OkClient())
                .setConverter(new JacksonConverter())
                .setEndpoint(Constants.BLOCKSCORE_DOMAIN);

        //Sets up the authentication headers and accept headers.
        restBuilder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader(Constants.AUTHORIZATION_HEADER, getEncodedAuthorization());
                request.addHeader(Constants.ACCEPT_HEADER, Constants.getAcceptHeaders());
            }
        });
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
        return "Basic " + Base64.getEncoder().encodeToString(sApiKey.getBytes());
    }
}
