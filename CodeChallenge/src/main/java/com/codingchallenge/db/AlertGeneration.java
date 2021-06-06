package com.codingchallenge.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class AlertGeneration {
	private static final Logger LOGGER = Logger.getLogger(AlertGeneration.class.getName());
	public void calculateAlertRecordsAndSaveInEventDetailsTable() {
		LOGGER.info("Moving data from EVENT_DETAILS_RAW table to EVENT_DETAILS table");
		Connection connection = ConnectionFactory.getConnection();
		try {
			Statement statement = connection.createStatement();
			String query = "INSERT INTO EVENT_DETAILS(EVENT_ID, EVENT_DURATION, ALERT) ("
					+ "SELECT EVENT_ID, "
					+ "MAX(TIMESTAMP)-MIN(TIMESTAMP), "
					+ "CASE WHEN MAX(TIMESTAMP)-MIN(TIMESTAMP)>4 THEN 'true' ELSE 'false' END "
					+ "FROM EVENT_DETAILS_RAW "
					+ "GROUP BY EVENT_ID)";
			statement.execute(query);
			LOGGER.info("Moved the data from EVENT_DETAILS_RAW to EVENT_DETAILS table");
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.severe("There was error while creating the statement");
		}
		
	} 
}
