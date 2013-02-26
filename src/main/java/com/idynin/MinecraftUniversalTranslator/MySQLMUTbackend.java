package com.idynin.MinecraftUniversalTranslator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLMUTbackend implements MUTbackend {

  private Connection conn;

  private String address, username, password;

  public MySQLMUTbackend(String address, String database, String username, String password) {
    this.address =
        "jdbc:mysql://" + address + "/" + database + "?useUnicode=yes&characterEncoding=utf8";
    this.username = username;
    this.password = password;

    connect();

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
  public MUTtranslation getTranslation(MUTphrase s) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MUTtranslation getTranslation(String s) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public MUTuser getUser(String uid) {
    // TODO Auto-generated method stub
    return null;
  }

  private void initializeDatabase() {
    connect();

    // TODO: Create Tables method
  }

  @Override
  public void putTranslation(MUTtranslation m) {
    // TODO Auto-generated method stub

  }

  @Override
  public void putUser(MUTuser m) {
    // TODO Auto-generated method stub

  }

}
