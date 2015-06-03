package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public class Document {
  @NotNull
  @JsonProperty("document_type")
  private String documentType;

  @NotNull
  @JsonProperty("document_value")
  private String documentValue;

  @NotNull
  @JsonProperty("document_country_code")
  private String documentCountryCode;

  /**
   * Returns the type of this document.
   *
   * @return the type of document // TODO: convert to an enum
   */
  public String getDocumentType() {
    return documentType;
  }

  /**
   * Returns the value of this document.
   *
   * @return the document value
   */
  public String getValue() {
    return documentValue;
  }

  /**
   * Returns the country code associated with this document.
   *
   * @return the country code
   */
  public String getCountryCode() {
    return documentCountryCode;
  }
}
