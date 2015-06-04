package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public class NameResult {
  @NotNull
  @JsonProperty("name_primary")
  private boolean isPrimary;

  @NotNull
  @JsonProperty("name_full")
  private String fullName;

  @NotNull
  @JsonProperty("name_strength")
  private String nameStrength;

  /**
   * Returns whether or not this is a primary name.
   *
   * @return true if this name is a primary name
   */
  public boolean isPrimary() {
    return isPrimary;
  }

  /**
   * Returns the full name.
   *
   * @return the full name
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * Returns the name strength.
   *
   * @return the name strength
   */
  public ResultStrength getNameStrength() {
    return ResultStrength.toEnum(nameStrength);
  }
}
