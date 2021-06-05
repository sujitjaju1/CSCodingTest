package com.codingchallenge.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.codingchallenge.db.DBInsertTask;
import com.codingchallenge.model.EventDetails;
import com.google.gson.Gson;

public class FileHandler {
	private static final Logger LOGGER = Logger.getLogger(FileHandler.class.getName());
	
	public void readFileAndInvokeTaskToInsertRecordInDB(String fileName) {
		LOGGER.info("Reading the file: " + fileName);
		File file = new File(fileName);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			Scanner scanner = new Scanner(fis);
			/* We are creating a thread pool and submitting batches of EventDetails to the threadpool 
			 * to be inserted into database.
			 */
			ExecutorService service = Executors.newFixedThreadPool(10);
			List<EventDetails> list = new ArrayList<EventDetails>(10000); 
			//Initializing the size so that resizing is avoided at runtime.
			Gson gson = new Gson();
			//Read the file line by line so that we do not occupy larger memory at once.
			while(scanner.hasNext()) {
				EventDetails e = gson.fromJson(scanner.next(), EventDetails.class);
				list.add(e);
				if(list.size()>10000) {
					DBInsertTask task = new DBInsertTask(new ArrayList<EventDetails>(list));
					list.clear();
					service.execute(task); 
				}
			}
			LOGGER.info("File Reading complete.");
			if(list.size()>0) {
				DBInsertTask task = new DBInsertTask(new ArrayList<EventDetails>(list));
				list.clear();
				service.execute(task);
			}
			
			//Shutdown the executor service
			service.shutdown();
			//Wait for all the threads to finish their execution before proceeding with the next step.
			service.awaitTermination(100, TimeUnit.MINUTES);
			scanner.close();
			fis.close();
			LOGGER.info("All threads have completed their execution.");
		} catch (FileNotFoundException e) {
			LOGGER.severe("Error while opening FileInputStream: " + e.getMessage());
		} catch (InterruptedException e) {
			LOGGER.severe("There was interrupted message while waiting on threads to finish their execution.: " + e.getMessage());
		} catch (IOException e) {
			LOGGER.severe("There was error while closing the FileInputStream: " + e.getMessage());
		}
	}
}
