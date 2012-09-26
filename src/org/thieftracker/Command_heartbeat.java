package org.thieftracker;


public class Command_heartbeat extends Command {

	public void execute(String params) {
	
		String[] tokens = params.split(" ");
		String subCommand = tokens[0].toLowerCase();

		if ( subCommand.equals("period".toLowerCase()) ) {
			Integer period = Integer.parseInt(tokens[1]);
			HeartBeat.getInstance().setPeriod(period*1000);
			this.answerBack("Set HeartBeat period to "+period);
		}

		if ( subCommand.equals("url".toLowerCase()) ) {
			String url = tokens[1];
			HeartBeat.getInstance().setUrl(url);
			this.answerBack("Set HeartBeat url to "+url);
		}

		if ( subCommand.equals("start".toLowerCase()) ) {
			HeartBeat.getInstance().start();
			this.answerBack("HeartBeat started.");
		}
			
		if ( subCommand.equals("stop".toLowerCase()) ) {
			HeartBeat.getInstance().stop();
			this.answerBack("HeartBeat stopped.");
		}

	}
	
}
