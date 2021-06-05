package com.codingchallenge.db;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.Test;

public class ConnectionFactoryTest {

	@Test
	public void testGetConnection() {
		Connection connection = ConnectionFactory.getConnection();
		assertNotNull(connection);
	}
	
}
