package org.thieftracker;

import java.util.ArrayList;

public class Logger {

	private ArrayList<ILogRecorder> logRecorders;
	
	public Logger() {
		this.logRecorders = new ArrayList<ILogRecorder>();
	}

	public void addLogRecorder(ILogRecorder logRecorder) {
		this.logRecorders.add(logRecorder);
	}
	
	public void log(String text) {
		for(ILogRecorder logRecorder : this.logRecorders) {
			logRecorder.record(text);
		}
	}
}
