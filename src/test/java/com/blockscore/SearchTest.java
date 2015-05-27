import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.Candidate;
import com.blockscore.models.Address;
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
 */
public class SearchTest {
    BlockscoreApiClient apiClient = setupBlockscoreApiClient();

    @Test
    public void searchTest() {
        Candidate candidate = createTestCandidate();
        isCandidateValid(candidate);

        SearchRequest request = new SearchRequest(candidate.getId());
        request.setMatchType(SearchRequest.MatchType.PERSON);
        WatchlistSearchResults results = apiClient.searchWatchlists(request);
        areSearchResultsValid(results);
    }

    @Test
    public void noCandidateFoundTest() {
        InvalidRequestException exception = null;

        Candidate candidate = createTestCandidate();
        isCandidateValid(candidate);

        try {
            SearchRequest request = new SearchRequest("1");
            WatchlistSearchResults results = apiClient.searchWatchlists(request);
            areSearchResultsValid(results);
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

        Candidate.Builder builder = new Candidate.Builder(apiClient);
        builder.setNote("12341234")
               .setSSN("001")
               .setDateOfBirth(new Date())
               .setFirstName("John")
               .setLastName("BredenKamp")
               .setAddress(address);
        return builder.create();
    }

    private void isCandidateValid(@Nullable final Candidate candidate) {
        Assert.assertNotNull(candidate);
        Assert.assertNotNull(candidate.getId());
    }

    private void areSearchResultsValid(@Nullable final WatchlistSearchResults results) {
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getSearchedLists());
        Assert.assertNotNull(results.getMatches());
        areMatchesValid(results.getMatches());
    }

    private void areMatchesValid(@Nullable final List<WatchlistMatch> matches) {
        Assert.assertNotNull(matches);
        for (WatchlistMatch match : matches) {
            isMatchValid(match);
        }
    }

    private void isMatchValid(@Nullable final WatchlistMatch match) {
        Assert.assertNotNull(match);
        Assert.assertNotNull(match.getMatchingInfo());
        Assert.assertNotNull(match.getWatchList());
        Assert.assertNotNull(match.getMatchingRecord());
        Assert.assertNotNull(match.getMatchingRecord().getId());
    }

    @NotNull
    private BlockscoreApiClient setupBlockscoreApiClient() {
        BlockscoreApiClient.useVerboseLogs(false);
        return new BlockscoreApiClient("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
    }
}
