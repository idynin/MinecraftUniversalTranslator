package com.idynin.TranslateAPI;

public class TranslationQuery {
  private String sourceText;
  private Language fromLanguage;
  private Language toLanguage;

  /**
   * @return the sourceText
   */
  public String getSourceText() {
    return sourceText;
  }

  /**
   * @return the fromLanguage
   */
  public Language getFromLanguage() {
    return fromLanguage;
  }

  /**
   * @return the toLanguage
   */
  public Language getToLanguage() {
    return toLanguage;
  }

  public TranslationQuery(String sourceText, Language fromLanguage, Language toLanguage) {
    this.sourceText = sourceText;
    this.fromLanguage = fromLanguage;
    this.toLanguage = toLanguage;
  }

  @Override
  public String toString() {
    return "TranslationQuery:{sl:\"" + fromLanguage + "\",tl:\"" + toLanguage + "\",st:\""
        + sourceText + "\"}";
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof TranslationQuery)
      return this.toString().equals(((Translation) obj).toString());
    return false;
  }
}
