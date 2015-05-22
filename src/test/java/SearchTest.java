import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.WatchlistCandidate;
import com.blockscore.models.request.SearchRequest;
import com.blockscore.models.results.WatchlistMatch;
import com.blockscore.models.results.WatchlistSearchResults;
import com.blockscore.net.BlockscoreApiClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Simple test for searching watchlists
 * Created by Tony Dieppa on 9/30/14.
 */
public class SearchTest {

    @Test
    public void searchTest() {
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        //Creates a watchlist candidate.
        WatchlistCandidate candidate = apiClient.createWatchlistCandidate(createTestCandidate()).toBlocking().first();
        isCandidateValid(candidate);

        //Tests searching for him/her.
        SearchRequest request = new SearchRequest(candidate.getId());
        request.setMatchType(SearchRequest.MatchType.PERSON);
        WatchlistSearchResults results = apiClient.searchWatchlists(request).toBlocking().first();
        areSearchResultsValid(results);
    }

    @Test
    public void noCandidateFoundTest() {
        InvalidRequestException exception = null;
        BlockscoreApiClient apiClient = setupBlockscoreApiClient();

        //Creates a watchlist candidate.
        WatchlistCandidate candidate = apiClient.createWatchlistCandidate(createTestCandidate()).toBlocking().first();
        isCandidateValid(candidate);

        try {
            SearchRequest request = new SearchRequest("1");
            WatchlistSearchResults results = apiClient.searchWatchlists(request).toBlocking().first();
            areSearchResultsValid(results);
        } catch (InvalidRequestException e) {
            Assert.assertNotNull(e.getMessage());
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    /**
     * Generates a sample watchlist candidate to use for this test suite.
     * @return Fake candidate.
     */
    @NotNull
    private WatchlistCandidate createTestCandidate() {
        WatchlistCandidate candidate = new WatchlistCandidate();
        candidate.setNote("12341234").setSSN("001").setDateOfBirth(new Date()).setFirstName("John")
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
    }

    /**
     * Determines if the results are valid.
     * @param results Results to test.
     */
    private void areSearchResultsValid(@Nullable final WatchlistSearchResults results) {
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getSearchedLists());
        Assert.assertNotNull(results.getMatches());
        areMatchesValid(results.getMatches());
    }

    /**
     * Cycles through the matches to determine validity.
     * @param matches Matches under test.
     */
    private void areMatchesValid(@Nullable final List<WatchlistMatch> matches) {
        Assert.assertNotNull(matches);
        for (WatchlistMatch match : matches) {
            isMatchValid(match);
        }
    }

    /**
     * Determines if the match is valid.
     * @param match Match under test.
     */
    private void isMatchValid(@Nullable final WatchlistMatch match) {
        Assert.assertNotNull(match);
        Assert.assertNotNull(match.getMatchingInfo());
        Assert.assertNotNull(match.getWatchList());
        Assert.assertNotNull(match.getMatchingRecord());

        Assert.assertNotNull(match.getMatchingRecord().getId());
    }

    /**
     * Sets up the API client.
     * @return API client.
     */
    @NotNull
    private BlockscoreApiClient setupBlockscoreApiClient() {
        BlockscoreApiClient.init("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
        BlockscoreApiClient.useVerboseLogs(false);
        return new BlockscoreApiClient();
    }
}
