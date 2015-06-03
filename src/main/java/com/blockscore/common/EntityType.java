package com.blockscore.common;

public enum EntityType {
  ENTITY, INDIVIDUAL;

  /**
   * Converts a string to a match type enum.
   *
   * @param value  the value to convert to an enum
   * @return the matching EntityType enum value
   */
  public static EntityType toEnum(final String value) {
    if (value == null) {
      return null;
    }

    if (value.equalsIgnoreCase(ENTITY.name()) || value.equalsIgnoreCase("company")) {
      return ENTITY;
    } else if (value.equalsIgnoreCase(INDIVIDUAL.name()) || value.equalsIgnoreCase("person")) {
      return INDIVIDUAL;
    } else {
      return null;
    }
  }
}
