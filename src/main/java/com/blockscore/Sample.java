package com.blockscore;

import com.blockscore.models.Address;
import com.blockscore.models.Identification;
import com.blockscore.models.Name;
import com.blockscore.models.Person;
import com.blockscore.net.BlockscoreApiClient;

/**
 * Example class on how to use the Blockscore API client.
 * Created by tealocean on 9/29/14.
 */
public class Sample {
    public static void main(final String[] args) {
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(true);
        BlockscoreApiClient apiClient = new BlockscoreApiClient();

        Person person = new Person();
        Name name = new Name("John", "Pearce", "Doe");
        Identification identification = new Identification();
        identification.setSSN("0000");
        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");



        apiClient.createVerification();
    }
}
