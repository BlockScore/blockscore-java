package com.blockscore.common;

/**
 * Constants used by the Java API client.
 * Created by Tony Dieppa on 9/29/14.
 */
public final class Constants {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ACCEPT_HEADER = "Accept";
    public static final String BLOCKSCORE_DOMAIN = "https://api.blockscore.com";

    private static final int VERSION_CODE = 3;
    private static final String ACCEPT_CONTENTS = "application/vnd.blockscore+json;version=%d";

    /**
     * Gets the proper Accept headers for the Blockscore API.
     * @return Headers to use when accessing the Blockscore API.
     */
    public static String getAcceptHeaders() {
        return String.format(ACCEPT_CONTENTS, VERSION_CODE);
    }
}
