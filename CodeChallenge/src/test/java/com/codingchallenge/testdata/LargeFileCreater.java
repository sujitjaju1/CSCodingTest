package com.codingchallenge.testdata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

public class LargeFileCreater {
	
	@Test
	public void createLargeFile() throws IOException {
		File file = new File("largeFile.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for(int i=0;i<100000; i++) {
			bw.write("{\"id\":\"abcd"+i+"\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"12345\",\"timestamp\":1491377495213}");
			bw.newLine();
			bw.write("{\"id\":\"abcd"+i+"\",\"state\":\"FINISHED\",\"type\":\"APPLICATION_LOG\",\"host\":\"12345\",\"timestamp\":1491377495218}");
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}

}
