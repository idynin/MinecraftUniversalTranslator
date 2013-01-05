package com.idynin.MinecraftUniversalTranslator;

public interface MUTbackend {
	public void putTranslation(MUTtranslation m);
	
	public void putUser(MUTuser m);
	
	public MUTtranslation getTranslation(String s);
	
	public MUTtranslation getTranslation(MUTphrase s);
	
	public MUTuser getUser(String uid);
	
}
