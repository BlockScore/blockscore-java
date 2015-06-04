package com.blockscore.exceptions;

import com.blockscore.models.error.BlockscoreError;
import com.blockscore.models.error.ValidationErrorType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Exception thrown when an invalid request is returned from the server.
 */
public class InvalidRequestException extends RuntimeException {
  private final BlockscoreError error;
  private static final long serialVersionUID = 0L;

  public InvalidRequestException(@NotNull final BlockscoreError error) {
    super(error.getError().getMessage());
    this.error = error;
  }

  /**
   * Gets the invalid parameters.
   *
   * @return the invalid parameter
   */
  @Nullable
  public String getInvalidParam() {
    return error.getError().getParam();
  }

  /**
   * Gets the validation error type. (If any)
   * @return the validation error type
   */
  @Nullable
  public ValidationErrorType getValidationErrorCode() {
    return error.getError().getValidationErrorCode();
  }
}
