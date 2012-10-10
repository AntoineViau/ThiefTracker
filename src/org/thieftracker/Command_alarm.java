package org.thieftracker;

import android.content.Context;

public class Command_alarm extends Command {
	
	public void execute(String params) {

		String[] tokens = params.split(" ");
		String subCommand = tokens[0].toLowerCase();

		Alarm alarm = ThiefTracker.getAlarm();
		
		if ( subCommand.equals("start".toLowerCase()) ) {
			alarm.start();
			this.answerBack("Alarm started");
		}
		
		if ( subCommand.equals("playing".toLowerCase()) ) {
			this.answerBack("Alarm : "+(alarm.isPlaying() ? "yes" : "no"));
		}
		
		if ( subCommand.equals("stop".toLowerCase()) ) {
			alarm.stop();
			this.answerBack("Alarm stopped");
		}
	}

}
