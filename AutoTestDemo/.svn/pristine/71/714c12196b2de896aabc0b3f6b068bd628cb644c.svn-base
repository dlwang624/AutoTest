package com.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.utils.tools.Tool;



public class DBBLFactory {

	String jdbcurl = Tool.getProperty("jdbc").trim();
	String user = Tool.getProperty("user").trim();
	String password = Tool.getProperty("password").trim();
	Connection conn;
	

	public  DBBLFactory() throws SQLException, InstantiationException,	IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance(); // mysql
		this.conn = DriverManager.getConnection(jdbcurl + "/ch", user,	password);
	}
	

	public Connection getDb() {
		return this.conn;
	}

	public void close() throws SQLException {
		conn.close();
	}
}
