package com.idynin.MinecraftUniversalTranslator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.idynin.GoogleTranslateAPI.Language;

class MUTuser {

	static final Language defaultlanguage = Language.ENGLISH;

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

	String uid;

	HashMap<Language, Integer> languageMap;

	Language primaryLanguage = Language.ENGLISH;
	Language preferedLanguage = Language.ENGLISH;

	Integer primValue = 0;
	float primCertainty = 0;

	void refreshPrimaryLanguage() {
		Iterator<Entry<Language, Integer>> it = languageMap.entrySet()
				.iterator();

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
		primCertainty = primValue / sum;

	}

}