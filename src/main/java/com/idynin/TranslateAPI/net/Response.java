package com.idynin.TranslateAPI.net;

import java.io.InputStream;

public class Response {
	private InputStream stream;
	private int id;

	public Response(InputStream stream, int id) {
		this.stream = stream;
	}

	public int getId() {
		return id;
	}

	public InputStream getInputStream() {
		return stream;
	}
}