package org.thieftracker;


public class Command_heartbeat extends Command {

	public void execute() {
	
		HeartBeat heartBeat = ThiefTracker.getHeartbeat();
		
		String subCommand = this.getParameter(0).toLowerCase();

		if ( subCommand.equals("period") ) {
			Integer period = Integer.parseInt(this.getParameter(1));
			heartBeat.setPeriod(period*1000);
			this.answerBack("Set HeartBeat period to "+period);
		}

		if ( subCommand.equals("url") ) {
			String url = this.getParameter(1);
			heartBeat.setUrl(url);
			PreferencesService preferencesService = ThiefTracker.getPreferencesService();
			preferencesService.setPreference("heartBeatUrl",url);
			this.answerBack("Set HeartBeat url to "+url);
		}

		if ( subCommand.equals("start") ) {
			heartBeat.start();
			this.answerBack("HeartBeat started.");
		}
			
		if ( subCommand.equals("stop") ) {
			heartBeat.stop();
			this.answerBack("HeartBeat stopped.");
		}

	}
	
}
