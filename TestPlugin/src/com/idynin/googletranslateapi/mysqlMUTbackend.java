package com.idynin.googletranslateapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysqlMUTbackend implements mutBackend {

	private Connection conn;

	private String address, database, username, password;

	public mysqlMUTbackend(String address, String database, String username,
			String password) {
		this.address = "jdbc:mysql://" + address + "/" + database
				+ "?useUnicode=yes&characterEncoding=utf8";
		this.username = username;
		this.password = password;

		connect();

	}

	private void initializeDatabase() {
		connect();
		
		//TODO: Create Tables method
	}

	private void connect() {
		try {
			if (conn == null || conn.isClosed()) {

				conn = DriverManager.getConnection(address, username, password);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void putTranslation(mutTranslation m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putUser(mutUser m) {
		// TODO Auto-generated method stub

	}

	@Override
	public mutTranslation getTranslation(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public mutTranslation getTranslation(mutPhrase s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public mutUser getUser(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

}
