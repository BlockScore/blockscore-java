package com.blockscore;

import com.blockscore.common.CorporationType;
import com.blockscore.models.Address;
import com.blockscore.models.Company;
import com.blockscore.models.WatchlistCandidate;
import com.blockscore.net.BlockscoreApiClient;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

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

        WatchlistCandidate candidate = new WatchlistCandidate();
        candidate.setNote("12341234").setSSN("001").setDateOfBirth(new Date()).setFirstName("John")
                .setLastName("BredenKamp").setStreet1("1 Infinite Loop").setCity("Harare").setCountryCode("ZW");
        Observable<WatchlistCandidate> step1 = apiClient.createWatchlistCandidate(candidate);
        Observable<WatchlistCandidate> step2 = step1.map(new Func1<WatchlistCandidate, WatchlistCandidate>() {
            @Override
            public WatchlistCandidate call(WatchlistCandidate watchlistCandidate) {
                return apiClient.;
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
