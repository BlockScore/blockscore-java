package com.blockscore.models;

import org.jetbrains.annotations.NotNull;

/**
 * The valid corporation types recognized by Blockscore.
 */
public enum CorporationType {
  CORPORATION("corporation"),
  LLC("llc"),
  PARTNERSHIP("partnership"),
  SOLE_PROPRIETORSHIP("sp"),
  OTHER("other");

  private final String value;

  CorporationType(@NotNull final String value) {
    this.value = value;
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
    if (value.equalsIgnoreCase(CORPORATION.toString())) {
      return CORPORATION;
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
