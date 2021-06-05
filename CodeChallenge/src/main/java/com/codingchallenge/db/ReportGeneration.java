package com.codingchallenge.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class ReportGeneration {
	private static final Logger LOGGER = Logger.getLogger(ReportGeneration.class.getName());

	public void printDatabaseReport() {
		LOGGER.info("Printing database report.");

		try {
			Connection connection = ConnectionFactory.getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT COUNT(*) FROM EVENT_DETAILS";
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()) {
				LOGGER.info("Total number of records in EVENT_DETAILS table: " + rs.getInt(1));
			}
			
			query = "SELECT COUNT(*) FROM EVENT_DETAILS where alert='true'";
			rs = statement.executeQuery(query);
			if(rs.next()) {
				LOGGER.info("Total number of records with event duration more than 4 ms in EVENT_DETAILS table: " + rs.getInt(1));
			}
		} catch (SQLException e) {
			LOGGER.severe("There was error while creating the statement.: " + e.getMessage());
		}
	}
}
