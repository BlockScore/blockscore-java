package com.blockscore.net;

import com.blockscore.common.Constants;
import com.blockscore.net.UserAgentInterceptor;

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

  private final OkUrlFactory okUrlFactory;

  public BlockscoreHttpClient() {
    okUrlFactory = new OkUrlFactory(generateDefaultHttpClient());
  }

  public BlockscoreHttpClient(OkHttpClient client) {
    okUrlFactory = new OkUrlFactory(client);
  }

  private OkHttpClient generateDefaultHttpClient() {
    OkHttpClient client = new OkHttpClient();
    client.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
    client.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
    client.networkInterceptors().add(new UserAgentInterceptor(Constants.USER_AGENT));
    return client;
  }

  @Override
  protected HttpURLConnection openConnection(Request request) throws IOException {
    return okUrlFactory.open(new URL(request.getUrl()));
  }
}
