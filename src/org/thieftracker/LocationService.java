package org.thieftracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

public class LocationService implements LocationListener {

	private static volatile LocationService instance;
	private Context context;
	private LocationManager locationManager;
	private ILocationReceiver quickLocationReceiver;
	private ILocationReceiver accurateLocationReceiver;
	private ILocationReceiver trackingLocationReceiver;
	private double lastKnownLatitude;
	private double lastKnownLongitude;
	private boolean trackingActive;

	private LocationService(Context context) {
		this.context = context;		
		this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		this.quickLocationReceiver = null;
		this.accurateLocationReceiver = null;
		this.trackingLocationReceiver = null;
		this.trackingActive = false;
	}
	
	public static LocationService getInstance(Context context) {
		if ( LocationService.instance == null ) {
			synchronized(LocationService.class) {
				if ( LocationService.instance == null ) { 				
					LocationService.instance = new LocationService(context);
				}
			}
		}
		return( LocationService.instance );
	}
	
	public void getQuickLocation(ILocationReceiver receiver) {
		this.quickLocationReceiver = receiver;
		
		LocationProvider provider;
		boolean r;
		provider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
		r = provider.requiresNetwork();

		provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
		r = provider.requiresNetwork();
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);		
	}
	
	public void getAccurateLocation(ILocationReceiver receiver) {
		this.accurateLocationReceiver = receiver;
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}
	
	public void startLocationTracking(ILocationReceiver receiver) {
		this.trackingLocationReceiver = receiver;
		this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000*10, 0, this);
		this.trackingActive = true;
	}
	
	public boolean isTracking() {
		return( this.trackingActive );
	}
	
	public void stopLocationTracking() {
		if ( this.quickLocationReceiver == null && this.accurateLocationReceiver == null ) {
			this.locationManager.removeUpdates(this);
			this.trackingActive = false;
		}
	}
	
	public boolean isTrackingActive() {
			return( this.trackingActive );
	}
	
	public void onLocationChanged(Location location) {

		double latitude = location.getLatitude();
		double longitude =location.getLongitude();
		
		if ( this.quickLocationReceiver != null ) {
			this.quickLocationReceiver.onReceiveLocation(latitude, longitude);
			this.quickLocationReceiver = null;
			if ( this.accurateLocationReceiver == null && this.trackingLocationReceiver == null ) {
				locationManager.removeUpdates(this);
			}
		}
		if ( this.accurateLocationReceiver != null ) {
			this.accurateLocationReceiver.onReceiveLocation(latitude, longitude);
			this.accurateLocationReceiver = null;
			if ( this.quickLocationReceiver == null && this.trackingLocationReceiver == null ) {
				locationManager.removeUpdates(this);
			}
		}
		this.lastKnownLatitude = latitude;
		this.lastKnownLongitude = longitude;
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onProviderDisabled(String provider) {
	}
	
	
}
/*		
double latitude = Math.round(location.getLatitude() * Math.pow(10, 7))	/ Math.pow(10, 7);
double longitude = Math	.round(location.getLongitude() * Math.pow(10, 7)) / Math.pow(10, 7);
if (latitude != this.lastKnownLatitude || longitude != this.lastKnownLongitude) {
	}
}
*/
/*		
Criteria criteria = new Criteria();
criteria.setSpeedRequired(true);
String provider = locationManager.getBestProvider(criteria, true);
*/		

