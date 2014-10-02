package com.blockscore.exceptions;

/**
 * Exception thrown when no API key is set and an API call is attempted.
 * Created by Tony Dieppa on 9/29/14.
 */
public class NoApiKeyFoundException extends RuntimeException {
    public NoApiKeyFoundException() {
        super("You must call BlockscoreApiClient.init() before you can use this function!");
    }

    public NoApiKeyFoundException(final String notes) {
        super(String.format("You must call BlockscoreApiClient.init() before you can use this function!%n%s", notes));
    }
}
