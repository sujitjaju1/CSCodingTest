package com.codingchallenge.db;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.codingchallenge.model.EventDetails;
import com.codingchallenge.testdata.TestDataSetup;

public class AlertGenerationTest {

	@BeforeClass 
	public static void setup() throws SQLException {
		new DatabaseInitializer().initializeDatabase();
		List<EventDetails> details = TestDataSetup.getEventDetailsList();
		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM EVENT_DETAILS");
		statement.executeUpdate("DELETE FROM EVENT_DETAILS_RAW");
		
		DBInsertTask dbInsertTask = new DBInsertTask(details);
		
		dbInsertTask.run();
	}
	@Test
	public void testCalculateAlertRecordsAndSaveInEventDetailsTable() throws SQLException {
		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM EVENT_DETAILS");
		rs.next();
		int beforeCount = rs.getInt(1); 
		AlertGeneration alertGeneration = new AlertGeneration();
		alertGeneration.calculateAlertRecordsAndSaveInEventDetailsTable();
		rs = statement.executeQuery("SELECT COUNT(*) FROM EVENT_DETAILS");
		rs.next();
		int afterCount = rs.getInt(1);
		assertEquals(10, afterCount-beforeCount);
	}
	
	

}
