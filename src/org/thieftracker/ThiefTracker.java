package org.thieftracker;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ThiefTracker extends Application {

	private static Context context;
	private static Logger logger;
	private static MotionMonitor motionMonitor;
	private static Contacter contacter;
	private static HeartBeat heartBeat;
	private static LocationService locationService;
	private static PreferencesService preferencesService;
	private static Alarm alarm;
	private static String deviceId;
	private static BroadcastReceiver mReceiver;
		
	@Override
	public void onCreate() {
		
		Log.d("ThiefTracker", "ThiefTracker onCreate.");
		
		super.onCreate();		
		context = this.getApplicationContext();

		ThiefTracker.logger = new Logger();
		if (BuildConfig.DEBUG ) {
			ThiefTracker.logger.addLogRecorder
			(
				new ILogRecorder() {
					public void record(String message) {
						Log.d("ThiefTracker logger", message);
					}
				}
			);
		}
		
		ThiefTracker.preferencesService = new PreferencesService(context);
			
		ThiefTracker.motionMonitor = new MotionMonitor(context);
		
		// Workaround : 
		// To keep motion detector active when device goes to sleep
		// we detect the screen off, deactivate and reactivate 
		// detection
		ThiefTracker.mReceiver = new BroadcastReceiver() {
	         @Override
	         public void onReceive(Context context, Intent intent) {
	             // Check action just to be on the safe side.
	        	 if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
	        		 Log.d("ThiefTracker", "Screen off : stop and restart motion detection");
	        		 if ( ThiefTracker.getMotionMonitor().isDetectionActive() ) {
	        			 ThiefTracker.getMotionMonitor().stopMotionDetection();
	        			 ThiefTracker.getMotionMonitor().startMotionDetection();
	        		 }
	             }
	         }
		};	
		context.registerReceiver(ThiefTracker.mReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
		
		ThiefTracker.contacter = new Contacter();
		
		ThiefTracker.heartBeat = new HeartBeat();

		TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		ThiefTracker.deviceId = telephonyManager.getDeviceId();
			
		ThiefTracker.locationService = new LocationService(context);
		
		ThiefTracker.alarm = new Alarm(context);
	}
	
	public static Logger getLogger() {
		return( ThiefTracker.logger) ;
	}
	
	public static MotionMonitor getMotionMonitor() {
		return( ThiefTracker.motionMonitor );
	}
		
	public static Contacter getContacter() {
		return( ThiefTracker.contacter );
	}
		
	public static HeartBeat getHeartbeat() {
		return( ThiefTracker.heartBeat) ;
	}
	
	public static LocationService getLocationService() {
		return( ThiefTracker.locationService );
	}
	
	public static PreferencesService getPreferencesService() {
		return( ThiefTracker.preferencesService );
	}
	
	public static Alarm getAlarm() {
		return( ThiefTracker.alarm);
	}
	
	public static String getDeviceId() {
		return( ThiefTracker.deviceId );
	}
	
	public static Context getContext() {
		return( ThiefTracker.context );
	}
	
}
