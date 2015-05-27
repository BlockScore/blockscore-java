import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.Candidate;
import com.blockscore.models.Address;
import com.blockscore.models.results.WatchlistHit;
import com.blockscore.models.results.PaginatedResult;
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
 */
public class WatchlistTest {
    BlockscoreApiClient apiClient = setupBlockscoreApiClient();

    @Test
    public void watchListTest() {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        //Tests creation of a candidate
        Candidate candidate = createTestCandidate();
        isCandidateValid(candidate);

        Address address = (new Address()).setStreet1("1 Infinite Sea")
                                         .setCity("Atlantis")
                                         .setCountryCode("US");

        //Tests updating a candidate.
        Date date = null;
        try {
            date = formatter.parse("1986-08-23");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        candidate.setNote("1234123")
                 .setSSN("002")
                 .setDateOfBirth(date)
                 .setFirstName("Jack")
                 .setLastName("Sparrow")
                 .setAddress(address)
                 .save();

        didCandidateDataUpdate(candidate);

        //Tests getting a candidate
        candidate = apiClient.retrieveCandidate(candidate.getId());
        isCandidateValid(candidate);

        //Tests listing candidates
        PaginatedResult<Candidate> candidateList = apiClient.listCandidates();
        areCandidatesValid(candidateList.getData());

        //Tests watch list candidate history
        List<Candidate> history = apiClient.getCandidateHistory(candidateList.getData().get(0).getId())
                ;
        areCandidatesValid(history);

        //Tests the candidate hits
        PaginatedResult<WatchlistHit> hits = apiClient.getCandidateHits(candidate.getId());
        areHitsValid(hits.getData());

        //Tests deletion of a candidate
        candidate = apiClient.deleteCandidate(candidate.getId());
        isCandidateValid(candidate);
    }

    @Test
    public void getNonexistentCandidateTest() {
        InvalidRequestException exception = null;
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        try {
            Candidate candidate = apiClient.retrieveCandidate("1");
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
            PaginatedResult<WatchlistHit> candidate = apiClient.getCandidateHits("1");
            areHitsValid(candidate.getData());
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

    @NotNull
    private Candidate createTestCandidate() {
        Address address = (new Address()).setStreet1("1 Infinite Loop")
                                 .setCity("Harare")
                                 .setCountryCode("ZW");

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse("1980-08-23");
        } catch(ParseException e) {
            e.printStackTrace();
        }

        Candidate.Builder builder = new Candidate.Builder(apiClient);
        builder.setNote("12341234")
                 .setSSN("001")
                 .setDateOfBirth(date)
                 .setFirstName("John")
                 .setLastName("BredenKamp")
                 .setAddress(address);

        return builder.create();
    }

    @NotNull
    private Candidate createBadTestCandidate() {
        Candidate.Builder builder = new Candidate.Builder(apiClient);
        return builder.create();
    }

    private void isCandidateValid(@Nullable final Candidate candidate) {
        Assert.assertNotNull(candidate);
        Assert.assertNotNull(candidate.getId());
    }

    private void areCandidatesValid(@Nullable final List<Candidate> candidates) {
        Assert.assertNotNull(candidates);
        for (Candidate candidate : candidates) {
            isCandidateValid(candidate);
        }
    }

    private void didCandidateDataUpdate(@NotNull final Candidate candidate) {
        isCandidateValid(candidate);

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Assert.assertEquals(candidate.getDateOfBirth(), formatter.parse("1986-08-23"));
        } catch(ParseException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(candidate.getSSN(), "002");
        Assert.assertEquals(candidate.getNote(), "1234123");
        Assert.assertEquals(candidate.getFirstName(), "Jack");
        Assert.assertEquals(candidate.getLastName(), "Sparrow");
        Address address = candidate.getAddress();
        Assert.assertEquals(address.getStreet1(), "1 Infinite Sea");
        Assert.assertEquals(address.getCountryCode(), "US");
    }

    private void areHitsValid(@Nullable List<WatchlistHit> hits) {
        Assert.assertNotNull(hits);
        for (WatchlistHit hit : hits) {
            Assert.assertNotNull(hit.getMatchingInfo());
            isCandidateValid(hit);
        }
    }

    @NotNull
    private BlockscoreApiClient setupBlockscoreApiClient() {
        BlockscoreApiClient.useVerboseLogs(false);
        return new BlockscoreApiClient("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
    }
}
