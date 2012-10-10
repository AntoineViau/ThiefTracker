package org.thieftracker;


public class Command_location extends Command {
	
	public void execute(String params) {

		LocationService locationService = ThiefTracker.getLocationService();
			
		String[] tokens = params.split(" ");
		String subCommand = tokens[0].toLowerCase();
	
		if ( subCommand.equals("quick".toLowerCase()) ) {

			locationService.getQuickLocation(
				new ILocationReceiver() {
					public void onReceiveLocation(double latitude, double longitude) {
						Contacter contacter = ThiefTracker.getContacter();
						contacter.send("Quick location : \nhttp://maps.google.com/maps?q="+latitude+","+longitude);
					}
				}
			);
			this.answerBack("Quick location requested.");
		}

		if ( subCommand.equals("accurate".toLowerCase()) ) {

			locationService.getAccurateLocation(
				new ILocationReceiver() {
					public void onReceiveLocation(double latitude, double longitude) {
						Contacter contacter = ThiefTracker.getContacter();
						contacter.send("Accurate location : \nhttp://maps.google.com/maps?q="+latitude+","+longitude);
					}
				}
			);
			this.answerBack("Accurate location requested.");
		}
		
		if ( subCommand.equals("startTracking".toLowerCase()) ) {
			locationService.startLocationTracking(
				new ILocationReceiver() {
					public void onReceiveLocation(double latitude, double longitude) {
						Contacter contacter = ThiefTracker.getContacter();
						contacter.send("Tracking : \nhttp://maps.google.com/maps?q="+latitude+","+longitude);
					}
				}
			);
			this.answerBack("Tracking started.");
		}

		if ( subCommand.equals("stopTracking".toLowerCase()) ) {
			locationService.stopLocationTracking();
			this.answerBack("Tracking stopped.");
		}		
	}

}
