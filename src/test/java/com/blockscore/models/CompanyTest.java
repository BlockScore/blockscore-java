package com.blockscore.models;

import com.blockscore.common.CorporationType;
import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.results.PaginatedResult;
import com.blockscore.net.BlockscoreApiClient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Candidate unit tests.
 */
public class CompanyTest {
  BlockscoreApiClient client = setupBlockscoreApiClient();

  @Test
  public void testCreatingAndRetrievingCompany() throws ParseException {
    // Tests creation.
    Company company = createTestCompany();
    isCompanyValid(company);

    // Tests retrieval.
    Company retrievedCompany = client.retrieveCompany(company.getId());
    isCompanyValid(retrievedCompany);

    // Test that we got back the same company information we entered.
    areCompaniesEquivalent(company, retrievedCompany);
  }

  @Test
  public void testListingCompanies() {
    PaginatedResult<Company> companies = client.listCompanies();
    areCompaniesValid(companies.getData());
  }

  @Test
  public void createCompanyInvalidParameters() throws ParseException {
    InvalidRequestException exception = null;

    try {
      Company company = createBadTestCompany();
      isCompanyValid(company);
    } catch (InvalidRequestException e) {
      assertNotNull(e.getMessage());
      assertNotNull(e.getInvalidParam());
      exception = e;
    }

    assertNotNull(exception);
  }

  @Test
  public void getNonExistingCompany() throws ParseException {
    InvalidRequestException exception = null;

    try {
      Company company = client.retrieveCompany("781237129");
      isCompanyValid(company);
    } catch (InvalidRequestException e) {
      assertNotNull(e.getMessage());
      exception = e;
    }
    
    assertNotNull(exception);
  }

  @NotNull
  private Company createTestCompany() throws ParseException {
    Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = formatter.parse("1980-08-23");

    Company.Builder builder = new Company.Builder(client);
    builder.setEntityName("BlockScore")
           .setTaxId("123410000")
           .setIncorporationDate(date)
           .setIncorporationState("DE")
           .setIncorporationCountryCode("US")
           .setIncorporationType(CorporationType.CORP)
           .setDbas("BitRemit")
           .setRegistrationNumber("123123123")
           .setEmail("test@example.com")
           .setUrl("https://blockscore.com")
           .setIpAddress("127.0.0.1")
           .setNote("note: this is a note")
           .setPhoneNumber("6505555555")
           .setAddress(address);

    Company company = builder.create();
    // ensure all fields were set
    isCompanyDataComplete(company);

    return company;
  }

  @NotNull
  private Company createBadTestCompany() throws ParseException {
    Company.Builder builder = new Company.Builder(client);
    return builder.create();
  }

  private void areCompaniesEquivalent(Company expected, Company actual) {
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getEntityName(), actual.getEntityName());
    assertEquals(expected.getTaxId(), actual.getTaxId());
    assertEquals(expected.getIncorporationState(), actual.getIncorporationState());
    assertEquals(expected.getIncorporationCountryCode(), actual.getIncorporationCountryCode());
    assertEquals(expected.getIncorporationType(), actual.getIncorporationType());
    assertEquals(expected.getIncorporationDate(), actual.getIncorporationDate());
    assertEquals(expected.getDbas(), actual.getDbas());
    assertEquals(expected.getRegistrationNumber(), actual.getRegistrationNumber());
    assertEquals(expected.getEmail(), actual.getEmail());
    assertEquals(expected.getUrl(), actual.getUrl());
    assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    assertEquals(expected.getIpAddress(), actual.getIpAddress());
    assertEquals(expected.getNote(), actual.getNote());
    assertEquals(expected.isValid(), actual.isValid());
    areAddressesEquivalent(expected.getAddress(), actual.getAddress());
  }

  private void areAddressesEquivalent(Address expected, Address actual) {
    assertEquals(expected.getCity(), actual.getCity());
    assertEquals(expected.getCountryCode(), actual.getCountryCode());
    assertEquals(expected.getPostalCode(), actual.getPostalCode());
    assertEquals(expected.getStreet1(), actual.getStreet1());
    assertEquals(expected.getStreet2(), actual.getStreet2());
    assertEquals(expected.getSubdivision(), actual.getSubdivision());
  }

  private void isCompanyDataComplete(@Nullable final Company company) {
    isCompanyValid(company);
    assertNotNull(company.getIncorporationState());
    assertNotNull(company.getIncorporationDate());
    assertNotNull(company.getDbas());
    assertNotNull(company.getRegistrationNumber());
    assertNotNull(company.getEmail());
    assertNotNull(company.getUrl());
    assertNotNull(company.getPhoneNumber());
    assertNotNull(company.getIpAddress());
    assertNotNull(company.getNote());
  }

  private void isCompanyValid(@Nullable final Company company) {
    assertNotNull(company);
    assertNotNull(company.getId());
    assertNotNull(company.getEntityName());
    assertNotNull(company.getTaxId());
    assertNotNull(company.getIncorporationCountryCode());
    assertNotNull(company.getIncorporationType());
    assertTrue(company.isValid());
    areDetailsValid(company.getDetails());
    isAddressValid(company.getAddress());
  }

  private void areDetailsValid(@Nullable final CompanyDetails details) {
    assertNotNull(details);
    assertNotNull(details.getAddressMatch());
    assertNotNull(details.getCountryCodeMatch());
    assertNotNull(details.getEntityMatch());
    assertNotNull(details.getIncorporationDateMatch());
    assertNotNull(details.getOfacMatch());
    assertNotNull(details.getStateMatch());
    assertNotNull(details.getTaxIdMatch());
  }

  private void isAddressValid(@Nullable final Address address) {
    assertNotNull(address);
    assertNotNull(address.getStreet1());
    assertNotNull(address.getSubdivision());
    assertNotNull(address.getPostalCode());
    assertNotNull(address.getCountryCode());
    assertNotNull(address.getCity());
  }

  private void areCompaniesValid(@Nullable final List<Company> companies) {
    assertNotNull(companies);

    for (Company company : companies) {
      isCompanyValid(company);
    }
  }

  @NotNull
  private BlockscoreApiClient setupBlockscoreApiClient() {
    BlockscoreApiClient.useVerboseLogs(false);
    return new BlockscoreApiClient("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
  }
}
