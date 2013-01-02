package com.idynin.googletranslateapi;

import com.google.gson.Gson;

public class mutPhrase {
	String phrase;
	Language lang;

	public mutPhrase(String phrase, Language lang) {
		this.phrase = phrase;
		this.lang = lang;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof mutPhrase) {
			mutPhrase m = (mutPhrase) arg0;
			if (this.lang == m.lang && this.phrase.equals(m.phrase))
				return true;
		}
		return false;
	}
	
	public String asJson(){
		return new Gson().toJson(this);
	}
	public static void main(String[] args) {
		mutPhrase m = new mutPhrase("Hello World!", Language.ENGLISH);
		System.out.println(m.asJson());
	}
}
