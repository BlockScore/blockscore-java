import com.blockscore.models.Candidate;
import com.blockscore.models.Address;
import com.blockscore.models.results.PaginatedResult;
import com.blockscore.models.results.WatchlistHit;
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

    PaginatedResult<WatchlistHit> results = candidate.searchWatchlists();
    areMatchesValid(results.getData());

    //TODO: Add in tests for match_type & similarity_threshold requests (!)
  }

  //TODO: Add back. Cannot create a nonexistant candidate without creating
  //      and then deleting a candidate which is currently non-functional.
  // @Test
  // public void noCandidateFoundTest() {
  //     InvalidRequestException exception = null;

  //     Candidate candidate = createTestCandidate();
  //     isCandidateValid(candidate);

  //     try {
  //         SearchRequest request = new SearchRequest("1");
  //         WatchlistSearchResults results = candidate.searchWatchlists();
  //         areSearchResultsValid(results);
  //     } catch (InvalidRequestException e) {
  //         Assert.assertNotNull(e.getMessage());
  //         exception = e;
  //     }
  //     Assert.assertNotNull(exception);
  // }

  @NotNull
  private Candidate createTestCandidate() {
    Address address = (new Address()).setStreet1("1 Infinite Loop")
                     .setCity("Harare")
                     .setCountryCode("ZW");

    Candidate.Builder builder = new Candidate.Builder(apiClient);
    builder.setNote("12341234")
         .setSsn("001")
         .setDateOfBirth(new Date())
         .setFirstName("John")
         .setLastName("BredenKamp")
         .setAddress(address);
    return builder.create();
  }

  private void areMatchesValid(@Nullable final List<WatchlistHit> matches) {
    Assert.assertNotNull(matches);
    for (WatchlistHit match : matches) {
      isMatchValid(match);
    }
  }

  private void isMatchValid(@Nullable final WatchlistHit match) {
    Assert.assertNotNull(match);
    Assert.assertNotNull(match.getMatchingInfo());
    Assert.assertNotNull(match.getWatchlist());
    Assert.assertNotNull(match.getMatchingInfo());
  }

  @NotNull
  private BlockscoreApiClient setupBlockscoreApiClient() {
    BlockscoreApiClient.useVerboseLogs(false);
    return new BlockscoreApiClient("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
  }
}
