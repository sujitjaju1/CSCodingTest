package com.codingchallenge.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.codingchallenge.model.EventDetails;
import com.codingchallenge.testdata.TestDataSetup;

public class ReportGenerationTest {

	@BeforeClass
	public static void setup() throws SQLException {
		new DatabaseInitializer().initializeDatabase();
		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM EVENT_DETAILS");
		statement.executeUpdate("DELETE FROM EVENT_DETAILS_RAW");
		List<EventDetails> details = TestDataSetup.getEventDetailsList();
		DBInsertTask dbInsertTask = new DBInsertTask(details);
		dbInsertTask.run();
		AlertGeneration alertGeneration = new AlertGeneration();
		alertGeneration.calculateAlertRecordsAndSaveInEventDetailsTable();
	}
	@Test
	public void testPrintDatabaseReport() throws SQLException {
		ReportGeneration generation = new ReportGeneration();
		generation.printDatabaseReport();
	}
	

}
