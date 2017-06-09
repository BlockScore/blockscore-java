package com.blockscore.models;

import static com.blockscore.models.TestUtils.assertAddressIsValid;
import static com.blockscore.models.TestUtils.assertAddressesAreEquivalent;
import static com.blockscore.models.TestUtils.assertBasicResponseIsValid;
import static com.blockscore.models.TestUtils.assertBasicResponsesAreEquivalent;
import static com.blockscore.models.TestUtils.setupBlockscoreApiClient;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.net.BlockscoreApiClient;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Company unit tests.
 */
public class CompanyTest {
  private static BlockscoreApiClient client = setupBlockscoreApiClient();

  @Test
  public void testCompanyCreation() {
    Company company = createTestCompany();
    assertCompanyDataIsComplete(company);
  }

  @Test
  public void testCompanyCreation_InvalidParameters() {
    InvalidRequestException expected = null;

    try {
      Company company = createInvalidTestCompany();
      assertCompanyIsValid(company);
    } catch (InvalidRequestException e) {
      assertNotNull(e.getMessage());
      assertNotNull(e.getInvalidParam());
      expected = e;
    }

    assertNotNull(expected);
  }

  @Test
  public void testCompanyRetrieval() {
    Company company = createTestCompany();

    Company retrievedCompany = client.retrieveCompany(company.getId());
    assertCompanyIsValid(retrievedCompany);

    // Test that we got back the same company information we entered.
    assertCompaniesAreEquivalent(company, retrievedCompany);
  }

  @Test
  public void testCompanyRetrieval_InvalidId() {
    InvalidRequestException expected = null;

    try {
      Company company = client.retrieveCompany("781237129");
      assertCompanyIsValid(company);
    } catch (InvalidRequestException e) {
      assertNotNull(e.getMessage());
      expected = e;
    }

    assertNotNull(expected);
  }

  @Test
  public void testCompanyListing() {
    for (Company company : client.listCompanies().getData()) {
      assertBasicResponseIsValid(company);
      assertNotNull(company.getId());
      assertNotNull(company.getEntityName());
      assertNotNull(company.getTaxId());
      assertNotNull(company.getIncorporationCountryCode());
      assertNotNull(company.getIncorporationType());
      assertDetailsAreValid(company.getDetails());
      assertAddressIsValid(company.getAddress());
    }
  }

  /*------------------*/
  /* Helper Functions */
  /*------------------*/

  private Company createTestCompany() {
    Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date incorporationDate = null;
    try {
      incorporationDate = formatter.parse("1980-08-23");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Company.Builder builder = new Company.Builder(client);
    builder.setEntityName("BlockScore")
           .setTaxId("123410000")
           .setIncorporationDate(incorporationDate)
           .setIncorporationState("DE")
           .setIncorporationCountryCode("US")
           .setIncorporationType(CorporationType.CORPORATION)
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
    assertCompanyDataIsComplete(company);

    return company;
  }

  private Company createInvalidTestCompany() {
    Company.Builder builder = new Company.Builder(client);
    return builder.create();
  }

  private void assertCompaniesAreEquivalent(Company expected, Company actual) {
    assertBasicResponsesAreEquivalent(expected, actual);
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
    assertAddressesAreEquivalent(expected.getAddress(), actual.getAddress());
  }

  private void assertCompanyDataIsComplete(final Company company) {
    assertCompanyIsValid(company);
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

  private void assertCompanyIsValid(final Company company) {
    assertBasicResponseIsValid(company);
    assertNotNull(company.getId());
    assertNotNull(company.getEntityName());
    assertNotNull(company.getTaxId());
    assertNotNull(company.getIncorporationCountryCode());
    assertNotNull(company.getIncorporationType());
    assertTrue(company.isValid());
    assertDetailsAreValid(company.getDetails());
    assertAddressIsValid(company.getAddress());
  }

  private void assertDetailsAreValid(final CompanyDetails details) {
    assertNotNull(details);
    assertNotNull(details.getAddressMatch());
    assertNotNull(details.getCountryCodeMatch());
    assertNotNull(details.getEntityMatch());
    assertNotNull(details.getIncorporationDateMatch());
    assertNotNull(details.getOfacMatch());
    assertNotNull(details.getStateMatch());
    assertNotNull(details.getTaxIdMatch());
  }
}
