package com.idynin.TranslateAPI.net;

import com.idynin.TranslateAPI.Language;

public class TranslationResponse {
	boolean success;
	String sourceText;
	String translatedText;
	Language fromLanguage;
	Language toLanguage;

	public TranslationResponse(boolean success, String sourceText,
			String translatedText, Language fromLanguage, Language toLanguage) {

		this.success = success;
		this.sourceText = sourceText;
		this.translatedText = translatedText;
		this.fromLanguage = fromLanguage;
		this.toLanguage = toLanguage;
	}

}
