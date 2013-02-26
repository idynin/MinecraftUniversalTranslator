package com.idynin.TranslateAPI;

public class Translation {
  private boolean success;
  private String sourceText;
  private String translatedText;
  private Language fromLanguage;
  private Language toLanguage;

  /**
   * @return the success
   */
  public boolean isSuccess() {
    return success;
  }

  /**
   * @return the sourceText
   */
  public String getSourceText() {
    return sourceText;
  }

  /**
   * @param sourceText the sourceText to set
   */
  public void setSourceText(String sourceText) {
    this.sourceText = sourceText;
  }

  /**
   * @param toLanguage the toLanguage to set
   */
  public void setToLanguage(Language toLanguage) {
    this.toLanguage = toLanguage;
  }

  /**
   * @return the translatedText
   */
  public String getTranslatedText() {
    return translatedText;
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

  public Translation(boolean success, String sourceText, String translatedText,
      Language fromLanguage, Language toLanguage) {

    this.success = success;
    this.sourceText = sourceText;
    this.translatedText = translatedText;
    this.fromLanguage = fromLanguage;
    this.toLanguage = toLanguage;
  }

  public Translation(String sourceText, String translatedText, Language fromLanguage,
      Language toLanguage) {

    this.success = true;
    this.sourceText = sourceText;
    this.translatedText = translatedText;
    this.fromLanguage = fromLanguage;
    this.toLanguage = toLanguage;
  }

  @Override
  public String toString() {
    return "Translation:{sl:\"" + fromLanguage + "\",tl:\"" + toLanguage + "\",st:\"" + sourceText
        + "\",tt:\"" + translatedText + "\",s:" + success + "}";
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Translation)
      return this.toString().equals(((Translation) obj).toString());
    return false;
  }

}
