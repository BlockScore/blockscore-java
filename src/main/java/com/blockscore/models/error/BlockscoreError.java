package com.blockscore.models.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit.RetrofitError;

/**
 * Blockscore Error model.
 */
public class BlockscoreError {
  @NotNull
  @JsonProperty("error")
  private RequestError requestError;

  /**
   * Converts a Retrofit Error into a Blockscore Error.
   *
   * @param cause  the Retrofit error to convert
   * @return the Blockscore Error
   */
  @Nullable
  public static BlockscoreError getBlockscoreError(@NotNull final RetrofitError cause) {
    Object rawError = cause.getBodyAs(BlockscoreError.class);
    if (rawError instanceof BlockscoreError) {
      return (BlockscoreError) rawError;
    } else {
      return null;
    }
  }

  /**
   * Returns the error details.
   *
   * @return the error details
   */
  @NotNull
  public RequestError getError() {
    return requestError;
  }
}
