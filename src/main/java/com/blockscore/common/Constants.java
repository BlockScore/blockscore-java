package com.blockscore.common;

import org.jetbrains.annotations.NotNull;

/**
 * Constants used by the Java API client.
 */
public final class Constants {
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String ACCEPT_HEADER = "Accept";

  private static final boolean DEBUG_MODE = false;
  private static final String BLOCKSCORE_DOMAIN = "https://api.blockscore.com";
  private static final String BLOCKSCORE_DEV_DOMAIN = "http://127.0.0.1:5400";
  private static final String VERSION_CODE = "4";
  private static final String ACCEPT_CONTENTS = "application/vnd.blockscore+json;version=%s";

  private Constants() {
    //Prevents user from initializing the static class
  }

  /**
   * Gets the proper Accept headers for the Blockscore API.
   * @return Headers to use when accessing the Blockscore API.
   */
  @NotNull
  public static String getAcceptHeaders() {
    return String.format(ACCEPT_CONTENTS, VERSION_CODE);
  }

  /**
   * Gets the domain to use for testing this API client. (DO NOT RELEASE IN DEBUG MODE!)
   * @return Debug domain if we are in debug mode.
   */
  @NotNull
  public static String getDomain() {
    if (DEBUG_MODE) {
      return BLOCKSCORE_DEV_DOMAIN;
    } else {
      return BLOCKSCORE_DOMAIN;
    }
  }
}
