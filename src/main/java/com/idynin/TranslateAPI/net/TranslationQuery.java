package com.idynin.TranslateAPI.net;

import com.idynin.TranslateAPI.Language;

public class TranslationQuery {
	String sourceText;
	Language fromLanguage;
	Language toLanguage;

	public TranslationQuery(String sourceText, Language fromLanguage,
			Language toLanguage) {
		this.sourceText = sourceText;
		this.fromLanguage = fromLanguage;
		this.toLanguage = toLanguage;
	}
}
