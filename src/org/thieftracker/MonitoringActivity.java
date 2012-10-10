package org.thieftracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * The MonitoringActivity is the launched activity and the only one (so far)
 * displayed to the user. Its purposes are to launch the application components
 * and to display logs to the the user.
 * The whole bunch of code at the end is here to display a ListView smoothly with
 * log strings. 
 * @author Antoine
 *
 */
public class MonitoringActivity extends Activity implements ILogRecorder {
		
	private ListView listView;
	private ArrayList<String> logStrings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.d("MonitoringActivity", "onCreate");
		
		super.onCreate(savedInstanceState);

		Logger logger = ThiefTracker.getLogger();		
		setContentView(R.layout.activity_monitoring);	
		this.logStrings = new ArrayList<String>();
		this.listView = (ListView) findViewById(R.id.logging);
		this.listView.setAdapter( new CustomAdapter(this, 0, this.logStrings) );				
		logger.addLogRecorder(this);
		
		PreferencesService preferencesService = ThiefTracker.getPreferencesService();
		
		MotionMonitor motionMonitor = ThiefTracker.getMotionMonitor();
		try {
			Float motionSensitivity = Float.parseFloat(preferencesService.getPreference("motionSensitivity"));
			motionMonitor.setSensitivity(motionSensitivity);
		}
		catch(NumberFormatException e) {
		}
				
		Contacter contacter = ThiefTracker.getContacter();
		contacter.addPhoneNumber( preferencesService.getPreference("phone1") );
		contacter.addPhoneNumber( preferencesService.getPreference("phone2") );
		contacter.addEmailAddress( preferencesService.getPreference("email1") );
		contacter.addEmailAddress( preferencesService.getPreference("email2") );
		logger.log("Phones numbers : ");
		for(String phoneNumber :  contacter.getPhonesNumbers() ) {
			logger.log(phoneNumber);
		}
		logger.log("Emails addresses : ");
		for(String emailAddress : contacter.getEmailsAddresses() ) {
			logger.log(emailAddress);
		}
		contacter.setRealMode(false);
		logger.log( contacter.isForReal() ? "Contacter is for real" : "Contacter NOT for real" );
		
		
		motionMonitor.startMotionDetection();		
		logger.log( "Motion monitor started" );

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
		logger.log( "Heart beat started" );
		
		logger.log( "Config : \n"+(new ConfigRetriever()).getConfigString() );
	}

	public void onStop() {
		Log.d("MonitoringActivity", "onStop");
		Alarm alarm = ThiefTracker.getAlarm();
		if ( alarm.isPlaying() ) {
			Contacter contacter = ThiefTracker.getContacter();
			contacter.send("Application stopped while alarm playing.");
		}
		super.onPause();
		this.finish();
	}

	public void onDestroy() {
		Log.d("MonitoringActivity", "onDestroy");		
		super.onDestroy();
	}

	protected void onResume() {
		Log.d("MonitoringActivity", "onResume");		
		super.onResume();
		ThiefTracker.getLogger().log("Resuming.");
	}
	
	private class CustomAdapter extends ArrayAdapter {
		
		public CustomAdapter(Context context, int textViewResourceId, List<String> strings) {						
			super(context, textViewResourceId, strings);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			Context context = (Context)MonitoringActivity.this;
			String listItemString = this.getItem(position).toString();
			TextView textView;
			if (convertView == null) {
				textView = new TextView(context);
				convertView = textView;
				convertView.setTag(textView);
			} else {
				textView = (TextView)convertView.getTag();
			}
			textView.setText( listItemString );
			return(convertView);
		}
	}
	
	private class MonitoringActivityPrinter implements Runnable {
		
		private String text;
		
		MonitoringActivityPrinter(String text) {
			this.text = text;
		}
		
		public void run() {
			
			Calendar now = Calendar.getInstance();
			String logString = 
					now.get(Calendar.YEAR)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.DAY_OF_MONTH)
					+" "
					+now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+":"+now.get(Calendar.SECOND)
					+" : "
					+this.text;
			MonitoringActivity.this.logStrings.add(logString);
			((ArrayAdapter)MonitoringActivity.this.listView.getAdapter()).notifyDataSetChanged();
			MonitoringActivity.this.listView.scrollTo(0, 0);			
		}
	}
	
	public void record(String text) {
		runOnUiThread( new MonitoringActivityPrinter(text) );		
	}
}
