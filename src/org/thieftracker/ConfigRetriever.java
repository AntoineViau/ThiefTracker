package org.thieftracker;

import java.util.Date;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class ConfigRetriever {

	public String getConfigString() {
		
		Contacter contacter = ThiefTracker.getContacter();
		HeartBeat heartBeat = ThiefTracker.getHeartbeat();
		MotionMonitor motionMonitor = ThiefTracker.getMotionMonitor();
		LocationService locationService = ThiefTracker.getLocationService();
		LocationTracker locationTracker = ThiefTracker.getLocationTracker();
		
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
		
		Date lastMotionDetectionDate = motionMonitor.getLastMotionDetectionDate();
		configString += "Last motion detection : "+(lastMotionDetectionDate != null ? lastMotionDetectionDate.toString() : "No motion detected so far")+"\n";
		
		configString += "Motion sensitivity : "+motionMonitor.getSensitivity() +"\n";
		
		configString += "Nb seconds without moving to be stable : "+motionMonitor.getStableDelayBeforeDetection()/1000 +"\n";
		
		String locationTrackingUrl = locationTracker.getUrl();
		configString += "Location tracking url : "+(locationTrackingUrl != null ? locationTrackingUrl : "Not set")+"\n";

		configString += "Location tracking : "+(locationTracker.isTracking() ? "Active" : "Inactive")+"\n";
		
		configString += "Quick location pending : "+(locationService.quickLocationPending() ? "yes" : "no")+"\n";

		configString += "Accurate location pending : "+(locationService.accurateLocationPending() ? "yes" : "no")+"\n";
		
		configString += "Stable : "+(motionMonitor.isStable() ? "yes" : "no")+"\n";
		
		return( configString );
	}
}
