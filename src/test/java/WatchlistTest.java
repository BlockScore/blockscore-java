import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.Candidate;
import com.blockscore.models.results.WatchlistHit;
import com.blockscore.net.BlockscoreApiClient;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

    @Test
    public void watchListTest() throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        //Tests creation of a candidate
        Candidate candidate = apiClient.createCandidate(createTestCandidate());
        isCandidateValid(candidate);

        //Tests updating a candidate.
        Candidate candidateUpdate = new Candidate();
        Date date = formatter.parse("1986-08-23");
        candidateUpdate.setNote("1234123").setSSN("002").setDateOfBirth(date).setFirstName("Jack")
                .setLastName("Sparrow").setStreet1("1 Infinite Sea").setCity("Atlantis").setCountryCode("US");
        candidate = apiClient.updateCandidate(candidate.getId(), candidateUpdate);
        didCandidateDataUpdate(candidate);

        //Tests getting a candidate
        candidate = apiClient.getCandidate(candidate.getId());
        isCandidateValid(candidate);

        //Tests listing candidates
        List<Candidate> candidateList = apiClient.listCandidates();
        areCandidatesValid(candidateList);

        //Tests watch list candidate history
        List<Candidate> history = apiClient.getCandidateHistory(candidateList.get(0).getId())
                ;
        areCandidatesValid(history);

        //Tests the candidate hits
        List<WatchlistHit> hits = apiClient.getCandidateHits(candidate.getId());
        areHitsValid(hits);

        //Tests deletion of a candidate
        candidate = apiClient.deleteCandidate(candidate.getId());
        isCandidateValid(candidate);
    }

    @Test
    public void updateNonexistentCandidateTest() {
        InvalidRequestException exception = null;
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        try {
            Candidate candidate = apiClient.updateCandidate("1", createBadTestCandidate())
                    ;
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
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        try {
            Candidate candidate = apiClient.getCandidate("1");
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
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        try {
            List<Candidate> candidate = apiClient.getCandidateHistory("1");
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
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        try {
            List<WatchlistHit> candidate = apiClient.getCandidateHits("1");
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
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        try {
            Candidate candidate = apiClient.deleteCandidate("1");
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
    private Candidate createTestCandidate() throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Candidate candidate = new Candidate();
        Date date = formatter.parse("1980-08-23");
        candidate.setNote("12341234").setSSN("001").setDateOfBirth(date).setFirstName("John")
                .setLastName("BredenKamp").setStreet1("1 Infinite Loop").setCity("Harare").setCountryCode("ZW");
        return candidate;
    }

    /**
     * Creates a bad test watchlist candidate.
     * @return Watch list candidate to test.
     */
    @NotNull
    private Candidate createBadTestCandidate() {
        return new Candidate();
    }

    /**
     * Determines if this candidate is valid.
     * @param candidate True if valid.
     */
    private void isCandidateValid(@Nullable final Candidate candidate) {
        Assert.assertNotNull(candidate);
        Assert.assertNotNull(candidate.getId());
    }

    /**
     * Tests a list of candidates.
     * @param candidates Candidates under test.
     */
    private void areCandidatesValid(@Nullable final List<Candidate> candidates) {
        Assert.assertNotNull(candidates);
        for (Candidate candidate : candidates) {
            isCandidateValid(candidate);
        }
    }

    /**
     * Tests if the candidate was updated. (PATCH)
     * @param candidate Candidate under test.
     * @throws ParseException
     */
    private void didCandidateDataUpdate(@NotNull final Candidate candidate) throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        isCandidateValid(candidate);
        Assert.assertEquals(candidate.getSSN(), "002");
        Assert.assertEquals(candidate.getNote(), "1234123");
        Assert.assertEquals(candidate.getDateOfBirth(), formatter.parse("1986-08-23"));
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
        Assert.assertNotNull(hits);
        for (WatchlistHit hit : hits) {
            Assert.assertNotNull(hit.getMatchingInfo());
            isCandidateValid(hit);
        }
    }

    /**
     * Sets up the API client.
     * @return API client.
     */
    @NotNull
    private BlockscoreApiClient setupBlockscoreApiClient() {
        BlockscoreApiClient.useVerboseLogs(false);
        return new BlockscoreApiClient("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
    }
}
