import com.blockscore.models.WatchlistCandidate;
import com.blockscore.net.BlockscoreApiClient;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Simple test for the company endpoints.
 * Created by Tony Dieppa on 9/30/14.
 */
public class WatchlistTest {

    @Test
    public void fullVerificationFlowTest() throws ParseException {
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(true);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();


        Observable<WatchlistCandidate> step1 = apiClient.createWatchlistCandidate(createTestCandidate());
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
                Assert.assertTrue(true);
            }

            @Override
            public void onError(Throwable e) {
                Assert.assertTrue(false);
            }

            @Override
            public void onNext(WatchlistCandidate candidate) {
                Assert.assertTrue(candidate != null);
            }
        });
    }

    private WatchlistCandidate createTestCandidate() throws ParseException {
        WatchlistCandidate candidate = new WatchlistCandidate();
        candidate.setNote("12341234").setSSN("001").setDateOfBirth(new Date()).setFirstName("John")
                .setLastName("BredenKamp").setStreet1("1 Infinite Loop").setCity("Harare").setCountryCode("ZW");
        return candidate;
    }
}
