package org.thieftracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

import android.os.Environment;

public class FileLogRecorder implements ILogRecorder {
	
	public FileLogRecorder() {
	}
	
	public void record(String text) {

		String state = Environment.getExternalStorageState();		

		if ( Environment.MEDIA_MOUNTED.equals(state) ) {
			Calendar now = Calendar.getInstance();
			String logString = 
					now.get(Calendar.YEAR)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.DAY_OF_MONTH)
					+" "
					+now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+":"+now.get(Calendar.SECOND)
					+" : "
					+text
					+"\n";
			
			File logFile = new File( Environment.getExternalStorageDirectory()+File.separator, "thieftracker.log");
			try {
				BufferedWriter bw = new BufferedWriter( new FileWriter(logFile, logFile.exists() ? true : false), logString.length() ); 
				bw.write(logString);
				bw.flush();
				bw.close();
			}
			catch(Exception e) {
			}
		} 
		else { 
		}	
	}

}
