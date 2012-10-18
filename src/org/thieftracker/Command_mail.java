package org.thieftracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.os.Environment;

public class Command_mail extends Command {

	public void execute() {
	
		String account = this.getParameter(0).toLowerCase();
		String password = this.getParameter(1);
		String recipient = this.getParameter(2).toLowerCase();
		String content = this.getParameter(3);
	
		if ( content.equals("logs") ) {
			File logFile = new File( Environment.getExternalStorageDirectory()+File.separator, "thieftracker.log");
			int logFileSize = (int)logFile.length();
			try {
				
				BufferedReader br = new BufferedReader( new FileReader(logFile), (int)logFile.length() );
				char[] bodyContent = new char[logFileSize];
				br.read(bodyContent, 0,  logFileSize);
				br.close();
				try {
					Mail mail = new Mail(account, password);
					mail.addRecipient(recipient);
					mail.setBody( new String(bodyContent) );
					mail.send();
					this.answerBack("Mail sent.");
				}
				catch(Exception e) {
					this.answerBack("Send mail failed.");
				}
			}
			catch(Exception e) {
				this.answerBack("Could not read log file.");
			}
		}	
	}	
}
