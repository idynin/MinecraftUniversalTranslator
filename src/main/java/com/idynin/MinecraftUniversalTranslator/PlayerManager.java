package com.idynin.MinecraftUniversalTranslator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.idynin.TranslateAPI.Language;

public class PlayerManager {
	Map<String, MUTuser> players;
	EnumSet<Language> targetLanguages;

	boolean targetLanguagesCurrent;

	public PlayerManager() {
		players = new HashMap<String, MUTuser>();
		targetLanguages = EnumSet.noneOf(Language.class);
		targetLanguagesCurrent = false;
	}

	public EnumSet<Language> getTargetLanguages() {
		if (!targetLanguagesCurrent) {
			this.updateTargetLanguages();
		}
		return targetLanguages;
	}

	private void updateTargetLanguages() {
		targetLanguages = EnumSet.noneOf(Language.class);
		for (MUTuser user : players.values()) {
			targetLanguages.add(user.getPrimaryLanguage());
		}
		targetLanguagesCurrent = true;
	}

	/**
	 * @param username
	 * @return True if added player. False if already exists.
	 */
	public boolean add(String username) {
		targetLanguagesCurrent = false;
		if (!players.containsKey(username)) {
			players.put(username, new MUTuser(username));
			return true;
		}
		return false;
	}

	public MUTuser get(String username) {
		return players.get(username);
	}

}
