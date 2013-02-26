package com.idynin.MinecraftUniversalTranslator;

import java.util.HashMap;
import java.util.HashSet;

public class MUTdatastore {
  private enum dsLoc {
    COMMON_CACHE, RECENT_CACHE, BACKEND, NONE;
  }

  final static int commonCacheSizeMax = 1000, recentCacheSizeMax = 1000, userCacheSizeMax = 100;

  HashMap<String, MUTtranslation> commonCache = new HashMap<String, MUTtranslation>(
      commonCacheSizeMax);
  HashMap<String, MUTtranslation> recentCache = new HashMap<String, MUTtranslation>(
      recentCacheSizeMax);

  HashSet<MUTuser> userCache = new HashSet<MUTuser>(userCacheSizeMax);

  int commonCacheSize = 0, recentCacheSize = 0, userCacheSize = 0;

  long requests = 0, commonCacheHits = 0, recentCacheHits = 0;

  public boolean contains(String s) {
    return !(getLocation(s) == dsLoc.NONE);
  }

  public MUTtranslation get(String s) {
    dsLoc loc = getLocation(s);
    if (loc != dsLoc.NONE) {
      // TODO return translation
    }
    return null;

  }

  private dsLoc getLocation(String s) {
    if (commonCache.containsKey(s))
      return dsLoc.COMMON_CACHE;
    if (recentCache.containsKey(s))
      return dsLoc.RECENT_CACHE;
    if (commonCache.containsKey(s))
      return dsLoc.COMMON_CACHE;
    return dsLoc.NONE;
  }

  public void put(MUTtranslation s) {
    putCache(s);
    putBackend(s);
  }

  private void putBackend(MUTtranslation s) {
    new Thread() {
      @Override
      public void run() {
        // TODO async backend
      };
    }.run();
  }

  private void putCache(MUTtranslation s) {
    // TODO
  }

}
