package com.blockscore.common;

import org.jetbrains.annotations.NotNull;

/**
 * Validity Status.
 */
public enum ValidityStatus {
  VALID,
  INVALID;

  /**
   * Returns if this matches.
   *
   * @param value  the value to test
   * @return whether the value is equivalent
   */
  public boolean isEqualTo(final String value) {
    return name().equalsIgnoreCase(value);
  }

  /**
   * Converts a string to an enum.
   *
   * @param value the value to convert
   * @return the validity status type
  */
  @NotNull
  public static ValidityStatus toEnum(@NotNull final String value) {
    if (value.equalsIgnoreCase(VALID.toString())) {
      return VALID;
    } else {
      return INVALID;
    }
  }
}
