import com.blockscore.common.CorporationType;
import com.blockscore.models.Address;
import com.blockscore.models.Company;
import com.blockscore.net.BlockscoreApiClient;
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

    @Test
    public void companyTest() throws ParseException {
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(false);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();

        Company company = apiClient.createCompany(createTestCompany()).toBlocking().first();
        isCompanyValid(company);

        company = apiClient.getCompany(company.getId()).toBlocking().first();
        isCompanyValid(company);

        List<Company> companies = apiClient.listCompanies().toBlocking().first();
        isListOfCompaniesValid(companies);
    }

    /**
     * Generates a sample company to use for this test suite.
     * @return Fake company.
     * @throws ParseException
     */
    private Company createTestCompany() throws ParseException {
        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");
        Company company = new Company();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("1980-08-23");
        return company.setEntityName("BlockScore").setTaxId("123410000").setIncorpDate(date)
                .setIncorpState("DE").setIncorpCountryCode("US").setIncorpType(CorporationType.CORP)
                .setDbas("BitRemit").setRegNumber("123123123").setEmail("test@example.com")
                .setURL("https://blockscore.com").setPhoneNumber("6505555555").setIPAddress("67.160.8.182")
                .setAddress(address);
    }

    /**
     * Tests to see if the company object is valid.
     * @param company Company to test.
     */
    private void isCompanyValid(Company company) {
        Assert.assertNotEquals(company, null);
        Assert.assertNotEquals(company.getId(), null);
        Assert.assertNotEquals(company.getEntityName(), null);
        Assert.assertNotEquals(company.getTaxId(), null);
        Assert.assertNotEquals(company.getIncorpCountryCode(), null);
        Assert.assertNotEquals(company.getIncorpType(), null);
        isAddressValid(company.getAddress());
    }

    /**
     * Examines the address and ensures it is valid.
     * @param address Address to test.
     */
    private void isAddressValid(Address address) {
        Assert.assertNotEquals(address, null);
        Assert.assertNotEquals(address.getStreet1(), null);
        Assert.assertNotEquals(address.getState(), null);
        Assert.assertNotEquals(address.getPostalCode(), null);
        Assert.assertNotEquals(address.getCountryCode(), null);
        Assert.assertNotEquals(address.getCity(), null);
    }

    /**
     * Tests a list of companies to ensure they are valid.
     * @param companies Companies to test.
     */
    private void isListOfCompaniesValid(List<Company> companies) {
        for (Company company : companies) {
            isCompanyValid(company);
        }
    }
}
