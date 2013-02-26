package com.idynin.TranslateAPI.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

import org.apache.commons.codec.CharEncoding;

public class Request implements Callable<Response> {
  private URL url;

  private int id;

  public Request(URL url) {
    this(url, 0);
  }

  public Request(URL url, int id) {
    this.url = url;
    this.id = id;
  }

  @Override
  public Response call() throws IOException {
    URLConnection urlc = url.openConnection();
    urlc.setRequestProperty("User-Agent", NetHandler.getUserAgent());

    BufferedReader br =
        new BufferedReader(new InputStreamReader(urlc.getInputStream(), CharEncoding.UTF_8));
    String output = "", line;

    while ((line = br.readLine()) != null) {
      output += line;
    }

    return new Response(output, id);
  }
}
