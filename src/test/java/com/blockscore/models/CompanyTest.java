package com.blockscore.models;

import com.blockscore.common.CorporationType;
import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.results.PaginatedResult;
import com.blockscore.net.BlockscoreApiClient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Simple test for the company endpoints.
 * Created by Tony Dieppa on 9/30/14.
 */
public class CompanyTest {
    BlockscoreApiClient apiClient = setupBlockscoreApiClient();

    @Test
    public void companyTest() throws ParseException {
        //Tests creation.
        Company company = createTestCompany();
        isCompanyValid(company);

        //Tests getting the company.
        company = apiClient.retrieveCompany(company.getId());
        isCompanyValid(company);

        //Tests listing the companies
        PaginatedResult<Company> companies = apiClient.listCompanies();
        isListOfCompaniesValid(companies.getData());
    }

    @Test
    public void createCompanyInvalidParameters() throws ParseException {
        InvalidRequestException exception = null;

        try {
            Company company = createBadTestCompany();
            isCompanyValid(company);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            Assert.assertNotNull(e.getInvalidParam());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    public void getNonExistingCompany() throws ParseException {
        InvalidRequestException exception = null;

        try {
            Company company = apiClient.retrieveCompany("781237129");
            isCompanyValid(company);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    /**
     * Generates a sample company to use for this test suite.
     * @return Fake company.
     * @throws ParseException
     */
    @NotNull
    private Company createTestCompany() throws ParseException {
        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("1980-08-23");

        Company.Builder builder = new Company.Builder(apiClient);
        builder.setEntityName("BlockScore")
               .setTaxId("123410000")
               .setIncorporationDate(date)
               .setIncorporationState("DE")
               .setIncorporationCountryCode("US")
               .setIncorporationType(CorporationType.CORP)
               .setDbas("BitRemit")
               .setRegistrationNumber("123123123")
               .setEmail("test@example.com")
               .setURL("https://blockscore.com")
               .setPhoneNumber("6505555555")
               .setAddress(address);

        return builder.create();
    }

    /**
     * Generates a bad company to use for this test suite.
     * @return Bad company.
     * @throws ParseException
     */
    @NotNull
    private Company createBadTestCompany() throws ParseException {
        Company.Builder builder = new Company.Builder(apiClient);
        return builder.create();
    }

    /**
     * Tests to see if the company object is valid.
     * @param company Company to test.
     */
    private void isCompanyValid(@Nullable final Company company) {
        Assert.assertNotNull(company);
        Assert.assertNotNull(company.getId());
        Assert.assertNotNull(company.getEntityName());
        Assert.assertNotNull(company.getTaxId());
        Assert.assertNotNull(company.getIncorporationCountryCode());
        Assert.assertNotNull(company.getIncorporationType());
        isAddressValid(company.getAddress());
    }

    /**
     * Examines the address and ensures it is valid.
     * @param address Address to test.
     */
    private void isAddressValid(@Nullable final Address address) {
        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getStreet1());
        Assert.assertNotNull(address.getSubdivision());
        Assert.assertNotNull(address.getPostalCode());
        Assert.assertNotNull(address.getCountryCode());
        Assert.assertNotNull(address.getCity());
    }

    /**
     * Tests a list of companies to ensure they are valid.
     * @param companies Companies to test.
     */
    private void isListOfCompaniesValid(@Nullable final List<Company> companies) {
        Assert.assertNotNull(companies);
        for (Company company : companies) {
            isCompanyValid(company);
        }
    }

    /**
     * Sets up the API client.
     * @return API client.
     */
    @NotNull
    private BlockscoreApiClient setupBlockscoreApiClient() {
        BlockscoreApiClient.useVerboseLogs(false);
        return new BlockscoreApiClient("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
    }
}
