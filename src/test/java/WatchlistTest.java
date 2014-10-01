import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.WatchlistCandidate;
import com.blockscore.models.results.WatchlistHit;
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
        BlockscoreApiClient.useVerboseLogs(false);
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

        //Tests the candidate hits
        List<WatchlistHit> hits = apiClient.getWatchlistCandidateHits(candidate.getId()).toBlocking().first();
        areHitsValid(hits);

        //Tests deletion of a candidate
        candidate = apiClient.deleteWatchlistCandidate(candidate.getId()).toBlocking().first();
        isCandidateValid(candidate);
    }

    @Test
    public void badCandidateCreationTest() throws ParseException {
        InvalidRequestException exception = null;
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(false);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();

        try {
            //Open issue for this: https://github.com/BlockScore/blockscore-api/issues/332
            //This should return an error, but instead allows it through. This code should be updated once the bug
            //is fixed.
            WatchlistCandidate candidate = apiClient.createWatchlistCandidate(createBadTestCandidate()).toBlocking()
                    .first();
            isCandidateValid(candidate);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            Assert.assertNotNull(e.getInvalidParam());
            exception = e;
        }
        //Assert.assertNotNull(exception); //Uncomment this once the issue is resolved to confirm the code works.
    }

    @Test
    public void updateNonexistentCandidateTest() {
        InvalidRequestException exception = null;
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(false);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();

        try {
            WatchlistCandidate candidate = apiClient.updateWatchlistCandidate("1", createBadTestCandidate())
                    .toBlocking().first();
            isCandidateValid(candidate);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    public void getNonexistentCandidateTest() {
        InvalidRequestException exception = null;
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(false);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();

        try {
            WatchlistCandidate candidate = apiClient.getWatchlistCandidate("1").toBlocking().first();
            isCandidateValid(candidate);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    public void getNonexistentWatchCandidateHistoryTest() {
        InvalidRequestException exception = null;
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(false);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();

        try {
            List<WatchlistCandidate> candidate = apiClient.getWatchlistCandidateHistory("1").toBlocking().first();
            areCandidatesValid(candidate);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    public void getNonexistentCandidateHits() {
        InvalidRequestException exception = null;
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(false);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();

        try {
            List<WatchlistHit> candidate = apiClient.getWatchlistCandidateHits("1").toBlocking().first();
            areHitsValid(candidate);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    public void deleteNonexistentCandidateTest() {
        InvalidRequestException exception = null;
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(false);
        final BlockscoreApiClient apiClient = new BlockscoreApiClient();

        try {
            WatchlistCandidate candidate = apiClient.deleteWatchlistCandidate("1").toBlocking().first();
            isCandidateValid(candidate);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
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
     * Creates a bad test watchlist candidate.
     * @return Watch list candidate to test.
     */
    @NotNull
    private WatchlistCandidate createBadTestCandidate() {
        return new WatchlistCandidate();
    }

    /**
     * Determines if this candidate is valid.
     * @param candidate True if valid.
     */
    private void isCandidateValid(@Nullable final WatchlistCandidate candidate) {
        Assert.assertNotNull(candidate);
        Assert.assertNotNull(candidate.getId());
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

    /**
     * Tests the watchlist hits.
     * @param hits Hits under test.
     */
    private void areHitsValid(@Nullable List<WatchlistHit> hits) {
        for (WatchlistHit hit : hits) {
            Assert.assertNotNull(hit.getMatchingInfo());
            isCandidateValid(hit);
        }
    }
}
