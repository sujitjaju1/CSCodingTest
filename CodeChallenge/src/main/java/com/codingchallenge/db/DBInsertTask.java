package com.codingchallenge.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.codingchallenge.model.EventDetails;

public class DBInsertTask implements Runnable{
	private List<EventDetails> list;
	private static final Logger LOGGER = Logger.getLogger(DBInsertTask.class.getName());
	public DBInsertTask(List<EventDetails> list) {
		this.list = list;
	}
	public void run() {
		processListOfEventDetails();
	}
	private void processListOfEventDetails() {
		Connection connection = ConnectionFactory.getConnection();
		
		try {
			String query = "INSERT INTO EVENT_DETAILS_RAW VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(query);
			for(EventDetails e: this.list) {
				ps.setString(1, e.getId());
				ps.setLong(2, e.getTimestamp());
				ps.setString(3, e.getType());
				ps.setString(4, e.getHost());
				ps.setString(5, e.getState());
				ps.addBatch();
			}
			ps.executeBatch();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.severe("There was error while inserting data into DB: " + e.getMessage());
		}
	}
}
