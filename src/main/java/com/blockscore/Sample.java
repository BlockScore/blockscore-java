package com.blockscore;

import com.blockscore.common.CorporationType;
import com.blockscore.models.Address;
import com.blockscore.models.Company;
import com.blockscore.net.BlockscoreApiClient;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Example class on how to use the Blockscore API client.
 */
public class Sample {
    public static void main(final String[] args) {
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(true);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();

        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");
        Company company = new Company();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse("1980-08-23");
        } catch (ParseException e) {
            //Do nothing.
        }
        company.setEntityName("BlockScore").setTaxId("123410000").setIncorpDate(date)
                .setIncorpState("DE").setIncorpCountryCode("US").setIncorpType(CorporationType.CORP)
                .setDbas("BitRemit").setRegNumber("123123123").setEmail("test@example.com")
                .setURL("https://blockscore.com").setPhoneNumber("6505555555").setIPAddress("67.160.8.182")
                .setAddress(address);
        Observable<Company> step1 = apiClient.createCompany(company);
        Observable<Company> step2 = step1.map(new Func1<Company, Company>() {
            @Override
            public Company call(Company company) {
                return apiClient.getCompany(company.getId()).toBlocking().first();
            }
        });
        Observable<List<Company>> step3 = step2.map(new Func1<Company, List<Company>>() {
            @Override
            public List<Company> call(Company company) {
                return apiClient.listCompanies().toBlocking().first();
            }
        });
        step3.subscribe(new Observer<List<Company>>() {
            @Override
            public void onCompleted() {
                System.out.println("OK");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("ERROR");
            }

            @Override
            public void onNext(List<Company> companies) {

            }
        });
    }
}
