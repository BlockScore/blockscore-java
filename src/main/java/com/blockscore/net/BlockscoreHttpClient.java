package com.blockscore.net;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

class BlockscoreHttpClient extends UrlConnectionClient {
    private static final int CONNECT_TIMEOUT_MILLIS = 30 * 1000;
    private static final int READ_TIMEOUT_MILLIS = 30 * 1000;

    private final OkUrlFactory mOkUrlFactory;

    public BlockscoreHttpClient() {
        mOkUrlFactory = new OkUrlFactory(generateDefaultHTTPClient());
    }

    public BlockscoreHttpClient(OkHttpClient client) {
        mOkUrlFactory = new OkUrlFactory(client);
    }

    private OkHttpClient generateDefaultHTTPClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        client.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        return client;
    }

    @Override
    protected HttpURLConnection openConnection(Request request) throws IOException {
        return mOkUrlFactory.open(new URL(request.getUrl()));
    }
}