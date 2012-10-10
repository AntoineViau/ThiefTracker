package org.thieftracker;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class HeartBeat extends TimerTask {

	private String deviceId;
	private Timer heartBeatTimer;
	private String url;
	private Integer period;
	
	public HeartBeat() {
		this.heartBeatTimer = null;
		this.deviceId = null;
		this.url = null;
		this.period = 1000 * 60 * 60;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public void setPeriod(Integer period) {
		this.period = period;
	}	
	public Integer getPeriod() {
		return( this.period );
	}

	public void setUrl(String url) {
		this.url = url;
	}	
	public String getUrl() {
		return( this.url );
	}

	public void start() {
		if ( this.heartBeatTimer == null && this.url != null ) {
			this.heartBeatTimer = new Timer("HeartBeatTimer", true);
			heartBeatTimer.schedule(this, 0, this.period);
		}
	}
		
	public void stop() {		
		this.heartBeatTimer.cancel();
		this.heartBeatTimer = null;
	}
	
	public boolean isBeating() {
		return( this.heartBeatTimer != null );
	}
	
	public void run() {
		try {		
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(this.url+"?deviceId="+this.deviceId);			
			request.setHeader("User-Agent", "ThiefTracker");			
			//List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			//nameValuePairs.add(new BasicNameValuePair("deviceId", this.deviceId));
			//request.setEntity(new UrlEncodedFormEntity(nameValuePairs));						
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
		    String responseBody = client.execute(request, responseHandler);			
		    ThiefTracker.getLogger().log("HeartBeat ok : "+responseBody);
		} 
		catch (IOException e) {		
			ThiefTracker.getLogger().log("HeartBeat error : "+e.getMessage());
		}	
	}
}
		
