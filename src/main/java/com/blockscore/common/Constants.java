package com.blockscore.common;

import org.jetbrains.annotations.NotNull;

/**
 * Constants used by the Java API client.
 */
public final class Constants {
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String ACCEPT_HEADER = "Accept";
  public static final String BLOCKSCORE_DOMAIN = "https://api.blockscore.com";
  public static final String USER_AGENT = "blockscore-java/4.0.1 (https://github.com/BlockScore/blockscore-java)";

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
}
