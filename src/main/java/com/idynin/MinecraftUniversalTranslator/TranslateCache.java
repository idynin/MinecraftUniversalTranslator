package com.idynin.MinecraftUniversalTranslator;

public interface TranslateCache {
	public void clearCache();

	public boolean contains(String source);

	public String fetch(String source);

	public void loadCache();

	public String store(String source, String language, String result);

	public void storeCache();
}
