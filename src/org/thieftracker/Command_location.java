package org.thieftracker;


public class Command_location extends Command {
	
	public void execute() {

		LocationService locationService = ThiefTracker.getLocationService();
			
		String subCommand = this.getParameter(0).toLowerCase();
	
		if ( subCommand.equals("quick") ) {

			locationService.getQuickLocation(
				new ILocationReceiver() {
					public void onReceiveLocation(double latitude, double longitude) {
						Contacter contacter = ThiefTracker.getContacter();
						contacter.send("Quick location : \nhttp://maps.google.com/maps?q="+latitude+","+longitude);
					}
				}
			);
			this.answerBack("Quick location requested.");
			return;
		}

		if ( subCommand.equals("accurate") ) {

			locationService.getAccurateLocation(
				new ILocationReceiver() {
					public void onReceiveLocation(double latitude, double longitude) {
						Contacter contacter = ThiefTracker.getContacter();
						contacter.send("Accurate location : \nhttp://maps.google.com/maps?q="+latitude+","+longitude);
					}
				}
			);
			this.answerBack("Accurate location requested.");
			return;
		}

		if ( subCommand.equals("gpsstatus") ) {
			this.answerBack("GpsStatus : "+(locationService.getGpsFix() ? "fixed" : "not fixed"));			
			return;
		}

	}

}
