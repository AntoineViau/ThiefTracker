package org.thieftracker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;

public class Command_config extends Command {
	
	public void execute(String params) {
		
		Context context = this.getContext();
		
		SharedPreferences settings = context.getSharedPreferences("ThiefTrackerPrefsFile", 0);
		String configString = "";
		
		configString += "For real : "+(SmsSender.getInstance(context).getForReal() ? "yes" : "no")+"\n";
		
		configString += "phone1 : "+settings.getString("phone1", "")+"\n";
		configString += "phone2 : "+settings.getString("phone2", "")+"\n";
		
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = context.getApplicationContext().registerReceiver(null, ifilter);		
		int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING) || (status == BatteryManager.BATTERY_STATUS_FULL);

		int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
		boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;			
		configString += "Battery charging : "+(isCharging ? "yes" : "no") +"\n";
					
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		configString += "Battery charged : "+((level / (float)scale)*100) +"%\n";
		
		
		configString += "HeartBeat : "+(HeartBeat.getInstance().isBeating() ? "Active" : "Inactive")+"\n";
		configString += "HeartBeat URL : "+HeartBeat.getInstance().getUrl()+"\n";
		configString += "HeartBeat period : "+HeartBeat.getInstance().getPeriod()/1000+"\n";
		
		configString += "Motion detection : "+(MotionDetector.getInstance(context).isDetectionActive() ? "Active" : "Inactive")+"\n";
		configString += "Motion sensitivity : "+MotionDetector.getInstance(context).getSensitivity() +"\n";
		configString += "Motion delay before reset : "+MotionDetector.getInstance(context).getDelayBeforeReset()/1000 +"\n";
		
		configString += "Location tracking : "+(LocationService.getInstance(context).isTrackingActive() ? "Active" : "Inactive")+"\n";
					
		SmsSender.getInstance(context).sendSms(this.getOriginatingAddress(), configString);	
	}

}
