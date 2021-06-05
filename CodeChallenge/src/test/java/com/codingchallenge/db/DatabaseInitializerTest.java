package com.codingchallenge.db;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class DatabaseInitializerTest {

	
	DatabaseInitializer databaseInitializer = new DatabaseInitializer();
	@Test
	public void testInitializeDatabase() throws SQLException {
		databaseInitializer.initializeDatabase();
		Connection connection = ConnectionFactory.getConnection();
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet tables = dbm.getTables(null, null, "EVENT_DETAILS", null);
		assertTrue(tables.next()); 
		
		tables = dbm.getTables(null, null, "EVENT_DETAILS_RAW", null);
		assertTrue(tables.next());
		
	}
	
}
