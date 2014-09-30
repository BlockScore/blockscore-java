package com.blockscore;

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
        apiClient.createVerification();
    }
}
