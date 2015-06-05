package com.blockscore.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.blockscore.models.base.BasicResponse;
import com.blockscore.net.BlockscoreApiClient;

class TestUtils {
  static void assertAddressesAreEquivalent(Address expected, Address actual) {
    assertEquals(expected.getCity(), actual.getCity());
    assertEquals(expected.getCountryCode(), actual.getCountryCode());
    assertEquals(expected.getPostalCode(), actual.getPostalCode());
    assertEquals(expected.getStreet1(), actual.getStreet1());
    assertEquals(expected.getStreet2(), actual.getStreet2());
    assertEquals(expected.getSubdivision(), actual.getSubdivision());
  }

  static void assertBasicResponseIsValid(final BasicResponse response) {
    assertNotNull(response);
    assertNotNull(response.getId());
    assertNotNull(response.getCreatedAtDate());
    assertNotNull(response.getUpdatedAtDate());
    assertFalse(response.isLiveMode());
  }

  static void assertBasicResponsesAreEquivalent(final BasicResponse expected, final BasicResponse actual) {
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getCreatedAtDate(), actual.getCreatedAtDate());
    assertEquals(expected.getUpdatedAtDate(), actual.getUpdatedAtDate());
    assertEquals(expected.isLiveMode(), actual.isLiveMode());
  }

  static void assertAddressIsValid(final Address address) {
    assertNotNull(address);
    assertNotNull(address.getStreet1());
    assertNotNull(address.getSubdivision());
    assertNotNull(address.getPostalCode());
    assertNotNull(address.getCountryCode());
    assertNotNull(address.getCity());
  }

  static BlockscoreApiClient setupBlockscoreApiClient() {
    BlockscoreApiClient.useVerboseLogs(false);
    return new BlockscoreApiClient("sk_test_a1ed66cc16a7cbc9f262f51869da31b3");
  }
}
