package com.codingchallenge.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

public class DatabaseInitializer {
	private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

	public void initializeDatabase() {
		Statement statement = null;
		try {
			Connection connection = ConnectionFactory.getConnection();
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "EVENT_DETAILS", null);
			if (tables.next()) {
				LOGGER.info("Table is already present. Hence not creating again");
			} else {
				// Table does not exist
				LOGGER.info("Creating the table Event_Details as it does not exist");
				statement = connection.createStatement();
				
				String query = "CREATE TABLE EVENT_DETAILS (" 
								+ "EVENT_ID VARCHAR(50) NOT NULL," 
								+ "EVENT_DURATION BIGINT,"
								+ "ALERT VARCHAR(5), " 
								+ "TYPE VARCHAR(50)," 
								+ "HOST VARCHAR(50),"
								+ "PRIMARY KEY(EVENT_ID)" + ")";
				
				int result = statement.executeUpdate(query);
				
				LOGGER.info("DB Table EVENT_DETAILS created successfully. " + result);
			}
			
			tables = dbm.getTables(null, null, "EVENT_DETAILS_RAW", null);
			if (tables.next()) {
				LOGGER.info("Table EVENT_DETAILS_RAW is already present. Hence not creating again");
			} else {
				// Table does not exist
				LOGGER.info("Creating the table EVENT_DETAILS_RAW as it does not exist");
				String query = "CREATE TABLE EVENT_DETAILS_RAW (" 
								+ "EVENT_ID VARCHAR(50) NOT NULL," 
								+ "TIMESTAMP BIGINT,"
								+ "TYPE VARCHAR(50)," 
								+ "HOST VARCHAR(50),"
								+ "STATE VARCHAR(20)"
								+ ")";
				int result = statement.executeUpdate(query);
				LOGGER.info("DB Table EVENT_DETAILS_RAW created successfully. " + result);
			}
		} catch (Exception e) {
			LOGGER.severe("Error while creating the table EVENT_DETAILS_RAW: " + e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e) {
				LOGGER.severe("Error while closing the statement: " + e.getMessage());
			}
		}
	}
}
