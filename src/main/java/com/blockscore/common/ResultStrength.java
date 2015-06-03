package com.blockscore.common;

import org.jetbrains.annotations.NotNull;

public enum ResultStrength {
  HIGH("high"),
  LOW("low"),
  NO_MATCH("no_match"),
  NO_DATA("insufficient_data");

  private final String value;

  ResultStrength(@NotNull final String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

  /**
   * Converts a string to a enum.
   *
   * @param value  the value to convert
   * @return the matching MatchStrenght enum value
   */
  public static ResultStrength toEnum(@NotNull final String value) {
    if (value.equalsIgnoreCase(HIGH.toString())) {
      return HIGH;
    } else if (value.equalsIgnoreCase(LOW.toString())) {
      return LOW;
    } else if (value.equalsIgnoreCase(NO_MATCH.toString())) {
      return NO_MATCH;
    } else {
      return NO_DATA;
    }
  }
}
