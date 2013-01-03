package main.java.com.idynin.googletranslateapi;

public interface mutBackend {
	public void putTranslation(mutTranslation m);
	
	public void putUser(mutUser m);
	
	public mutTranslation getTranslation(String s);
	
	public mutTranslation getTranslation(mutPhrase s);
	
	public mutUser getUser(String uid);
	
}
