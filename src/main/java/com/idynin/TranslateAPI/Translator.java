package com.idynin.TranslateAPI;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.idynin.MinecraftUniversalTranslator.TranslateCache;
import com.idynin.TranslateAPI.adapters.TranslatorAdapter;

public class Translator {

	public static final String userAgent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

	TranslateCache cache;

	private TranslatorAdapter adapter;

	public Translator(TranslatorAdapter adapter) {
		this.adapter = adapter;
	}

	public Translation translate(TranslationQuery query) {
		return adapter.translate(query);
	}

	public List<Translation> translate(List<TranslationQuery> queryList) {
		return adapter.translate(queryList);
	}

	public TranslationQuery queryConstructor(String sourceText,
			Language sourceLanguage, Language targetLanguage) {
		return new TranslationQuery(sourceText, sourceLanguage, targetLanguage);
	}

	public List<TranslationQuery> queryConstructor(String sourceText,
			Language sourceLanguage, EnumSet<Language> targetLanguages) {
		List<TranslationQuery> queries = new ArrayList<TranslationQuery>();
		for (Language lang : targetLanguages) {
			queries.add(new TranslationQuery(sourceText, sourceLanguage, lang));
		}
		return queries;
	}

}
