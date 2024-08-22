package com.coffee.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class ConnectionPool {

	// Connection Pool : DB와 연결을 관리하는 도구
	// 여러개의 연결을 미리 만들고, 필요할 때마다 제공
	public static class DBPool {
		private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		private static final String DB_URL = "jdbc:mysql://localhost:3306/coffee_db";
		private static final String DB_USER = "root";
		private static final String DB_PASSWORD = "1234";
		
		// DBCP 라이브러리
		static final BasicDataSource dbcp = new BasicDataSource();
		static {
			dbcp.setDriverClassName(JDBC_DRIVER);
			dbcp.setUrl(DB_URL);
			dbcp.setUsername(DB_USER);
			dbcp.setPassword(DB_PASSWORD);
			
		}
		
		public static Connection getDBPool() throws SQLException {
			return dbcp.getConnection();
		}
		
		
		
	}
	
	
}
