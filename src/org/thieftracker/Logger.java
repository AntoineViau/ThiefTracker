package org.thieftracker;

import java.util.ArrayList;

import android.content.Context;

public class Logger {

	private static volatile Logger instance = null;
	private ArrayList<ILogRecorder> logRecorders;

	private Logger() {			
	}
	
	public static void instantiate() {
		Logger.instance = new Logger();
		Logger.instance.logRecorders = new ArrayList<ILogRecorder>();
	}
	
	public static Logger getInstance() {
		return( Logger.instance );
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
