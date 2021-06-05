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

public class DBInsertTaskTest {

	@BeforeClass
	public static void setup() {
		new DatabaseInitializer().initializeDatabase();
	}
	@Test
	public void testRun() throws SQLException {
		List<EventDetails> details = TestDataSetup.getEventDetailsList();
		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM EVENT_DETAILS");
		statement.executeUpdate("DELETE FROM EVENT_DETAILS_RAW");
		DBInsertTask dbInsertTask = new DBInsertTask(details);
		
		//Get count of event_details_raw before running the test 
		
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM EVENT_DETAILS_RAW");
		rs.next();
		int beforeCount = rs.getInt(1); 
		dbInsertTask.run();
		rs = statement.executeQuery("SELECT COUNT(*) FROM EVENT_DETAILS_RAW");
		rs.next();
		int afterCount = rs.getInt(1); 
		
		assertEquals(20, afterCount-beforeCount);
	}
	
}
