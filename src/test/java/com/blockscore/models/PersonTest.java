package com.blockscore.models;

import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.request.AnswerRequest;
import com.blockscore.models.results.PaginatedResult;

import com.blockscore.net.BlockscoreApiClient;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple test for the person process.
 */
public class PersonTest {
    BlockscoreApiClient apiClient = setupBlockscoreApiClient();

    @Test
    public void createPersonTest() {
        Person person = createTestPerson();
        isPersonValid(person);
    }

    @Test
    public void retrievePersonTest() {
        Person person = createTestPerson();
        person = apiClient.retrievePerson(person.getId());
        isPersonValid(person);
    }

    @Test
    public void listPeopleTest() {
        PaginatedResult<Person> persons = apiClient.listPeople();
        arePersonsValid(persons.getData());
    }

    @Test
    public void createBadPersonTest() {
        InvalidRequestException exception = null;

        try {
            Person person = createBadTestPerson();
            isPersonValid(person);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            Assert.assertNotNull(e.getInvalidParam());
            exception = e;
        }

        Assert.assertNotNull(exception);
    }

    @Test
    public void getNonexistentPerson() {
        InvalidRequestException exception = null;

        try {
            Person person = apiClient.retrievePerson("-1");
            isPersonValid(person);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @NotNull
    private Person createTestPerson() {
        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = null;
        try {
            dateOfBirth = formatter.parse("1980-08-23");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Person.Builder builder = new Person.Builder(apiClient);
        builder.setFirstName("John")
               .setMiddleName("Pearce")
               .setLastName("Doe")
               .setDocumentType("ssn")
               .setDocumentValue("0000")
               .setAddress(address)
               .setDateOfBirth(dateOfBirth);
        return builder.create();
    }

    @NotNull
    private Person createBadTestPerson() {
        Person.Builder builder = new Person.Builder(apiClient);
        return builder.create();
    }

    private void arePersonsValid(@Nullable final List<Person> personList) {
        Assert.assertNotNull(personList);
        for (Person person : personList) {
            isPersonValid(person);
        }
    }

    private void isPersonValid(@NotNull final Person person) {
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getId());
        Assert.assertNotNull(person.getQuestionSets());

        areDetailsValid(person.getDetails());
        isNameValid(person);
        isAddressValid(person.getAddress());
    }

    private void areDetailsValid(@NotNull final Details details) {
        Assert.assertNotNull(details);
        Assert.assertNotNull(details.getAddressRisk());
        Assert.assertNotNull(details.getAddressMatchDetails());
        Assert.assertNotNull(details.getIdentfication());
        Assert.assertNotNull(details.getDateOfBirth());
        Assert.assertNotNull(details.getOFAC());
    }

    private void isAddressValid(@Nullable final Address address) {
        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getStreet1());
        Assert.assertNotNull(address.getSubdivision());
        Assert.assertNotNull(address.getPostalCode());
        Assert.assertNotNull(address.getCountryCode());
        Assert.assertNotNull(address.getCity());
    }

    private void isNameValid(@Nullable final Person name) {
        Assert.assertNotNull(name);
        Assert.assertNotNull(name.getFirstName());
        Assert.assertNotNull(name.getLastName());
    }

    @NotNull
    private BlockscoreApiClient setupBlockscoreApiClient() {
        BlockscoreApiClient.useVerboseLogs(false);
        return new BlockscoreApiClient("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
    }
}
