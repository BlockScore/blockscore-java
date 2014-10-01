package com.blockscore;

import com.blockscore.models.WatchlistCandidate;
import com.blockscore.models.request.SearchRequest;
import com.blockscore.models.results.WatchlistSearchResults;
import com.blockscore.net.BlockscoreApiClient;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

import java.util.Date;

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
        Observable<WatchlistSearchResults> step2 = step1.map(new Func1<WatchlistCandidate, WatchlistSearchResults>() {
            @Override
            public WatchlistSearchResults call(WatchlistCandidate candidate) {
                SearchRequest request = new SearchRequest(candidate.getId());
                return apiClient.searchWatchlists(request).toBlocking().first();
            }
        });
        step2.subscribe(new Observer<WatchlistSearchResults>() {
            @Override
            public void onCompleted() {
                System.out.println("OK");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("FAIL");
            }

            @Override
            public void onNext(WatchlistSearchResults watchlistSearchResults) {

            }
        });
    }
}
