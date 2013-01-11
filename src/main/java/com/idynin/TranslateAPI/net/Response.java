package com.idynin.TranslateAPI.net;

public class Response {
	private String contents;
	private int id;

	public Response(String contents, int id) {
		this.contents = contents;
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public String getContents() {
		return contents;
	}
}