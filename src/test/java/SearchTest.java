import com.blockscore.models.WatchlistCandidate;
import com.blockscore.models.request.SearchRequest;
import com.blockscore.models.results.WatchlistSearchResults;
import com.blockscore.net.BlockscoreApiClient;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

import java.text.ParseException;
import java.util.Date;

/**
 * Simple test for searching watchlists
 * Created by Tony Dieppa on 9/30/14.
 */
public class SearchTest {

    @Test
    public void fullVerificationFlowTest() throws ParseException {
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(true);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();


        Observable<WatchlistCandidate> step1 = apiClient.createWatchlistCandidate(createTestCandidate());
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
                Assert.assertTrue(true);
            }

            @Override
            public void onError(Throwable e) {
                Assert.assertTrue(false);
            }

            @Override
            public void onNext(WatchlistSearchResults watchlistSearchResults) {
                Assert.assertTrue(watchlistSearchResults != null);
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
