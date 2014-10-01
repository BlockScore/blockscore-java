import com.blockscore.models.WatchlistCandidate;
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
public class WatchlistTest {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void watchListTest() throws ParseException {
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(true);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();

        //Tests creation of a candidate
        WatchlistCandidate candidate = apiClient.createWatchlistCandidate(createTestCandidate()).toBlocking().first();
        isCandidateValid(candidate);

        //Tests updating a candidate.
        WatchlistCandidate candidateUpdate = new WatchlistCandidate();
        Date date = FORMATTER.parse("1986-08-23");
        candidateUpdate.setNote("1234123").setSSN("002").setDateOfBirth(date).setFirstName("Jack")
                .setLastName("Sparrow").setStreet1("1 Infinite Sea").setCity("Atlantis").setCountryCode("US");
        candidate = apiClient.updateWatchlistCandidate(candidate.getId(), candidateUpdate).toBlocking().first();
        didCandidateDataUpdate(candidate);

        //Tests getting a candidate
        candidate = apiClient.getWatchlistCandidate(candidate.getId()).toBlocking().first();
        isCandidateValid(candidate);

        //Tests listing candidates
        List<WatchlistCandidate> candidateList = apiClient.listWatchlistCandidate().toBlocking().first();
        areCandidatesValid(candidateList);

        //Tests watch list candidate history
        List<WatchlistCandidate> history = apiClient.getWatchlistCandidateHistory(candidateList.get(0).getId())
                .toBlocking().first();
        areCandidatesValid(history);

//        Observable<WatchlistCandidate> step6 = step5.map(new Func1<WatchlistCandidate, WatchlistCandidate>() {
//            @Override
//            public WatchlistCandidate call(WatchlistCandidate watchlistCandidate) {
//                apiClient.getWatchlistCandidateHits(watchlistCandidate.getId()).toBlocking().first();
//                return watchlistCandidate;
//            }
//        });
//        Observable<WatchlistCandidate> step7 = step6.map(new Func1<WatchlistCandidate, WatchlistCandidate>() {
//            @Override
//            public WatchlistCandidate call(WatchlistCandidate watchlistCandidate) {
//                return apiClient.deleteWatchlistCandidate(watchlistCandidate.getId()).toBlocking().first();
//            }
//        });
//        step7.subscribe(new Observer<WatchlistCandidate>() {
//            @Override
//            public void onCompleted() {
//                Assert.assertTrue(true);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Assert.assertTrue(false);
//            }
//
//            @Override
//            public void onNext(WatchlistCandidate candidate) {
//                Assert.assertTrue(candidate != null);
//            }
//        });
    }

    /**
     * Creates a test watchlist candidate.
     * @return Watch list candidate to test.
     * @throws ParseException
     */
    @NotNull
    private WatchlistCandidate createTestCandidate() throws ParseException {
        WatchlistCandidate candidate = new WatchlistCandidate();
        Date date = FORMATTER.parse("1980-08-23");
        candidate.setNote("12341234").setSSN("001").setDateOfBirth(date).setFirstName("John")
                .setLastName("BredenKamp").setStreet1("1 Infinite Loop").setCity("Harare").setCountryCode("ZW");
        return candidate;
    }

    /**
     * Determines if this candidate is valid.
     * @param candidate True if valid.
     */
    private void isCandidateValid(@Nullable final WatchlistCandidate candidate) {
        Assert.assertNotNull(candidate);
        Assert.assertNotNull(candidate.getId());
        Assert.assertNotNull(candidate.getSSN());
        Assert.assertNotNull(candidate.getNote());
        Assert.assertNotNull(candidate.getDateOfBirth());
        Assert.assertNotNull(candidate.getFirstName());
        Assert.assertNotNull(candidate.getLastName());
        Assert.assertNotNull(candidate.getStreet1());
        Assert.assertNotNull(candidate.getCountryCode());
    }

    /**
     * Tests a list of candidates.
     * @param candidates Candidates under test.
     */
    private void areCandidatesValid(@Nullable final List<WatchlistCandidate> candidates) {
        Assert.assertNotNull(candidates);
        for (WatchlistCandidate candidate : candidates) {
            isCandidateValid(candidate);
        }
    }

    /**
     * Tests if the candidate was updated. (PATCH)
     * @param candidate Candidate under test.
     * @throws ParseException
     */
    private void didCandidateDataUpdate(@Nullable final WatchlistCandidate candidate) throws ParseException {
        isCandidateValid(candidate);
        Assert.assertEquals(candidate.getSSN(), "002");
        Assert.assertEquals(candidate.getNote(), "1234123");
        Assert.assertEquals(candidate.getDateOfBirth(), FORMATTER.parse("1986-08-23"));
        Assert.assertEquals(candidate.getFirstName(), "Jack");
        Assert.assertEquals(candidate.getLastName(), "Sparrow");
        Assert.assertEquals(candidate.getStreet1(), "1 Infinite Sea");
        Assert.assertEquals(candidate.getCountryCode(), "US");
    }
}
