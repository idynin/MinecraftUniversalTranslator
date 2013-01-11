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

	/**
	 * @param username
	 * @return True if added player. False if already exists.
	 */
	public boolean add(String username) {
		if (!players.containsKey(username)) {
			players.put(username, new MUTuser(username));
			return true;
		}
		return false;
	}
	
	/**
	 * @param username
	 * @return True if loaded player.
	 */
	public boolean load(String username){
		if (!players.containsKey(username)) {
			players.put(username, new MUTuser(username));
			return true;
		}
		return false;
	}
	
	
}
