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

    @NotNull
    private Company createBadTestCompany() throws ParseException {
        Company.Builder builder = new Company.Builder(apiClient);
        return builder.create();
    }

    private void isCompanyValid(@Nullable final Company company) {
        Assert.assertNotNull(company);
        Assert.assertNotNull(company.getId());
        Assert.assertNotNull(company.getEntityName());
        Assert.assertNotNull(company.getTaxId());
        Assert.assertNotNull(company.getIncorporationCountryCode());
        Assert.assertNotNull(company.getIncorporationType());
        isAddressValid(company.getAddress());
    }

    private void isAddressValid(@Nullable final Address address) {
        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getStreet1());
        Assert.assertNotNull(address.getSubdivision());
        Assert.assertNotNull(address.getPostalCode());
        Assert.assertNotNull(address.getCountryCode());
        Assert.assertNotNull(address.getCity());
    }

    private void isListOfCompaniesValid(@Nullable final List<Company> companies) {
        Assert.assertNotNull(companies);
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
