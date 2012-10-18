package org.thieftracker;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationTracker implements LocationListener {

	private LocationManager locationManager;
	private String deviceId;
	private String url;
	private boolean tracking;
	
	public LocationTracker(Context context) {
		this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		this.url = null;
		this.tracking = false;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return( this.url );
	}
	
	public void start() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		this.tracking = true;
	}
	
	public boolean isTracking() {
		return( this.tracking );
	}
	
	private void sendLocationToUrl(double latitude, double longitude) {
		Logger logger = ThiefTracker.getLogger();
		logger.log("Send location tracking...");
		if ( this.url != null ) {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(this.url+"?deviceId="+this.deviceId+"&latitude="+latitude+"&longitude="+longitude+"&timestamp="+System.currentTimeMillis());			
			request.setHeader("User-Agent", "ThiefTracker");			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				String responseBody = client.execute(request, responseHandler);
				logger.log("Tracking location sent to "+this.url+", response : "+responseBody);
			}
			catch (IOException e) {	
				logger.log("Error while sending tracking location to "+this.url);
			}
		}
		else {
			logger.log("No url set for location tracking.");
		}
	}
	
	public void onLocationChanged(Location location) {
		sendLocationToUrl( location.getLatitude(), location.getLongitude() );
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onProviderDisabled(String provider) {
	}

	public void stop() {
		this.locationManager.removeUpdates(this);
		this.tracking = false;
	}
	
}
