package com.blockscore.common;

import org.jetbrains.annotations.NotNull;

/**
 * Corporation type.
 */
public enum CorporationType {
  CORP("corporation"),
  LLC("llc"),
  PARTNERSHIP("partnership"),
  SOLE_PROPRIETORSHIP("sp"),
  OTHER("other");

  private final String value;

  CorporationType(@NotNull final String value) {
    this.value = value;
  }

  /**
   * Returns if this matches.
   *
   * @param value  the value to test
   * @return whether the value is equivalent to the enum
   */
  public boolean isEqualTo(final String value) {
    return this.value.equalsIgnoreCase(value);
  }

  /**
   * Converts an enum to the string value.
   *
   * @return String value for enum
   */
  @Override
  public String toString() {
    return value;
  }

  /**
   * Converts a string to a corporation type enum.
   * @param value  the value to convert
   * @return the CorporationType enum value
   */
  public static CorporationType toEnum(@NotNull final String value) {
    if (value.equalsIgnoreCase(CORP.toString())) {
      return CORP;
    } else if (value.equalsIgnoreCase(LLC.toString())) {
      return LLC;
    } else if (value.equalsIgnoreCase(PARTNERSHIP.toString())) {
      return PARTNERSHIP;
    } else if (value.equalsIgnoreCase(SOLE_PROPRIETORSHIP.toString())) {
      return SOLE_PROPRIETORSHIP;
    } else {
      return OTHER;
    }
  }
}
