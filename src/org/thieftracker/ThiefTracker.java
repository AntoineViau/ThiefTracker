package org.thieftracker;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * The ThiefTracker application creates all needed components
 * and launches the services 
 * @author Antoine
 *
 */
public class ThiefTracker extends Application {

	private static Context context;
	private static Logger logger;
	private static MotionMonitor motionMonitor;
	private static Contacter contacter;
	private static HeartBeat heartBeat;
	private static LocationService locationService;
	private static PreferencesService preferencesService;
	private static Alarm alarm;
	private static LocationTracker locationTracker;
	private static String deviceId;
	private static ComponentName serviceComponentName = null;

			
	@Override
	public void onCreate() {
		
		Log.d("ThiefTracker", "ThiefTracker application onCreate.");
		
		super.onCreate();		
		context = this.getApplicationContext();

		ThiefTracker.logger = new Logger();
		
		ThiefTracker.logger.addLogRecorder( new FileLogRecorder() );
		
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
				
		ThiefTracker.contacter = new Contacter();
		
		ThiefTracker.heartBeat = new HeartBeat();
				
		TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		ThiefTracker.deviceId = telephonyManager.getDeviceId();
			
		ThiefTracker.locationService = new LocationService(context);
		
		ThiefTracker.alarm = new Alarm(context);
		
		ThiefTracker.locationTracker = new LocationTracker(context);

		ThiefTracker.serviceComponentName = this.startService( new Intent(this, ThiefTrackerService.class) );
				
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
		return( ThiefTracker.heartBeat );
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

	public static LocationTracker getLocationTracker() {
		return( ThiefTracker.locationTracker );
	}
	
	public static ComponentName getServiceComponentName() {
		return( ThiefTracker.serviceComponentName);
	}

	
}
