package org.thieftracker;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class ConfigRetriever {

	public String getConfigString() {
		
		Contacter contacter = ThiefTracker.getContacter();
		HeartBeat heartBeat = ThiefTracker.getHeartbeat();
		MotionMonitor motionMonitor = ThiefTracker.getMotionMonitor();
		LocationService locationService = ThiefTracker.getLocationService();
		
		String configString = "";
		
		configString += "SMS For real : "+(contacter.getRealMode() ? "yes" : "no")+"\n";
		
		configString += "Phones numbers :\n";
		for(String phoneNumber :  contacter.getPhonesNumbers() ) {
			configString += " - "+phoneNumber+"\n";
		}
		configString += "Emails addresses :\n";
		for(String emailAddress : contacter.getEmailsAddresses() ) {
			configString += " - "+emailAddress+"\n";
		}
		
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = ThiefTracker.getContext().registerReceiver(null, ifilter);		
		int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING) || (status == BatteryManager.BATTERY_STATUS_FULL);

		//int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		//boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
		//boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;			
		configString += "Battery charging : "+(isCharging ? "yes" : "no") +"\n";
					
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		configString += "Battery charged : "+((level / (float)scale)*100) +"%\n";
		
		
		configString += "HeartBeat : "+(heartBeat.isBeating() ? "Active" : "Inactive")+"\n";
		String heartBeatUrl = heartBeat.getUrl();
		
		configString += "HeartBeat URL : "+( heartBeatUrl != null ? heartBeatUrl : "not set" )+"\n";
		
		configString += "HeartBeat period : "+heartBeat.getPeriod()/1000+"\n";
		
		configString += "Motion detection : "+(motionMonitor.isDetectionActive() ? "Active" : "Inactive")+"\n";
		
		configString += "Motion sensitivity : "+motionMonitor.getSensitivity() +"\n";
		
		configString += "Motion delay before reset : "+motionMonitor.getStableDelayBeforeDetection()/1000 +"\n";
		
		configString += "Location tracking : "+(locationService.isTrackingActive() ? "Active" : "Inactive")+"\n";
		
		return( configString );
	}
}
