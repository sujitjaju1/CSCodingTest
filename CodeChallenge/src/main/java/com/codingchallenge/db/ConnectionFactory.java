package com.codingchallenge.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionFactory {
	private static Connection connection;
	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

	public static Connection getConnection() {
		if (connection == null) {
			ServerCreater.createServer();
			try {
				LOGGER.info("Creating Connection object to connect to logdb database.");
				Class.forName("org.hsqldb.jdbcDriver");
				connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/logdb", "SA", "");
				LOGGER.info("Connection created successfully.");
			} catch (ClassNotFoundException e) {
				LOGGER.severe("Error creating the connection object: " + e.getMessage());
			} catch (SQLException e) {
				LOGGER.severe("Error creating the connection object: " + e.getMessage());
			}
		}
		return connection;
	}
	
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			LOGGER.severe("Error closing the connection object: " + e.getMessage());
		}
	}
}
