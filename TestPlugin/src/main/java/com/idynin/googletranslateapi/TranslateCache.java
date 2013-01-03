package main.java.com.idynin.googletranslateapi;

public interface TranslateCache {
	public boolean contains(String source);

	public String fetch(String source);

	public String store(String source, String language, String result);

	public void loadCache();

	public void storeCache();
	
	public void clearCache();
}
