package com.blockscore.models;

import static com.blockscore.models.TestUtils.assertAddressIsValid;
import static com.blockscore.models.TestUtils.assertAddressesAreEquivalent;
import static com.blockscore.models.TestUtils.assertBasicResponseIsValid;
import static com.blockscore.models.TestUtils.assertBasicResponsesAreEquivalent;
import static com.blockscore.models.TestUtils.setupBlockscoreApiClient;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.net.BlockscoreApiClient;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Person unit tests.
 */
public class PersonTest {
  private static BlockscoreApiClient client = setupBlockscoreApiClient();

  @Test
  public void testPersonCreation() {
    Person person = createTestPerson();
    assertPersonIsValid(person);
  }

  @Test
  public void testPersonCreation_InvalidParameters() {
    InvalidRequestException expected = null;

    try {
       createInvalidTestPerson();
    } catch (InvalidRequestException e) {
      assertNotNull(e.getMessage());
      assertNotNull(e.getInvalidParam());
      expected = e;
    }

    assertNotNull(expected);
  }

  @Test
  public void testPersonRetrieval() {
    Person person = createTestPerson();

    Person retrievedPerson = client.retrievePerson(person.getId());
    assertPersonIsValid(retrievedPerson);

    assertPeopleAreEquivalent(person, retrievedPerson);
  }

  @Test
  public void testPersonRetrieval_NonExistentPerson() {
    InvalidRequestException expected = null;

    try {
      Person person = client.retrievePerson("-1");
      assertPersonIsValid(person);
    } catch (InvalidRequestException e) {
      assertNotNull(e.getMessage());
      expected = e;
    }

    assertNotNull(expected);
  }

  @Test
  public void testPeopleListing() {
    PaginatedResult<Person> persons = client.listPeople();
    assertPeopleAreValid(persons.getData());
  }

  /*------------------*/
  /* Helper Functions */
  /*------------------*/

  static Person createTestPerson() {
    Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date dateOfBirth = null;
    try {
      dateOfBirth = formatter.parse("1980-08-23");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Person.Builder builder = new Person.Builder(client);
    builder.setFirstName("John")
          .setMiddleName("Pearce")
          .setLastName("Doe")
          .setDocumentType("ssn")
          .setDocumentValue("0000")
          .setAddress(address)
          .setDateOfBirth(dateOfBirth);
    return builder.create();
  }

  private Person createInvalidTestPerson() {
    Person.Builder builder = new Person.Builder(client);
    return builder.create();
  }

  private void assertPeopleAreValid(final List<Person> personList) {
    assertNotNull(personList);

    for (Person person : personList) {
      assertPersonIsValid(person);
    }
  }

  private void assertPeopleAreEquivalent(Person expected, Person actual) {
    assertNotNull(expected);
    assertNotNull(actual);
    assertBasicResponsesAreEquivalent(expected, actual);
    assertEquals(expected.getFirstName(), actual.getFirstName());
    assertEquals(expected.getMiddleName(), actual.getMiddleName());
    assertEquals(expected.getLastName(), actual.getLastName());
    assertEquals(expected.getDocumentType(), actual.getDocumentType());
    assertEquals(expected.getDocumentValue(), actual.getDocumentValue());
    assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth());
    assertAddressesAreEquivalent(expected.getAddress(), actual.getAddress());
    assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    assertEquals(expected.getIpAddress(), actual.getIpAddress());
    assertEquals(expected.getNote(), actual.getNote());
  }

  private void assertPersonIsValid(final Person person) {
    assertBasicResponseIsValid(person);
    assertNotNull(person.getId());
    assertNotNull(person.getQuestionSetIds());
    assertDetailsAreValid(person.getDetails());
    assertNameIsValid(person);
    assertAddressIsValid(person.getAddress());
  }

  private void assertDetailsAreValid(final PersonDetails personDetails) {
    assertNotNull(personDetails);
    assertNotNull(personDetails.getAddressRisk());
    assertNotNull(personDetails.getAddressMatchDetails());
    assertNotNull(personDetails.getIdentificationMatch());
    assertNotNull(personDetails.getDateOfBirthMatch());
    assertNotNull(personDetails.getOfac());
    assertNotNull(personDetails.getPep());
  }

  private void assertNameIsValid(final Person name) {
    assertNotNull(name);
    assertNotNull(name.getFirstName());
    assertNotNull(name.getLastName());
  }
}
