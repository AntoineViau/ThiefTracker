package org.thieftracker;

public class Command_tracking extends Command {
	
	public void execute() {
		
		String subCommand = this.getParameter(0).toLowerCase();
		
		LocationTracker locationTracker = ThiefTracker.getLocationTracker();
		
		if ( subCommand.equals("start") ) {
			locationTracker.start();
			this.answerBack("Tracking started.");
			return;
		}

		if ( subCommand.equals("stop") ) {
			locationTracker.stop();
			this.answerBack("Tracking stopped.");
			return;
		}
	}

}
