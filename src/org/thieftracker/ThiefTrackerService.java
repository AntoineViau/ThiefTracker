package org.thieftracker;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class ThiefTrackerService extends Service {
	
	public IBinder onBind(Intent intent) {
		return(null);
	}
	
	public void onCreate() {
		
		super.onCreate();
		
		PreferencesService preferencesService = ThiefTracker.getPreferencesService();
		
		Contacter contacter = ThiefTracker.getContacter();
		contacter.addPhoneNumber( preferencesService.getPreference("phone1") );
		contacter.addPhoneNumber( preferencesService.getPreference("phone2") );
		contacter.addEmailAddress( preferencesService.getPreference("email1") );
		contacter.addEmailAddress( preferencesService.getPreference("email2") );
		contacter.setRealMode(false);
		if ( !BuildConfig.DEBUG ) {
			String realModePreference = preferencesService.getPreference("realMode");
			contacter.setRealMode( realModePreference.equals("on") );
		}
				
		HeartBeat heartBeat = ThiefTracker.getHeartbeat();
		try {
			Integer heartBeatPeriod = Integer.parseInt(preferencesService.getPreference("heartBeatPeriod"));
			if ( heartBeatPeriod > 0 ) {
				heartBeat.setPeriod(heartBeatPeriod);
			}
		}
		catch(NumberFormatException e) {
		}
		heartBeat.setUrl(preferencesService.getPreference("heartBeatUrl"));		
		heartBeat.setDeviceId(ThiefTracker.getDeviceId());		
		heartBeat.start();
		
		MotionMonitor motionMonitor = ThiefTracker.getMotionMonitor();
		try {
			Float motionSensitivity = Float.parseFloat(preferencesService.getPreference("motionSensitivity"));
			motionMonitor.setSensitivity(motionSensitivity);
		}
		catch(NumberFormatException e) {
		}	
		this.registerReceiver
		(
			new BroadcastReceiver() {
		         @Override
		         public void onReceive(Context context, Intent intent) {
		             // Check action just to be on the safe side.
		        	 if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
		        		 Log.d("ThiefTracker", "Screen off : reset motion hardware");
		        		 if ( ThiefTracker.getMotionMonitor().isDetectionActive() ) {
		        			 ThiefTracker.getMotionMonitor().resetHardware();
		        		 }
		             }
		         }
			}
			,new IntentFilter(Intent.ACTION_SCREEN_OFF)
		);		
		motionMonitor.startMotionDetection();

		Logger logger = ThiefTracker.getLogger();
		logger.log( "Config : \n"+(new ConfigRetriever()).getConfigString() );
	}
}
