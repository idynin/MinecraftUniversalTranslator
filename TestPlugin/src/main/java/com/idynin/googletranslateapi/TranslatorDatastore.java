package main.java.com.idynin.googletranslateapi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class TranslatorDatastore {
	private enum dsLoc {
		COMMON_CACHE, RECENT_CACHE, BACKEND, NONE;
	}

	final static int commonCacheSizeMax = 1000, recentCacheSizeMax = 1000,
			userCacheSizeMax = 100;

	HashMap<String, mutTranslation> commonCache = new HashMap<String, mutTranslation>(
			commonCacheSizeMax);
	HashMap<String, mutTranslation> recentCache = new HashMap<String, mutTranslation>(
			recentCacheSizeMax);

	HashSet<mutUser> userCache = new HashSet<mutUser>(userCacheSizeMax);

	int commonCacheSize = 0, recentCacheSize = 0, userCacheSize = 0;

	long requests = 0, commonCacheHits = 0, recentCacheHits = 0;

	public void put(mutTranslation s) {
		putCache(s);
		putBackend(s);
	}

	public boolean contains(String s) {
		return !(getLocation(s) == dsLoc.NONE);
	}

	private dsLoc getLocation(String s) {
		if (commonCache.containsKey(s)) {
			return dsLoc.COMMON_CACHE;
		}
		if (recentCache.containsKey(s)) {
			return dsLoc.RECENT_CACHE;
		}
		if (commonCache.containsKey(s)) {
			return dsLoc.COMMON_CACHE;
		}
		return dsLoc.NONE;
	}

	public mutTranslation get(String s) {
		dsLoc loc = getLocation(s);
		if(loc != dsLoc.NONE){
			//TODO return translation
		}
		return null;
		
	}

	private void putCache(mutTranslation s) {
		//TODO
	}

	private void putBackend(mutTranslation s) {
		new Thread() {
			public void run() {
				//TODO async backend
			};
		}.run();
	}

}
