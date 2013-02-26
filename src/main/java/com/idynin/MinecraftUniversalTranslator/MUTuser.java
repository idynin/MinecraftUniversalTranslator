package com.idynin.MinecraftUniversalTranslator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.idynin.TranslateAPI.Language;

class MUTuser {

  static Language defaultlanguage = Language.AUTOMATIC_DETECTION;

  String uid;

  HashMap<Language, Integer> languageMap;

  Language primaryLanguage = Language.AUTOMATIC_DETECTION;

  Language preferedLanguage = Language.ENGLISH;

  Integer primValue = 0;

  float primCertainty = 0;

  MUTuser(String uid) {
    this(uid, defaultlanguage);
  }

  MUTuser(String uid, Language lang) {
    this(uid, lang, lang);
  }

  MUTuser(String uid, Language primLang, Language prefLang) {
    this.uid = uid;
    this.primaryLanguage = primLang;
    this.preferedLanguage = prefLang;
  }

  public Language getPreferedLanguage() {
    return preferedLanguage;
  }

  Language getPrimaryLanguage() {
    if (primCertainty < 0.5)
      return defaultlanguage;
    return primaryLanguage;
  }

  void refreshPrimaryLanguage() {
    Iterator<Entry<Language, Integer>> it = languageMap.entrySet().iterator();

    int sum = 0;
    Entry<Language, Integer> e;
    while (it.hasNext()) {
      e = it.next();
      if (e.getValue() > primValue) {
        primValue = e.getValue();
        primaryLanguage = e.getKey();
      }
      sum += e.getValue();
    }
    if (sum == 0)
      return;
    primCertainty = primValue / sum;

  }

  public void setPreferedLanguage(Language lang) {
    this.preferedLanguage = lang;
  }

  public void addDetectedLanguage(Language detectedLang) {
    Integer count = languageMap.get(detectedLang);
    if (count == null) {
      count = 0;
    }
    count++;
    languageMap.put(detectedLang, count);
    refreshPrimaryLanguage();

  }

}
