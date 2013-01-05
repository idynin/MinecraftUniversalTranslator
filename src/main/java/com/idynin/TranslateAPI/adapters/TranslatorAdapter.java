package com.idynin.TranslateAPI.adapters;

import java.util.List;

import com.idynin.TranslateAPI.Language;
import com.idynin.TranslateAPI.net.TranslationQuery;
import com.idynin.TranslateAPI.net.TranslationResponse;

public abstract class TranslatorAdapter {

	public abstract Language getDetectedLanguage();

	public abstract Language[] getSupportedLanguages();

	public abstract boolean isLanguageSupported(Language language);

	public abstract List<TranslationResponse> translate(
			List<TranslationQuery> queryList);

	public abstract String translate(String text, Language fromLanguage,
			Language toLanguage);
}
