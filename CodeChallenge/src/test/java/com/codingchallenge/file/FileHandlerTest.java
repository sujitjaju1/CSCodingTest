package com.codingchallenge.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.BeforeClass;
import org.junit.Test;

import com.codingchallenge.db.ConnectionFactory;
import com.codingchallenge.db.DatabaseInitializer;

public class FileHandlerTest {
	@BeforeClass
	public static void setup() {
		new DatabaseInitializer().initializeDatabase();
	}
	@Test
	public void testReadFileAndInvokeTaskToInsertRecordInDB() throws SQLException {
		FileHandler fileHandler = new FileHandler();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("logfile.txt").getFile());
		Connection connection = ConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select count(*) from EVENT_DETAILS_RAW");
		rs.next();
		int beforeCount = rs.getInt(1);
		fileHandler.readFileAndInvokeTaskToInsertRecordInDB(file.getAbsolutePath());
		
		
		
		rs = statement.executeQuery("select count(*) from EVENT_DETAILS_RAW");
		rs.next();
		int afterCount = rs.getInt(1);
		assertEquals(6, afterCount - beforeCount);
	}
	
}
