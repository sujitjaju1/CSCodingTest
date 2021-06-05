package com.codingchallenge.integrationtest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.codingchallenge.db.AlertGeneration;
import com.codingchallenge.db.ConnectionFactory;
import com.codingchallenge.db.DatabaseInitializer;
import com.codingchallenge.db.ReportGeneration;
import com.codingchallenge.file.FileHandler;

public class IntegrationTest {

	@Test
	public void testIntegration() throws SQLException {
		//Step 1: Initialize the database
		new DatabaseInitializer().initializeDatabase();
		
		//Clean the DB if records present due to other test cases
		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM EVENT_DETAILS");
		statement.executeUpdate("DELETE FROM EVENT_DETAILS_RAW");
		
		//Step 2: Read the file and insert records into DB
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("logfile.txt").getFile());
		new FileHandler().readFileAndInvokeTaskToInsertRecordInDB(file.getAbsolutePath());
		//Step 3: Move the data to EVENTS_DETAILS along with duration and alert
		new AlertGeneration().calculateAlertRecordsAndSaveInEventDetailsTable();
		//Step 4: Print the report
		new ReportGeneration().printDatabaseReport();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM EVENT_DETAILS WHERE ALERT='true'");
		rs.next();
		int count = rs.getInt(1);
		assertEquals(1, count);
	}
}
