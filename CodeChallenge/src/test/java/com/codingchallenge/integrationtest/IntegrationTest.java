package com.codingchallenge.integrationtest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
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
import com.codingchallenge.testdata.LargeFileCreater;

public class IntegrationTest {

	@Test
	public void testIntegration() throws SQLException, IOException {
		//Create Test Data with 2 million records
		new LargeFileCreater().createLargeFile();
		
		//Step 1: Initialize the database
		new DatabaseInitializer().initializeDatabase();
		
		//Clean the DB if records present due to other test cases
		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM EVENT_DETAILS");
		statement.executeUpdate("DELETE FROM EVENT_DETAILS_RAW");
		
		//Step 2: Read the file and insert records into DB
		new FileHandler().readFileAndInvokeTaskToInsertRecordInDB("largeFile.txt");
		//Step 3: Move the data to EVENTS_DETAILS along with duration and alert
		new AlertGeneration().calculateAlertRecordsAndSaveInEventDetailsTable();
		//Step 4: Print the report
		new ReportGeneration().printDatabaseReport();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM EVENT_DETAILS WHERE ALERT='true'");
		rs.next();
		int count = rs.getInt(1);
		assertEquals(100000, count);
	}
}
