package com.codingchallenge.testdata;

import java.util.ArrayList;
import java.util.List;

import com.codingchallenge.model.EventDetails;

public class TestDataSetup {
	public static List<EventDetails> getEventDetailsList() {
		List<EventDetails> list = new ArrayList<EventDetails>();
		for(int i=0;i<10;i++) {
			EventDetails e = new EventDetails();
			e.setId("ABCDE"+i+1);
			e.setTimestamp(123456789);
			e.setState("STARTED");
			list.add(e);
			
			e = new EventDetails();
			e.setId("ABCDE"+i+1);
			if(i%3==0) {
				e.setTimestamp(123456792);	
			} else if(i%4==0) {
				e.setTimestamp(123456794);
				e.setType("APPLICATION");
				e.setHost("10.0.0.7");
			} else {
				e.setTimestamp(123456791);
			}
			e.setState("FINISHED");
			list.add(e);
			
		}
		return list;
	}
	
	
}
