package com.blockscore.models;

import static com.blockscore.models.TestUtils.assertAddressIsValid;
import static com.blockscore.models.TestUtils.assertAddressesAreEquivalent;
import static com.blockscore.models.TestUtils.assertBasicResponseIsValid;
import static com.blockscore.models.TestUtils.assertBasicResponsesAreEquivalent;
import static com.blockscore.models.TestUtils.setupBlockscoreApiClient;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.net.BlockscoreApiClient;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Candidate unit tests.
 */
public class CandidateTest {
  private static BlockscoreApiClient client = setupBlockscoreApiClient();

  @Test
  public void testCandidateCreation() {
    Candidate candidate = createTestCandidate();
    assertCandidateIsValid(candidate);
  }

  @Test
  public void testCandidateCreation_EmptyCandidate() {
    Candidate candidate = createEmptyCandidate();
    assertCandidateIsValid(candidate);
  }

  @Test
  public void testCandidateRetrieval() {
    Candidate candidate = createTestCandidate();

    Candidate retrievedCandidate = client.retrieveCandidate(candidate.getId());
    assertCandidateIsValid(retrievedCandidate);

    assertCandidatesAreEquivalent(candidate, retrievedCandidate);
  }

  @Test
  public void testCandidateRetrieval_InvalidCandidateId() {
    InvalidRequestException expected = null;

    try {
      client.retrieveCandidate("1");
    } catch (InvalidRequestException e) {
      assertNotNull(e.getMessage());
      expected = e;
    }

    assertNotNull(expected);
  }

  @Test
  public void testCandidateUpdate() {
    Candidate candidate = createTestCandidate();

    Address address = (new Address()).setStreet1("1 Infinite Sea")
            .setCity("Atlantis")
            .setCountryCode("US");

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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

    assertCandidateUpdated(candidate);
  }

  @Test
  public void testWatchlistSearch() {
    Candidate candidate = createTestCandidate();

    PaginatedResult<WatchlistHit> results = candidate.searchWatchlists();
    // TODO: Update validation logic when sample data is available.
    //       Tests for different match_type & similarity_threshold
    //       requests would also be worthwhile.
    //       See note below.
    assertHitsAreValid(results.getData());
  }


  @Test
  public void testCandidateListing() {
    PaginatedResult<Candidate> candidateList = client.listCandidates();
    assertCandidatesAreValid(candidateList.getData());
  }

  @Test
  public void testCandidateHistory() {
    Candidate candidate = createTestCandidate();

    // Add updates to the candidate's update history
    candidate.setFirstName("Mickey").save();
    candidate.setLastName("Mouse").save();

    List<Candidate> history = candidate.getRevisionHistory();

    assertCandidatesAreValid(history);
    assertEquals("Mickey", history.get(0).getFirstName());
    assertEquals("Mouse", history.get(0).getLastName());
  }

  // NOTE: All commented code that follows requires deletion to be functional server-side.
  //       Currently deleting a candidate shows "deleted":true in the
  //       server response but you can still successfully retrieve/update/delete
  //       the deleted candidate which should not happen.

  @Test
  public void testCandidateDeletion() {
    //InvalidRequestException expected = null;

    Candidate candidate = createTestCandidate();

    candidate.delete();

    // try {
    //     client.retrieveCandidate(candidate.getId());
    // } catch (InvalidRequestException e) {
    //     assertNotNull(e.getMessage());
    //     expected = e;
    // }
    //
    // assertNotNull(expected);
  }

//  @Test
//  public void testWatchlistSearch_DeletedCandidate() {
//    InvalidRequestException expected = null;
//
//    Candidate candidate = createTestCandidate();
//    candidate.delete();
//
//    try {
//      candidate.searchWatchlists();
//    } catch (InvalidRequestException e) {
//      assertNotNull(e.getMessage());
//      expected = e;
//    }
//
//    assertNotNull(expected);
//  }

//  @Test
//  public void testCandidateUpdate_DeletedCandidate() {
//    InvalidRequestException expected = null;
//
//    Candidate candidate = createTestCandidate();
//    candidate.delete();
//
//    try {
//      candidate.save();
//    } catch (InvalidRequestException e) {
//      assertNotNull(e.getMessage());
//      expected = e;
//    }
//
//    assertNotNull(expected);
//  }

//  @Test
//  public void testRevisionHistory_DeletedCandidate() {
//    InvalidRequestException expected = null;
//
//    Candidate candidate = createTestCandidate();
//    candidate.delete();
//
//    try {
//      candidate.getRevisionHistory();
//    } catch (InvalidRequestException e) {
//      assertNotNull(e.getMessage());
//      expected = e;
//    }
//
//    assertNotNull(expected);
//  }

//  @Test
//  public void testPastHits_DeletedCandidate() {
//    InvalidRequestException expected = null;
//
//    Candidate candidate = createTestCandidate();
//    candidate.delete();
//
//    try {
//      candidate.getPastHits();
//    } catch (InvalidRequestException e) {
//      assertNotNull(e.getMessage());
//      expected = e;
//    }
//    assertNotNull(expected);
//  }

//  @Test
//  public void testCandidateDeletion_DeletedCandidate() {
//    InvalidRequestException expected = null;
//
//    Candidate candidate = createTestCandidate();
//    candidate.delete();
//
//    try {
//      candidate.delete();
//    } catch (InvalidRequestException e) {
//      assertNotNull(e.getMessage());
//      expected = e;
//    }
//
//    assertNotNull(expected);
//  }

  /*------------------*/
  /* Helper Functions */
  /*------------------*/

  private Candidate createTestCandidate() {
    return new Candidate.Builder(client).setFirstName("John").setLastName("BredenKamp").create();
  }

  private Candidate createEmptyCandidate() {
    Candidate.Builder builder = new Candidate.Builder(client);
    return builder.create();
  }

  private void assertCandidatesAreEquivalent(Candidate expected, Candidate actual) {
    assertBasicResponsesAreEquivalent(expected, actual);
    assertEquals(expected.getNote(), actual.getNote());
    assertEquals(expected.getFirstName(), actual.getFirstName());
    assertEquals(expected.getMiddleName(), actual.getMiddleName());
    assertEquals(expected.getLastName(), actual.getLastName());
    assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth());
    assertEquals(expected.getSsn(), actual.getSsn());
    assertEquals(expected.getPassport(), actual.getPassport());
    assertAddressesAreEquivalent(expected.getAddress(), actual.getAddress());
  }

  private void assertCandidateIsValid(final Candidate candidate) {
    assertBasicResponseIsValid(candidate);
  }

  private void assertCandidatesAreValid(final List<Candidate> candidates) {
    assertNotNull(candidates);
    for (Candidate candidate : candidates) {
      assertCandidateIsValid(candidate);
    }
  }

  private void assertCandidateUpdated(final Candidate candidate) {
    assertCandidateIsValid(candidate);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date newDate = null;
    try {
      newDate = formatter.parse("1986-08-23");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    assertEquals(newDate, candidate.getDateOfBirth());
    assertEquals("002", candidate.getSsn());
    assertEquals("1234123", candidate.getNote());
    assertEquals("Jack", candidate.getFirstName());
    assertEquals("Sparrow", candidate.getLastName());
    Address address = candidate.getAddress();
    assertNotNull(address);
    assertEquals("1 Infinite Sea", address.getStreet1());
    assertEquals("Atlantis", address.getCity());
    assertEquals("US", address.getCountryCode());
  }

  private void assertHitsAreValid(List<WatchlistHit> hits) {
    assertNotNull(hits);
    assertNotEquals(hits.size(), 0);
    for (WatchlistHit hit : hits) {
      assertHitIsValid(hit);
    }
  }

  private void assertHitIsValid(WatchlistHit hit) {
    assertNotNull(hit);
    assertNotNull(hit.getMatchingInfo());
    assertNotNull(hit.getWatchlist());
    assertNotNull(hit.getEntryType());
    assertNotNull(hit.getMatchingInfo());
    assertNotNull(hit.getConfidence());
    assertNull(hit.getUrl());
    assertNull(hit.getNotes());
    assertNull(hit.getTitle());
    assertNotNull(hit.getName());
    assertNotNull(hit.getAlternateNames());
    assertNotNull(hit.getDateOfBirth());
    assertNull(hit.getSsn());
    assertNotNull(hit.getPassports());
    assertNotNull(hit.getAddress());
    assertNotNull(hit.getAddress().getStreet1());
    assertNotNull(hit.getAddress().getCity());
    assertNotNull(hit.getAddress().getCountryCode());
    assertNotNull(hit.getRawAddress());
    assertNotNull(hit.getNames());
    assertEquals(hit.getNames().size(), 3);
    assertNotNull(hit.getBirths());
    assertEquals(hit.getBirths().size(), 1);
    assertNotNull(hit.getDocuments());
    assertEquals(hit.getDocuments().size(), 4);
    assertNotNull(hit.getAlternateNames());
    assertNotEquals(hit.getAddresses().size(), 0);
    for(Address address : hit.getAddresses()) {
      assertNotNull(address.getStreet1());
      assertNotNull(address.getCity());
      assertNotNull(address.getCountryCode());
    }
    assertDocumentsAreValid(hit.getDocuments());
  }

  private void assertDocumentsAreValid(List<Document> documents) {
    for (Document doc : documents) {
      assertNotNull(doc.getDocumentType());
      assertNotNull(doc.getValue());
      assertNotNull(doc.getCountryCode());
    }
  }
}
