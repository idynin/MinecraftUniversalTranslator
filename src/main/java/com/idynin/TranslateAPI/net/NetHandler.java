package com.idynin.TranslateAPI.net;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NetHandler {

	private ExecutorService executor;

	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

	private static final int DEFAULT_THREADS = 5;

	protected static String getUserAgent() {
		return USER_AGENT;
	}

	public NetHandler() {
		this(DEFAULT_THREADS);
	}

	public NetHandler(int threads) {
		executor = Executors.newFixedThreadPool(threads);
	}

	public List<String> get(List<URL> urls) {
		String[] out = new String[urls.size()];

		List<Future<Response>> responses = new ArrayList<Future<Response>>(
				urls.size() + 1);

		for (int i = 0; i < urls.size(); i++) {
			responses.add(executor.submit(new Request(urls.get(i), i)));
		}

		long timeoutMs = 30000;
		long max = System.currentTimeMillis() + timeoutMs;

		while (!responses.isEmpty()) {
			if (System.currentTimeMillis() > max) {
				System.err.println("NetHandler get(list) timeout");
				break;
			}

			for (int i = 0; i < responses.size(); i++) {
				if (responses.get(i).isDone()) {

					try {
						out[responses.get(i).get().getId()] = responses
								.remove(i).get().getContents();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return Arrays.asList(out);

	}

	public String get(URL url) {

		try {
			return new Request(url).call().getContents();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";

	}
}
