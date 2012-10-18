package org.thieftracker;

import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;

public class LocationService implements LocationListener, GpsStatus.Listener {

	private LocationManager locationManager;
	private ILocationReceiver quickLocationReceiver;
	private ILocationReceiver accurateLocationReceiver;

	public LocationService(Context context) {	
		this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		this.quickLocationReceiver = null;
		this.accurateLocationReceiver = null;
	}
		
	public void getQuickLocation(ILocationReceiver receiver) {
		this.quickLocationReceiver = receiver;
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);		
	}
	
	public void getAccurateLocation(ILocationReceiver receiver) {
		this.accurateLocationReceiver = receiver;
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}
	
	public boolean quickLocationPending() {
		return( this.quickLocationReceiver != null );
	}
	
	public boolean accurateLocationPending() {
		return( this.accurateLocationReceiver != null );
	}

	private long mLastLocationMillis;
	private Location mLastLocation;
	private boolean isGpsFix = false;
	
	public void onGpsStatusChanged(int event) {
		switch (event) {
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				if (mLastLocation != null) {
					isGpsFix = (SystemClock.elapsedRealtime() - mLastLocationMillis) < 3000;				
					if (isGpsFix) { // A fix has been acquired.
					} 
					else { // The fix has been lost.
					}
				}
				break;
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				isGpsFix = true;
				break;
		}
    }

	public boolean getGpsFix() {
		return( this.isGpsFix );
	}
	
	public void onLocationChanged(Location location) {
		 
		if (location != null) {
		    mLastLocationMillis = SystemClock.elapsedRealtime();
		    mLastLocation = location;
		}
		
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		if ( this.quickLocationReceiver != null ) {
			this.quickLocationReceiver.onReceiveLocation(latitude, longitude);
			this.quickLocationReceiver = null;
			if ( this.accurateLocationReceiver == null ) {
				locationManager.removeUpdates(this);
			}
		}
		if ( this.accurateLocationReceiver != null ) {
			this.accurateLocationReceiver.onReceiveLocation(latitude, longitude);
			this.accurateLocationReceiver = null;
			if ( this.quickLocationReceiver == null ) {
				locationManager.removeUpdates(this);
			}
		}
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onProviderDisabled(String provider) {
	}	
}
