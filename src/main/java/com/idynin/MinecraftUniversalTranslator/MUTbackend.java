package com.idynin.MinecraftUniversalTranslator;

public interface MUTbackend {
	public MUTtranslation getTranslation(MUTphrase s);

	public MUTtranslation getTranslation(String s);

	public MUTuser getUser(String uid);

	public void putTranslation(MUTtranslation m);

	public void putUser(MUTuser m);

}
