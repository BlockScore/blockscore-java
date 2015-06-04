package com.blockscore.models;

public enum DocumentType {
  PASSPORT, SSN;

  /**
   * Converts a string to a match type enum.
   *
   * @param value  the value to convert to an enum
   * @return the matching EntityType enum value
   */
  public static DocumentType toEnum(final String value) {
    if (value == null) {
      return null;
    }

    if (value.equalsIgnoreCase(PASSPORT.name())) {
      return PASSPORT;
    } else if (value.equalsIgnoreCase(SSN.name())) {
      return SSN;
    } else {
      return null;
    }
  }
}
