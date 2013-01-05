package com.idynin.TranslateAPI.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NetHandler {

	private ExecutorService executor;

	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

	private static final int DEFAULT_THREADS = 3;

	protected static String getUserAgent() {
		return USER_AGENT;
	}

	InputStream emptyStream = new InputStream() {

		@Override
		public int read() throws IOException {
			// TODO Auto-generated method stub
			return 0;
		}
	};

	public NetHandler() {
		this(DEFAULT_THREADS);
	}

	public NetHandler(int threads) {
		executor = Executors.newFixedThreadPool(threads);
	}

	public List<InputStream> get(List<URL> urls) {
		List<InputStream> out = new LinkedList<InputStream>();
		List<Future<Response>> responses = new ArrayList<Future<Response>>(
				urls.size() + 1);
		for (URL url : urls) {
			responses.add(executor.submit(new Request(url)));
		}

		while (!responses.isEmpty()) {
			for (int i = 0; i < responses.size(); i++) {
				if (responses.get(i).isDone()) {
					try {
						out.add(responses.remove(i).get().getInputStream());
					} catch (InterruptedException e) {
						// TODO Fill with empty inputstream
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return out;

	}

	public InputStream get(URL url) {

		Future<Response> response = executor.submit(new Request(url));

		try {
			return response.get().getInputStream();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emptyStream;

	}
}
