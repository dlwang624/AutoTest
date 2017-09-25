package com.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.czy.entity.Qcdb;

import com.utils.tools.Tool;

public class DB2Factory {

//	String jdbcurl = Tool.getProperty("jdbc1").trim();
//	String user = Tool.getProperty("user1").trim();
//	String password = Tool.getProperty("password1").trim();
	Connection conn;
	

	public  DB2Factory(Qcdb db) throws SQLException, InstantiationException,	IllegalAccessException, ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance(); // mysql
		
		this.conn = DriverManager.getConnection(db.getIp() + "; DatabaseName="+db.getDbname(), db.getUsername(),db.getPassword());
	}
	

	public Connection getDb() {
		return this.conn;
	}

	public void close() throws SQLException {
		conn.close();
	}
}