package com.idynin.MinecraftUniversalTranslator;

import com.google.gson.Gson;
import com.idynin.TranslateAPI.Language;

class MUTtranslation implements Comparable<MUTtranslation> {
	public static void main(String[] args) {
		MUTphrase p1 = new MUTphrase("Hello World!", Language.ENGLISH);
		MUTphrase p2 = new MUTphrase("Bonjour le monde", Language.FRENCH);

		MUTtranslation m = new MUTtranslation(p1, p2);
		System.out.println(m.asJson());
	}

	MUTphrase source;

	MUTphrase target;
	Language sourceLanguage;

	Language targetLanguage;

	private int hits;

	public MUTtranslation(MUTphrase s, MUTphrase t) {
		this.source = s;
		this.target = t;
		this.sourceLanguage = s.lang;
		this.targetLanguage = t.lang;

		hits = 0;
	}

	public String asJson() {
		return new Gson().toJson(this);
	}

	@Override
	public int compareTo(MUTtranslation arg0) {
		return Integer.valueOf(hits).compareTo(arg0.hits);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MUTtranslation) {
			MUTtranslation m = (MUTtranslation) obj;
			if (m.source.equals(this.source) && m.target.equals(this.target)) {
				return true;
			}
		}
		return false;
	}

	void hit() {
		hits++;
	}

	int hits() {
		return hits;
	}
}