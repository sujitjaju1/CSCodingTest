package com.codingchallenge.db;

import org.hsqldb.Server;

public class ServerCreater {
	private static Server server;
	
	public static void createServer() {
		if(server==null) {
			server = new Server();
		}
		server.setDatabaseName(0, "logdb");
		server.setDatabasePath(0, "file:db/logdb");
		server.setSilent(true);
		if(server.getState()!=1) {
			server.start();
		}
	}
}
