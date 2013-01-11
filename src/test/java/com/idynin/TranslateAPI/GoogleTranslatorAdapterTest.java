/**
 * 
 */
package com.idynin.TranslateAPI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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
		Translation result = gta.translate(inPhrase, sourceLang, targetLang);
		assertEquals(Thread.currentThread().getStackTrace()[1].getMethodName(),
				realSourceLang, result.getFromLanguage());
	}

	/**
	 * Test method for
	 * {@link com.idynin.TranslateAPI.adapters.GoogleTranslatorAdapter#getSupportedLanguages()}
	 * .
	 */
	@Test
	public final void testGetSuportedLanguages() {
		assertTrue("testGetSuportedLanguages", gta.getSupportedLanguages()
				.containsAll(EnumSet.allOf(Language.class)));
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
	}

	/**
	 * Test method for
	 * {@link com.idynin.TranslateAPI.adapters.GoogleTranslatorAdapter#translate(java.lang.String, com.idynin.TranslateAPI.Language, com.idynin.TranslateAPI.Language)}
	 * .
	 */
	@Test
	public final void testTranslate() {
		Translation output = gta.translate(inPhrase, sourceLang, targetLang);
		assertEquals("ExpectedTranslation", outPhrase,
				output.getTranslatedText());
	}

	@Test
	public final void testTranslateList() {
		List<TranslationQuery> sourceList = new ArrayList<TranslationQuery>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(new TranslationQuery("test message",
						Language.AUTOMATIC_DETECTION, Language.RUSSIAN));
				add(new TranslationQuery("test message 1! and also",
						Language.AUTOMATIC_DETECTION, Language.GERMAN));
				add(new TranslationQuery("mmmpf lol I'm silly.",
						Language.AUTOMATIC_DETECTION, Language.NORWEGIAN));
			}
		};
		List<Translation> expectedResults = new ArrayList<Translation>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(new Translation("test message", "Тестовое сообщение",
						Language.ENGLISH, Language.RUSSIAN));
				add(new Translation("test message 1! and also",
						"Testnachricht 1! sowie", Language.ENGLISH,
						Language.GERMAN));
				add(new Translation("mmmpf lol I'm silly.",
						"mmmpf lol Jeg er dum .", Language.ENGLISH,
						Language.NORWEGIAN));
			}
		};

		List<Translation> actual = gta.translate(sourceList);

		assertTrue("testTranslateList", expectedResults.containsAll(actual));
	}

}
