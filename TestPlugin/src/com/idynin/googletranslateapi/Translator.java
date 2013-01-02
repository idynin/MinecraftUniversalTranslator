package com.idynin.googletranslateapi;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.lang3.CharEncoding;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Translator {

	private static final String autoSL = "auto";

	private static String autoTL = "en";

	private static final int rateLimitms = 1000;

	private static final String userAgent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

	private int threads;
	private ExecutorService executor;

	private long lastCall = 0l;

	private String lastDetectedLanguage = "";

	private File dataDirectory;

	TranslateCache cache;

	public Translator() {
		this(2, new File("translatorData"));
	}

	public Translator(File dataDirectory) {
		this(2, dataDirectory);
	}

	public String getTL() {
		return autoTL;
	}

	public Translator(int threads, File dataDirectory) {
		this.threads = threads;

		this.dataDirectory = dataDirectory;

		executor = Executors.newFixedThreadPool(this.threads);

		initializeDataDirectory();

		cache = new FlatFileTranslateCache(new File(
				this.dataDirectory.getAbsolutePath() + File.separator
						+ "translateCache.dat"));
	}

	private void initializeDataDirectory() {
		if (!this.dataDirectory.exists()) {
			this.dataDirectory.mkdirs();
		}
	}

	public String getLastDetectedLanguage() {
		return lastDetectedLanguage;
	}

	public void storeCache() {
		cache.storeCache();
	}

	public void clearCache() {
		cache.clearCache();
	}

	public void destroy() {
		if (!executor.isShutdown()) {
			executor.shutdown();
		}

		cache.storeCache();
	}

	public void setTargetLanguage(String language) {
		autoTL = language;
	}

	public String autoTranslate(String sourcetext) {
		return translate(sourcetext, autoSL, autoTL);
	}

	public String translate(String sourcetext, String sourceLang,
			String targetLang) {

		if (sourcetext == null) {
			return "translation error";
		}

		try {
			sourcetext = URLEncoder.encode(sourcetext, CharEncoding.UTF_8)
					.replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		if (cache.contains(sourcetext)) {
			System.out.println("Hit Cache");
			String result = cache.fetch(sourcetext);
			lastDetectedLanguage = result.substring(0, 2);
			return result.substring(3);
		}
		System.out.println("Not in cache, fetching...");

		try {
			// while (System.currentTimeMillis() - lastCall < rateLimitms) {
			// Thread.sleep(100);
			// }
			// lastCall = System.currentTimeMillis();

			// http://translate.google.com/translate_a/t?client=t&text=diese%20Worte.%20diese%20Worte.%20diese%20Worte.%20diese%20Worte.%20diese%20Worte.%20diese%20Worte.%20diese%20Worte.%20diese%20Worte.%20diese%20Worte.%20diese%20Worte.&hl=en&sl=auto&tl=en&ie=UTF-8&oe=UTF-8&multires=1&otf=2&pc=3&ssel=3&tsel=3&uptl=en&alttl=de&sc=1


			String urlString = "http://translate.google.com/translate_a/t?client=t&text="
					+ sourcetext
					+ "&hl=en&sl="
					+ sourceLang
					+ "&tl="
					+ targetLang
					+ "&ie=UTF-8&oe=UTF-8&multires=1&otf=2&pc=3&ssel=3&tsel=3&uptl=en&alttl=de&sc=1";

			URL longURL = new URL(urlString);
			//URL shortURL = new URL(
			//		"http://translate.google.com/translate_a/t?client=t&text="
			//				+ sourcetext + "&sl=" + sourceLang + "&tl="
			//				+ targetLang + "&ie=UTF-8&oe=UTF-8");
			JsonElement je = performGet(longURL);

			System.out.println(je);
			lastDetectedLanguage = je.getAsJsonArray().get(2).getAsString();
			System.out.println("Detected Language: " + lastDetectedLanguage);

			String translatedText = je.getAsJsonArray().get(0).getAsJsonArray()
					.get(0).getAsJsonArray().get(0).getAsString();

			cache.store(sourcetext, lastDetectedLanguage, translatedText);

			return translatedText;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		return "";
	}

	private JsonElement performGet(URL url) {
		try {
			Future<Response> response = executor.submit(new Request(url));

			InputStream body = response.get().getBody();

			return new JsonParser().parse(new InputStreamReader(body));

		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private class Request implements Callable<Response> {
		private URL url;

		public Request(URL url) {
			this.url = url;
		}

		@Override
		public Response call() throws Exception {
			URLConnection urlc = url.openConnection();
			urlc.setRequestProperty("User-Agent", userAgent);
			return new Response(urlc.getInputStream());
		}
	}

	private class Response {
		private InputStream body;

		public Response(InputStream body) {
			this.body = body;
		}

		public InputStream getBody() {
			return body;
		}
	}
}
