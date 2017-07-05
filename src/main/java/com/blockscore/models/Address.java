package com.blockscore.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.jetbrains.annotations.NotNull;

/**
 * The address model.
 */
public class Address {
  @JsonProperty("address_street1")
  private String street1;

  @JsonProperty("address_street2")
  private String street2;

  @JsonProperty("address_city")
  private String city;

  @JsonProperty("address_subdivision")
  private String subdivision;

  @JsonProperty("address_postal_code")
  private String postalCode;

  @JsonProperty("address_country_code")
  private String countryCode;


  public Address() {
    // Do nothing.
  }

  /**
   * Constructs a new Address object.
   *
   * @param street1  the primary street address
   * @param street2  the second address line (typically for apartment or suite numbers)
   * @param city  the city
   * @param subdivision  the subdivision of the entered country (in the U.S., the state)
   * @param postalCode  the postal (ZIP) code
   * @param countryCode  the country code
   */
  public Address(final String street1, final String street2,
           final String city, final String subdivision,
           final String postalCode, final String countryCode) {
    this.street1 = street1;
    this.street2 = street2;
    this.city = city;
    this.subdivision = subdivision;
    this.postalCode = postalCode;
    this.countryCode = countryCode;
  }

  /**
   * The primary street address of the customer. This is automatically normalized.
   *
   * @param street1  Street (Line 1)
   * @return this
   */
  @NotNull
  public Address setStreet1(final String street1) {
    this.street1 = street1;
    return this;
  }

  /**
   * The second address line typically used for apartment or suite numbers. This is automatically normalized.
   *
   * @param street2  Street (Line 2)
   * @return this
   */
  @NotNull
  public Address setStreet2(final String street2) {
    this.street2 = street2;
    return this;
  }

  /**
   * The city name of the customer. This is automatically normalized.
   *
   * @param city  City
   * @return this
   */
  @NotNull
  public Address setCity(final String city) {
    this.city = city;
    return this;
  }

  /**
   * The subdivision of the entered country. For instance in the United States, this would be a 'state'.
   * Ex: The subdivision for California would be CA.
   *
   * @param subdivision  Subdivision (FIPS code format)
   * @return this
   */
  @NotNull
  public Address setSubdivision(final String subdivision) {
    this.subdivision = subdivision;
    return this;
  }

  /**
   * The postal code, also known as the ZIP code of the address.
   *
   * @param postalCode  Postal (ZIP) code
   * @return this
   */
  @NotNull
  public Address setPostalCode(final String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  /**
   * The country of the customer. Should be of the ISO code form.
   *
   * @param countryCode  the country code
   * @return this
   */
  @NotNull
  public Address setCountryCode(final String countryCode) {
    this.countryCode = countryCode;
    return this;
  }

  /**
   * Gets the primary street address of the customer. This is automatically normalized.
   *
   * @return Line 1 of the address
   */
  public String getStreet1() {
    return street1;
  }

  /**
   * The second address line typically used for apartment or suite numbers. This is automatically normalized.
   *
   * @return Line 2 of the address
   */
  public String getStreet2() {
    return street2;
  }

  /**
   * The city name of the customer. This is automatically normalized.
   *
   * @return the address city
   */
  public String getCity() {
    return city;
  }

  /**
   * The subdivision of the entered country. For instance in the United States, this would be a 'state'.
   *
   * @return the address subdivision
   */
  public String getSubdivision() {
    return subdivision;
  }

  /**
   * The postal code, also known as the ZIP code of the address.
   *
   * @return the postal code
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * The country of the customer. Should be of the ISO code form.
   *
   * @return the country code
   */
  public String getCountryCode() {
    return countryCode;
  }
}
