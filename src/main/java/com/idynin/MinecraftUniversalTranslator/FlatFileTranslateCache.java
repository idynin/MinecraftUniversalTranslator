package com.idynin.MinecraftUniversalTranslator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FlatFileTranslateCache implements TranslateCache {

  private HashMap<String, String> translationCache;
  private File cacheFile;

  public FlatFileTranslateCache() {
    this(new File("translationCache.dat"));
  }

  public FlatFileTranslateCache(File cacheFile) {
    this.cacheFile = cacheFile;
    translationCache = new HashMap<String, String>();
    loadCache();
  }

  @Override
  public void clearCache() {
    translationCache = new HashMap<String, String>();
    storeCache();
  }

  @Override
  public boolean contains(String source) {
    return translationCache.containsKey(source);
  }

  @Override
  public String fetch(String source) {
    return translationCache.get(source);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void loadCache() {

    if (cacheFile.exists()) {
      try {
        FileInputStream fis = new FileInputStream(cacheFile);
        ObjectInputStream ois =
            new ObjectInputStream(new BufferedInputStream(new GZIPInputStream(fis)));

        translationCache = (HashMap<String, String>) ois.readObject();
        ois.close();
        fis.close();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (translationCache == null) {
      clearCache();
    }
  }

  @Override
  public String store(String source, String language, String result) {
    return translationCache.put(source, language + ":" + result);

  }

  @Override
  public void storeCache() {

    try {
      FileOutputStream fos = new FileOutputStream(cacheFile);
      ObjectOutputStream oos =
          new ObjectOutputStream(new BufferedOutputStream(new GZIPOutputStream(fos)));

      oos.writeObject(translationCache);
      oos.flush();
      oos.close();
      fos.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
