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

	public String getDocumentType() {
		return documentType;
	}

	public String getValue() {
		return documentValue;
	}

	public String getCountryCode() {
		return documentCountryCode;
	}
}