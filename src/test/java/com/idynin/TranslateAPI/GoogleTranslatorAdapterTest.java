/**
 * 
 */
package com.idynin.TranslateAPI;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.idynin.TranslateAPI.adapters.GoogleTranslatorAdapter;

/**
 * @author Ilya
 * 
 */
public class GoogleTranslatorAdapterTest {

	private GoogleTranslatorAdapter gta;

	private Language sourceLang = Language.AUTOMATIC_DETECTION;

	private Language realSourceLang = Language.ENGLISH;

	private Language targetLang = Language.RUSSIAN;

	private String inPhrase = "Test sentence 1? Test sentence two.";

	private String outPhrase = "Тест предложении 1 ? Тест приговор двум.";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gta = new GoogleTranslatorAdapter();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		gta = null;
	}

	/**
	 * Test method for
	 * {@link com.idynin.TranslateAPI.adapters.GoogleTranslatorAdapter#getDetectedLanguage()}
	 * .
	 */
	@Test
	public final void testGetDetectedLanguage() {
		gta.translate(inPhrase, sourceLang, targetLang);
		assertEquals(Thread.currentThread().getStackTrace()[1].getMethodName(),
				realSourceLang, gta.getDetectedLanguage());
		// fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.idynin.TranslateAPI.adapters.GoogleTranslatorAdapter#getSupportedLanguages()}
	 * .
	 */
	@Test
	public final void testGetSuportedLanguages() {
		assertArrayEquals(
				Thread.currentThread().getStackTrace()[1].getMethodName(),
				Language.getAllLanguages(), gta.getSupportedLanguages());

		// fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.idynin.TranslateAPI.adapters.GoogleTranslatorAdapter#isLanguageSupported(com.idynin.TranslateAPI.Language)}
	 * .
	 */
	@Test
	public final void testIsLanguageSupported() {
		for (Language lang : Language.values()) {
			assertTrue("Contains " + lang.name(), gta.isLanguageSupported(lang));
		}
		// fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.idynin.TranslateAPI.adapters.GoogleTranslatorAdapter#translate(java.lang.String, com.idynin.TranslateAPI.Language, com.idynin.TranslateAPI.Language)}
	 * .
	 */
	@Test
	public final void testTranslate() {
		String output = gta.translate(inPhrase, sourceLang, targetLang);
		assertEquals("ExpectedTranslation", outPhrase, output);
		// fail("Not yet implemented"); // TODO
	}

}
