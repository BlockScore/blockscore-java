package com.blockscore;

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
                return apiClient.updateWatchlistCandidate(watchlistCandidate.getId(), watchlistCandidate)
                        .toBlocking().first();
            }
        });
        Observable<WatchlistCandidate> step3 = step2.map(new Func1<WatchlistCandidate, WatchlistCandidate>() {
            @Override
            public WatchlistCandidate call(WatchlistCandidate watchlistCandidate) {
                return apiClient.getWatchlistCandidate(watchlistCandidate.getId()).toBlocking().first();
            }
        });
        Observable<List<WatchlistCandidate>> step4 = step3.map(new Func1<WatchlistCandidate
                , List<WatchlistCandidate>>() {
            @Override
            public List<WatchlistCandidate> call(WatchlistCandidate watchlistCandidate) {
                return apiClient.listWatchlistCandidate().toBlocking().first();
            }
        });
        Observable<WatchlistCandidate> step5 = step4.map(new Func1<List<WatchlistCandidate>, WatchlistCandidate>() {
            @Override
            public WatchlistCandidate call(List<WatchlistCandidate> watchlistCandidates) {
                apiClient.getWatchlistCandidateHistory(watchlistCandidates.get(0).getId()).toBlocking().first();
                return watchlistCandidates.get(0);
            }
        });
        Observable<WatchlistCandidate> step6 = step5.map(new Func1<WatchlistCandidate, WatchlistCandidate>() {
            @Override
            public WatchlistCandidate call(WatchlistCandidate watchlistCandidate) {
                apiClient.getWatchlistCandidateHits(watchlistCandidate.getId()).toBlocking().first();
                return watchlistCandidate;
            }
        });
        Observable<WatchlistCandidate> step7 = step6.map(new Func1<WatchlistCandidate, WatchlistCandidate>() {
            @Override
            public WatchlistCandidate call(WatchlistCandidate watchlistCandidate) {
                return apiClient.deleteWatchlistCandidate(watchlistCandidate.getId()).toBlocking().first();
            }
        });
        step7.subscribe(new Observer<WatchlistCandidate>() {
            @Override
            public void onCompleted() {
                System.out.println("OK");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("ERROR");
            }

            @Override
            public void onNext(WatchlistCandidate watchlistCandidate) {

            }
        });
    }
}
