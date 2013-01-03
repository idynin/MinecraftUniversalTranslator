package com.idynin.GoogleTranslateAPI;

import com.google.gson.Gson;

class mutTranslation implements Comparable<mutTranslation> {
	mutPhrase source;
	mutPhrase target;

	Language sourceLanguage;
	Language targetLanguage;

	private int hits;

	void hit() {
		hits++;
	}

	int hits() {
		return hits;
	}

	public mutTranslation(mutPhrase s, mutPhrase t) {
		this.source = s;
		this.target = t;
		this.sourceLanguage = s.lang;
		this.targetLanguage = t.lang;
		
		hits = 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof mutTranslation) {
			mutTranslation m = (mutTranslation) obj;
			if (m.source.equals(this.source) && m.target.equals(this.target))
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(mutTranslation arg0) {
		return Integer.valueOf(hits).compareTo(arg0.hits);
	}

	public String asJson() {
		return new Gson().toJson(this);
	}

	public static void main(String[] args) {
		mutPhrase p1 = new mutPhrase("Hello World!", Language.ENGLISH);
		mutPhrase p2 = new mutPhrase("Bonjour le monde", Language.FRENCH);

		mutTranslation m = new mutTranslation(p1, p2);
		System.out.println(m.asJson());
	}
}