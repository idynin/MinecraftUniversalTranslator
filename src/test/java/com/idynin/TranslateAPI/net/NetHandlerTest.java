/**
 * 
 */
package com.idynin.TranslateAPI.net;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ilya
 * 
 */
public class NetHandlerTest {

	private NetHandler nh;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		nh = new NetHandler();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		nh = null;
	}

	/**
	 * Test method for
	 * {@link com.idynin.TranslateAPI.net.NetHandler#get(java.util.List)}.
	 */
	@Test
	public final void testGetListOfURL() {
		int numFiles = 100;

		ArrayList<File> files = new ArrayList<File>(numFiles + 1);
		ArrayList<URL> urls = new ArrayList<URL>(numFiles + 1);

		File temp;
		FileWriter fw;

		int cnt = 0;

		while (files.size() < numFiles) {
			temp = new File(cnt + "_" + UUID.randomUUID().toString());

			if (!temp.exists()) {
				try {
					fw = new FileWriter(temp);
					fw.write("c_" + cnt++);
					fw.close();
					urls.add(temp.toURI().toURL());
					temp.deleteOnExit();
					files.add(temp);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		List<String> results = nh.get(urls);
		for (int i = 0; i < urls.size(); i++) {
			System.out.println();
			assertEquals("testGetListOfURL", "c_"
					+ files.get(i).getName().split("\\_")[0], results.get(i));
			files.get(i).delete();
		}

	}

	/**
	 * Test method for
	 * {@link com.idynin.TranslateAPI.net.NetHandler#get(java.net.URL)}.
	 */
	@Test
	public final void testGetURL() {
		try {
			File testfile = new File(UUID.randomUUID().toString());
			while (testfile.exists()) {
				testfile = new File(UUID.randomUUID().toString());
			}

			FileWriter fw = new FileWriter(testfile);
			fw.write("c:" + testfile.getName());
			fw.close();

			testfile.deleteOnExit();

			String result = nh.get(testfile.toURI().toURL());

			testfile.delete();

			assertEquals("testGetURL", "c:" + testfile.getName(), result);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link com.idynin.TranslateAPI.net.NetHandler#getUserAgent()}.
	 */
	@Test
	public final void testGetUserAgent() {
		assertEquals(
				"testGetUserAgent",
				"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2",
				NetHandler.getUserAgent());
	}

	/**
	 * Test method for
	 * {@link com.idynin.TranslateAPI.net.NetHandler#NetHandler()}.
	 */
	@Test
	public final void testNetHandler() {
		nh = new NetHandler();
		this.testGetListOfURL();
	}

	/**
	 * Test method for
	 * {@link com.idynin.TranslateAPI.net.NetHandler#NetHandler(int)}.
	 */
	@Test
	public final void testNetHandlerInt() {
		nh = new NetHandler(10);
		this.testGetListOfURL();
	}

}
