package org.thieftracker;

import android.content.Context;

public class Command_location extends Command {
	
	public void execute(String params) {

		Context context = this.getContext();
		
		String[] tokens = params.split(" ");
		String subCommand = tokens[0].toLowerCase();
	
		if ( subCommand.equals("quick".toLowerCase()) ) {

			LocationService.getInstance(context).getQuickLocation(
				new ILocationReceiver() {
					public void onReceiveLocation(double latitude, double longitude) {
						Contacter.getInstance(Command_location.this.getContext()).send("Quick location : \nhttp://maps.google.com/maps?q="+latitude+","+longitude);
					}
				}
			);
			this.answerBack("Quick location requested.");
		}

		if ( subCommand.equals("accurate".toLowerCase()) ) {

			LocationService.getInstance(context).getAccurateLocation(
				new ILocationReceiver() {
					public void onReceiveLocation(double latitude, double longitude) {
						Contacter.getInstance(Command_location.this.getContext()).send("Accurate location : \nhttp://maps.google.com/maps?q="+latitude+","+longitude);
					}
				}
			);
			this.answerBack("Accurate location requested.");
		}
		
		if ( subCommand.equals("startTracking".toLowerCase()) ) {
			LocationService.getInstance(context).startLocationTracking(
				new ILocationReceiver() {
					public void onReceiveLocation(double latitude, double longitude) {
						Contacter.getInstance(Command_location.this.getContext()).send("Tracking : \nhttp://maps.google.com/maps?q="+latitude+","+longitude);
					}
				}
			);
			this.answerBack("Tracking started.");
		}

		if ( subCommand.equals("stopTracking".toLowerCase()) ) {
			LocationService.getInstance(context).stopLocationTracking();
			this.answerBack("Tracking stopped.");
		}		
	}

}
