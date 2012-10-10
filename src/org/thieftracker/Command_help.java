package org.thieftracker;


public class Command_help extends Command {

	public void execute(String params) {
		
		Contacter contacter = ThiefTracker.getContacter();
		
		contacter.sendSms(
				"RealMode {on|off}\n"+
				"config\n" +
				"call {phone number}\n"+
				"HeartBeat url {address}\n" +
				"HeartBeat period {nb seconds}\n" +
				"HeartBeat start" + 
				"HeartBeat stop\n" + 
				"Preference phone1 {value}\n" +
				"Preference phone2 {value}\n" +
				"MotionDetection start\n" +
				"MotionDetection stop\n" +
				"MotionDetection sensitivity sensitivity {value}\n" + 
				"MotionDetection delayBeforeReset {nb seconds}\n" + 
				"Location quick\n" +
				"Location accurate\n" +
				"Location startTracking" +
				"Location stopTracking" +
				"Alarm start\n" +
				"Alarm playing\n" +
				"Alarm stop\n"
				,this.getOriginatingAddress()
				);
	}
	
}
