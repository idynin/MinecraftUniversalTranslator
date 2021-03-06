package com.idynin.TranslateAPI;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public enum Language {
  AUTOMATIC_DETECTION("auto"), AFRIKAANS("af"), ALBANIAN("sq"), ARABIC("ar"), AZERBAIJANI("az"), BASQUE(
      "eu"), BENGALI("bn"), BELARUSIAN("be"), BULGARIAN("bg"), CATALAN("ca"), CHINESE_SIMPLIFIED(
      "zh-CN"), CHINESE_TRADITIONAL("zh-TW"), CHINESE_CN("zh-CN"), CHINESE_TW("zh-TW"), CREOLE("ht"), CROATIAN(
      "hr"), CZECH("cs"), DANISH("da"), DUTCH("nl"), ENGLISH("en"), ESPERANTO("eo"), ESTONIAN("et"), FILIPINO(
      "tl"), FINNISH("fi"), FRENCH("fr"), GALICIAN("gl"), GEORGIAN("ka"), GERMAN("de"), GREEK("el"), GUJARATI(
      "gu"), HEBREW("iw"), HINDI("hi"), HUNGARIAN("hu"), ICELANDIC("is"), INDONESIAN("id"), IRISH(
      "ga"), ITALIAN("it"), JAPANESE("ja"), KANNADA("kn"), KOREAN("ko"), LATIN("la"), LATVIAN("lv"), LITHUANIAN(
      "lt"), MACEDONIAN("mk"), MALAY("ms"), MALTESE("mt"), NORWEGIAN("no"), PERSIAN("fa"), POLISH(
      "pl"), PORTUGUESE("pt"), ROMANIAN("ro"), RUSSIAN("ru"), SERBIAN("sr"), SLOVAK("sk"), SLOVENIAN(
      "sl"), SPANISH("es"), SWAHILI("sw"), SWEDISH("sv"), TAMIL("ta"), TELUGU("te"), THAI("th"), TURKISH(
      "tr"), UKRAINIAN("uk"), URDU("ur"), VIETNAMESE("vi"), WELSH("cy"), YIDDISH("yi");

  public static Language[] getAllLanguages() {

    Language[] allLanguages = Language.values();
    Arrays.sort(allLanguages);

    return allLanguages;
  }

  public static Language getBestMatch(String s) {
    if (s.length() < 3 || s.contains("-"))
      return getBestMatchByCode(s);
    return getBestMatchByName(s);
  }

  public static Language getBestMatchByCode(String s) {
    s = s.toLowerCase();

    int temp;

    TreeMap<Integer, LinkedList<Language>> distancemap =
        new TreeMap<Integer, LinkedList<Language>>();

    LinkedList<Language> templist;
    for (Language lang : values()) {

      temp = StringUtils.getLevenshteinDistance(s, lang.code.toLowerCase());

      if (distancemap.containsKey(temp)) {
        templist = distancemap.get(temp);
      } else {
        templist = new LinkedList<Language>();
      }

      templist.add(lang);
      distancemap.put(temp, templist);
    }

    return distancemap.firstEntry().getValue().getFirst();
  }

  public static Language getBestMatchByName(String s) {
    s = s.toUpperCase();

    int temp;

    TreeMap<Integer, LinkedList<Language>> distancemap =
        new TreeMap<Integer, LinkedList<Language>>();

    LinkedList<Language> templist;
    for (Language lang : values()) {

      temp = StringUtils.getLevenshteinDistance(s, lang.name());

      if (distancemap.containsKey(temp)) {
        templist = distancemap.get(temp);
      } else {
        templist = new LinkedList<Language>();
      }

      templist.add(lang);
      distancemap.put(temp, templist);
    }

    return distancemap.firstEntry().getValue().getFirst();
  }

  public String code;

  Language(String code) {
    this.code = code;
  }
}
