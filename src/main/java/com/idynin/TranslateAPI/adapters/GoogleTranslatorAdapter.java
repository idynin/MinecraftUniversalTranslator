package com.idynin.TranslateAPI.adapters;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.idynin.TranslateAPI.Language;
import com.idynin.TranslateAPI.TranslationQuery;
import com.idynin.TranslateAPI.Translation;
import com.idynin.TranslateAPI.net.NetHandler;

public class GoogleTranslatorAdapter extends TranslatorAdapter {

	private static final String urlString = "http://translate.google.com/translate_a/t?client=t&text="
			+ "$${text}"
			+ "&hl=en&sl="
			+ "$${sourceLanguage}"
			+ "&tl="
			+ "$${targetLanguage}"
			+ "&ie=UTF-8&oe=UTF-8&multires=1&prev=conf&"
			+ "psl="
			+ "$${sourceLanguage}"
			+ "&ptl="
			+ "$${targetLanguage}"
			+ "&otf=1&it=sel.10210%2Ctgtd.9202&ssel=3&tsel=4&uptl="
			+ "$${targetLanguage}" + "&sc=1";

	private static final EnumSet<Language> supportedLanguages = EnumSet
			.allOf(Language.class);

	private static URL constructURL(TranslationQuery tq)
			throws MalformedURLException {
		return constructURL(tq.getSourceText(), tq.getFromLanguage(),
				tq.getToLanguage());
	}

	private static URL constructURL(String text, Language sourceLanguage,
			Language targetLanguage) throws MalformedURLException {
		try {
			text = URLEncoder.encode(text, CharEncoding.UTF_8).replaceAll(
					"\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			// Better be supported...
			e.printStackTrace();
		}
		String constructedURLString = urlString;
		constructedURLString = StringUtils.replace(constructedURLString,
				"$${text}", text);
		constructedURLString = StringUtils.replace(constructedURLString,
				"$${sourceLanguage}", sourceLanguage.code);
		constructedURLString = StringUtils.replace(constructedURLString,
				"$${targetLanguage}", targetLanguage.code);

		return new URL(constructedURLString);
	}

	NetHandler netHandler;

	public GoogleTranslatorAdapter() {
		netHandler = new NetHandler(5);
	}

	@Override
	public EnumSet<Language> getSupportedLanguages() {
		return supportedLanguages;
	}

	@Override
	public boolean isLanguageSupported(Language language) {
		return supportedLanguages.contains(language);
	}

	@Override
	public List<Translation> translate(List<TranslationQuery> queryList) {
		List<URL> urls = new ArrayList<URL>(queryList.size() + 1);

		for (TranslationQuery tq : queryList) {
			try {
				if (tq.getFromLanguage().equals(tq.getToLanguage())) {
					continue;
				}
				urls.add(constructURL(tq));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		List<Translation> translations = new ArrayList<Translation>(
				queryList.size() + 1);

		List<String> results = netHandler.get(urls);
		Translation temp;
		for (int i = 0; i < results.size(); i++) {
			temp = parseTranslationFromJsonString(results.get(i));

			temp.setSourceText(queryList.get(i).getSourceText());
			temp.setToLanguage(queryList.get(i).getToLanguage());

			translations.add(temp);
		}

		return translations;
	}

	@Override
	public Translation translate(String text, Language fromLanguage,
			Language toLanguage) {

		if (fromLanguage.equals(toLanguage)) {
			return new Translation(text, text, fromLanguage, toLanguage);
		}
		try {
			URL translateURL = constructURL(text, fromLanguage, toLanguage);

			Translation result = parseTranslationFromJsonString(netHandler
					.get(translateURL));

			result.setSourceText(text);
			result.setToLanguage(toLanguage);

			return result;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Translation parseTranslationFromJsonString(String rawJson) {
		JsonElement je = new JsonParser().parse(rawJson);

		Iterator<JsonElement> partIterator = je.getAsJsonArray().get(0)
				.getAsJsonArray().iterator();

		String translation = "";
		while (partIterator.hasNext()) {
			translation += partIterator.next().getAsJsonArray().get(0)
					.getAsString();
		}

		return new Translation(null, translation,
				Language.getBestMatchByCode(je.getAsJsonArray().get(2)
						.getAsString()), null);
	}

	@Override
	public Translation translate(TranslationQuery query) {
		return translate(query.getSourceText(), query.getFromLanguage(),
				query.getToLanguage());
	}

}
