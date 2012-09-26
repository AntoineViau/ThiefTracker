package org.thieftracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.thieftracker.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MonitoringActivity extends Activity implements ILogRecorder {
		
	private ListView listView;
	private ArrayList<String> logStrings;
	private Integer logCounter;	

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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
				
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_monitoring);
		
		this.logStrings = new ArrayList<String>();
		this.listView = (ListView) findViewById(R.id.logging);
		this.listView.setAdapter( new CustomAdapter(this, 0, this.logStrings) );
		
		Logger.instantiate();
		Logger.getInstance().addLogRecorder(this);
		this.logCounter = 0;
		
		Logger.getInstance().log("Creating.");		
		
		MotionDetector.getInstance(this).addReactor(MotionMonitor.getInstance(this));
		MotionDetector.getInstance(this).startMotionDetection();

		TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		HeartBeat.getInstance().setDeviceId( telephonyManager.getDeviceId() );
		HeartBeat.getInstance().start();
/*		
		LocationService ls = LocationService.getInstance(this);		
		ls.getQuickLocation( 
			new ILocationReceiver() {				
				public void onReceiveLocation(double latitude, double longitude) {					
					latitude = latitude;
					longitude = longitude;
				}				
			}
		);
*/		
	}
		
	public void onStop() {
		super.onPause();
		this.finish();
	}

	public void onDestroy() {
		super.onDestroy();
	}

	protected void onResume() {
		super.onResume();
		Logger.getInstance().log("Resuming.");
	}
	
	private class MonitoringActivityPrinter implements Runnable {
		
		private String text;
		
		MonitoringActivityPrinter(String text) {
			this.text = text;
		}
		
		public void run() {
			
			//String logString = ++MonitoringActivity.this.logCounter+" : "+this.text;
			Calendar now = Calendar.getInstance();
			String logString = 
					now.get(Calendar.YEAR)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.DAY_OF_MONTH)
					+" "
					+now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+":"+now.get(Calendar.SECOND)
					+" : "
					+this.text;
			MonitoringActivity.this.logStrings.add(0, logString);
			((ArrayAdapter)MonitoringActivity.this.listView.getAdapter()).notifyDataSetChanged();
			MonitoringActivity.this.listView.scrollTo(0, 0);			
		}
	}
	
	public void record(String text) {
		runOnUiThread( new MonitoringActivityPrinter(text) );		
	}
}
