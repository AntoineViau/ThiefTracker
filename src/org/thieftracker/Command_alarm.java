package org.thieftracker;

import android.content.Context;

public class Command_alarm extends Command {
	
	public void execute(String params) {

		Context context = this.getContext();
		
		String[] tokens = params.split(" ");
		String subCommand = tokens[0].toLowerCase();

		if ( subCommand.equals("start".toLowerCase()) ) {
			SoundManager.getInstance(context).startAlarm();
			Contacter.getInstance(context).send("Alarm started.");
		}
		
		if ( subCommand.equals("stop".toLowerCase()) ) {
			SoundManager.getInstance(context).stopAlarm();			
			Contacter.getInstance(context).send("Alarm stopped.");
		}
	}

}
