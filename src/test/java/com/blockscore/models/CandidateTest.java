package com.blockscore.models;

import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.Candidate;
import com.blockscore.models.Address;
import com.blockscore.models.Document;
import com.blockscore.models.results.WatchlistHit;
import com.blockscore.models.results.PaginatedResult;
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
 * Company unit tests.
 */
public class CandidateTest {
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
             .setSsn("002")
             .setDateOfBirth(date)
             .setFirstName("Jack")
             .setLastName("Sparrow")
             .setAddress(address)
             .save();

    didCandidateDataUpdate(candidate);

    //Tests getting a candidate
    candidate = apiClient.retrieveCandidate(candidate.getId());
    isCandidateValid(candidate);

    //Tests deletion of a candidate
    //InvalidRequestException exception = null;
    candidate.delete();
    // TODO: Add back later. DELETE may not be functional server-side.
    //       Currently deleting a candidate shows "deleted":true in the
    //       server response but you can still successfully retrieve/update/delete
    //       the deleted candidate which should not happen.
    // try {
    //     client.retrieveCandidate(candidate.getId());
    // } catch (InvalidRequestException e) {
    //     Assert.assertNotNull(e.getMessage());
    //     exception = e;
    // }
    // Assert.assertNotNull(exception);
  }

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

  @Test
  public void listCandidatesTest() {
    Candidate candidate = createTestCandidate();

    PaginatedResult<Candidate> candidateList = apiClient.listCandidates();
    areCandidatesValid(candidateList.getData());

    List<Candidate> history = candidateList.getData()
                         .get(0)
                         .getRevisionHistory();
    areCandidatesValid(history);
  }

  @Test
  public void searchWatchlistTest() {
    Candidate candidate = (new Candidate.Builder(apiClient)).setFirstName("John")
                                .setLastName("BredenKamp")
                                .create();

    PaginatedResult<WatchlistHit> hits = candidate.searchWatchlists();
    areHitsValid(hits.getData());
  }

  // TODO: Add back later. DELETE may not be functional server-side.
  //       Currently deleting a candidate shows "deleted":true in the
  //       server response but you can still successfully retrieve/update/delete
  //       the deleted candidate which should not happen.
  // @Test
  // public void updateNonexistentCandidateTest() { //TODO
  //     InvalidRequestException exception = null;

  //     Candidate candidate = createTestCandidate();
  //     candidate.delete();

  //     //wait for the server to actually remove the candidate
  //     try {
  //         Thread.sleep(15000);
  //     } catch (InterruptedException e) {
  //         e.printStackTrace();
  //     }

  //     try {
  //         candidate.save();
  //     } catch (InvalidRequestException e) {
  //         Assert.assertNotNull(e.getMessage());
  //         exception = e;
  //     }
  //     Assert.assertNotNull(exception);
  // }

  @Test
  public void getNonexistentCandidateTest() {
    InvalidRequestException exception = null;

    try {
      Candidate candidate = apiClient.retrieveCandidate("1");
      isCandidateValid(candidate);
    } catch (InvalidRequestException e) {
      Assert.assertNotNull(e.getMessage());
      exception = e;
    }
    Assert.assertNotNull(exception);
  }

  //TODO: Add back. Cannot create a nonexistant candidate without creating
  //      and then deleting a candidate which is currently non-functional.
  // @Test
  // public void getNonexistentWatchCandidateHistoryTest() {
  //     InvalidRequestException exception = null;

  //     try {
  //         List<Candidate> candidate = client.getCandidateHistory("1");
  //         areCandidatesValid(candidate);
  //     } catch (InvalidRequestException e) {
  //         Assert.assertNotNull(e.getMessage());
  //         exception = e;
  //     }
  //     Assert.assertNotNull(exception);
  // }

  //TODO: Add back. Cannot create a nonexistant candidate without creating
  //      and then deleting a candidate which is currently non-functional.
  // @Test
  // public void getNonexistentCandidateHits() {
  //     InvalidRequestException exception = null;

  //     try {
  //         PaginatedResult<WatchlistHit> hits = //nonexistantcandidate.getPastHits();
  //         areHitsValid(candidate.getData());
  //     } catch (InvalidRequestException e) {
  //         Assert.assertNotNull(e.getMessage());
  //         exception = e;
  //     }
  //     Assert.assertNotNull(exception);
  // }

  // TODO: Add back later. DELETE may not be functional server-side.
  //       Currently deleting a candidate shows "deleted":true in the
  //       server response but you can still successfully retrieve/update/delete
  //       the deleted candidate which should not happen.
  // @Test
  // public void deleteNonexistentCandidateTest() {
  //     InvalidRequestException exception = null;

  //     Candidate candidate = createTestCandidate();
  //     candidate.delete();

  //     //wait for the server to actually remove the candidate
  //     // may not be neccesary
  //     try {
  //         Thread.sleep(500);
  //     } catch (InterruptedException e) {
  //         e.printStackTrace();
  //     }

  //     try {
  //         candidate.delete();
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

    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    try {
      date = formatter.parse("1980-08-23");
    } catch(ParseException e) {
      e.printStackTrace();
    }

    Candidate.Builder builder = new Candidate.Builder(apiClient);
    builder.setNote("12341234")
           .setSsn("001")
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

    Assert.assertEquals(candidate.getSsn(), "002");
    Assert.assertEquals(candidate.getNote(), "1234123");
    Assert.assertEquals(candidate.getFirstName(), "Jack");
    Assert.assertEquals(candidate.getLastName(), "Sparrow");
    Address address = candidate.getAddress();
    Assert.assertEquals(address.getStreet1(), "1 Infinite Sea");
    Assert.assertEquals(address.getCountryCode(), "US");
  }

  private void areHitsValid(@Nullable List<WatchlistHit> hits) {
    Assert.assertNotNull(hits);
    Assert.assertNotEquals(hits.size(), 0); // TODO: test with real data...
    for (WatchlistHit hit : hits) {
      Assert.assertNotNull(hit.getMatchingInfo());
      Assert.assertNotNull(hit.getWatchlist());
      Assert.assertNotNull(hit.getEntryType());
      Assert.assertNotNull(hit.getMatchingInfo());
      Assert.assertNotNull(hit.getConfidence());
      Assert.assertNull(hit.getUrl());
      Assert.assertNull(hit.getNotes());
      Assert.assertNull(hit.getTitle());
      Assert.assertNotNull(hit.getName());
      Assert.assertNotNull(hit.getAlternateNames());
      Assert.assertNotNull(hit.getDateOfBirth());
      Assert.assertNull(hit.getSsn());
      Assert.assertNotNull(hit.getPassports());
      Address address = hit.getAddress();
      Assert.assertNotNull(address);
      Assert.assertNotNull(address.getStreet1());
      Assert.assertNull(address.getStreet2());
      Assert.assertNotNull(address.getCity());
      Assert.assertNull(address.getSubdivision());
      Assert.assertNull(address.getPostalCode());
      Assert.assertNotNull(address.getCountryCode());
      Assert.assertNotNull(hit.getRawAddress());
      Assert.assertNotNull(hit.getNames());
      Assert.assertEquals(hit.getNames().size(), 3);
      Assert.assertNotNull(hit.getBirths());
      Assert.assertEquals(hit.getBirths().size(), 1);
      Assert.assertNotNull(hit.getDocuments());
      for(Document doc : hit.getDocuments()) {
        Assert.assertNotNull(doc.getDocumentType());
        Assert.assertNotNull(doc.getValue());
        Assert.assertNotNull(doc.getCountryCode());
      }
      Assert.assertEquals(hit.getDocuments().size(), 4);
      Assert.assertNotNull(hit.getAlternateNames());
      Assert.assertNotNull(hit.getAddresses());
    }
  }

  @NotNull
  private BlockscoreApiClient setupBlockscoreApiClient() {
    BlockscoreApiClient.useVerboseLogs(false);
    return new BlockscoreApiClient("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
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

}
