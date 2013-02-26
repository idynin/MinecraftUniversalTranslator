package com.idynin.TranslateAPI.adapters;

import java.util.EnumSet;
import java.util.List;

import com.idynin.TranslateAPI.Language;
import com.idynin.TranslateAPI.Translation;
import com.idynin.TranslateAPI.TranslationQuery;

public abstract class TranslatorAdapter {

  public abstract EnumSet<Language> getSupportedLanguages();

  public abstract boolean isLanguageSupported(Language language);

  public abstract List<Translation> translate(List<TranslationQuery> queryList);

  public abstract Translation translate(String text, Language fromLanguage, Language toLanguage);

  public abstract Translation translate(TranslationQuery query);
}
