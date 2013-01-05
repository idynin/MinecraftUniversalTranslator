package com.idynin.TranslateAPI.net;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

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
	public Response call() throws Exception {
		URLConnection urlc = url.openConnection();
		urlc.setRequestProperty("User-Agent", NetHandler.getUserAgent());
		return new Response(urlc.getInputStream(), id);
	}
}