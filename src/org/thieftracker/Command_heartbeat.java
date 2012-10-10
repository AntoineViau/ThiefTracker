package org.thieftracker;


public class Command_heartbeat extends Command {

	public void execute(String params) {
	
		HeartBeat heartBeat = ThiefTracker.getHeartbeat();
		
		String[] tokens = params.split(" ");
		String subCommand = tokens[0].toLowerCase();

		if ( subCommand.equals("period".toLowerCase()) ) {
			Integer period = Integer.parseInt(tokens[1]);
			heartBeat.setPeriod(period*1000);
			this.answerBack("Set HeartBeat period to "+period);
		}

		if ( subCommand.equals("url".toLowerCase()) ) {
			String url = tokens[1];
			heartBeat.setUrl(url);
			PreferencesService preferencesService = ThiefTracker.getPreferencesService();
			preferencesService.setPreference("heartBeatUrl",url);
			this.answerBack("Set HeartBeat url to "+url);
		}

		if ( subCommand.equals("start".toLowerCase()) ) {
			heartBeat.start();
			this.answerBack("HeartBeat started.");
		}
			
		if ( subCommand.equals("stop".toLowerCase()) ) {
			heartBeat.stop();
			this.answerBack("HeartBeat stopped.");
		}

	}
	
}
