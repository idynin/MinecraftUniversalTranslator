package com.idynin.TranslateAPI.adapters;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;

import com.idynin.TranslateAPI.Language;
import com.idynin.TranslateAPI.net.NetHandler;
import com.idynin.TranslateAPI.net.TranslationQuery;
import com.idynin.TranslateAPI.net.TranslationResponse;

public class GoogleTranslatorAdapter extends TranslatorAdapter {

	private Language lastDetectedLanguage = null;

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

	private static final Language[] supportedLanguages = Language
			.getAllLanguages();

	private static URL constructURL(String text, Language sourceLanguage,
			Language targetLanguage) throws MalformedURLException {
		try {
			text = URLEncoder.encode(text, CharEncoding.UTF_8).replaceAll(
					"\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String constructedURLString = urlString;
		constructedURLString = StringUtils.replace(constructedURLString,
				"$${text}", text);
		constructedURLString = StringUtils.replace(constructedURLString,
				"$${sourceLanguage}", sourceLanguage.code);
		constructedURLString = StringUtils.replace(constructedURLString,
				"$${targetLanguage}", targetLanguage.code);

		System.out.println("Constructed URL: " + constructedURLString);
		return new URL(constructedURLString);
	}

	NetHandler netHandler;

	public GoogleTranslatorAdapter() {
		netHandler = new NetHandler(5);
	}

	@Override
	public Language getDetectedLanguage() {
		return lastDetectedLanguage;
	}

	@Override
	public Language[] getSupportedLanguages() {
		return supportedLanguages;
	}

	@Override
	public boolean isLanguageSupported(Language language) {
		return Arrays.asList(supportedLanguages).contains(language);
	}

	@Override
	public List<TranslationResponse> translate(List<TranslationQuery> queryList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String translate(String text, Language fromLanguage,
			Language toLanguage) {
		// TODO Auto-generated method stub
		try {
			URL translateURL = constructURL(text, fromLanguage, toLanguage);

			netHandler.get(translateURL);

		} catch (MalformedURLException e) {
			Logger.getAnonymousLogger().severe(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}

}
