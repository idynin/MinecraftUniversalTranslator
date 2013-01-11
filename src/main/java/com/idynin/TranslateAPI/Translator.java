package com.idynin.TranslateAPI;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.CharEncoding;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.idynin.MinecraftUniversalTranslator.FlatFileTranslateCache;
import com.idynin.MinecraftUniversalTranslator.TranslateCache;
import com.idynin.TranslateAPI.adapters.TranslatorAdapter;
import com.idynin.TranslateAPI.net.Request;
import com.idynin.TranslateAPI.net.Response;

public class Translator {

	private Language sourceLanguage = Language.AUTOMATIC_DETECTION;

	private Language targetLanguage = Language.ENGLISH;

	public static final String userAgent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

	TranslateCache cache;

	private TranslatorAdapter adapter;

	public Translator(TranslatorAdapter adapter) {
		this.adapter = adapter;
	}

	public Translation translate(String text) {
		return adapter.translate(text, sourceLanguage, targetLanguage);
	}

	public void setSourceLanguage(Language lang) {
		sourceLanguage = lang;
	}

	public void setTargetLanguage(Language lang) {
		targetLanguage = lang;
	}

}
