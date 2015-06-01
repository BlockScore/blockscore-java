package com.blockscore.models.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

	public boolean isPrimary() {
		return isPrimary;
	}

	public String getFullName() {
		return fullName;
	}

	public String getNameStrength() {
		return nameStrength;
	}
}