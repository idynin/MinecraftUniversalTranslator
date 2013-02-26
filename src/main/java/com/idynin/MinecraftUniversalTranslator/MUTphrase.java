package com.idynin.MinecraftUniversalTranslator;

import com.google.gson.Gson;
import com.idynin.TranslateAPI.Language;

public class MUTphrase {
  public static void main(String[] args) {
    MUTphrase m = new MUTphrase("Hello World!", Language.ENGLISH);
    System.out.println(m.asJson());
  }

  String phrase;

  Language lang;

  public MUTphrase(String phrase, Language lang) {
    this.phrase = phrase;
    this.lang = lang;
  }

  public String asJson() {
    return new Gson().toJson(this);
  }

  @Override
  public boolean equals(Object arg0) {
    if (arg0 instanceof MUTphrase) {
      MUTphrase m = (MUTphrase) arg0;
      if (this.lang == m.lang && this.phrase.equals(m.phrase))
        return true;
    }
    return false;
  }
}
