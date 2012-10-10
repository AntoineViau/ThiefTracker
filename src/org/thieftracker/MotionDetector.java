package org.thieftracker;

import java.util.Date;
import java.util.Observable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * MotionDetector is just here to detect <i>one</i> movement.
 * When a motion is detected, MotionDetector waits for the motion to stop
 * and then waits some time to listen again for movement.
 * 
 * @author Antoine Viau
 */
public class MotionDetector extends Observable implements SensorEventListener {

	private Context context;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private float previousAcceleration[];
	private Date lastMotionDetectedDate;
	private boolean detectionActive;
	private float sensitivity;
	private Integer stableDelayBeforeDetection;
	private long counter;

	public MotionDetector(Context context) {
		this.context = context;
		this.mSensorManager = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);		
		this.mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		this.lastMotionDetectedDate = null;
		this.detectionActive = false;
		this.sensitivity = 3.0f; 
		this.stableDelayBeforeDetection = 1000*60;
		this.counter = 0;		
	}
	
	public void startMotionDetection() {
		this.previousAcceleration = new float[3];
		this.previousAcceleration[0] = 0.0f;
		this.previousAcceleration[1] = 0.0f;
		this.previousAcceleration[2] = 0.0f;
		this.lastMotionDetectedDate = null;
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		this.detectionActive = true;
	}
	
	public void stopMotionDetection() {
		this.mSensorManager.unregisterListener(this);
		this.detectionActive = false;
	}

	public void setSensitivity(float sensitivity) {
		this.sensitivity = sensitivity;
	}
	
	public float getSensitivity() {
		return( this.sensitivity );
	}
	
	public void setStableDelayBeforeDetection(Integer delay) {
		this.stableDelayBeforeDetection = delay;
	}	
	public Integer getStableDelayBeforeDetection() {
		return( this.stableDelayBeforeDetection );
	}
	
	public boolean isDetectionActive() {
		return( this.detectionActive );
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		return;
	}
	
	public void onSensorChanged(SensorEvent event) {
			
		final float alpha = 0.8f;		
		float[] gravity = new float[3];
		float[] linear_acceleration = new float[3];
		gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
		gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
		gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
		linear_acceleration[0] = event.values[0] - gravity[0];
		linear_acceleration[1] = event.values[1] - gravity[1];
		linear_acceleration[2] = event.values[2] - gravity[2];
	
		if ((this.previousAcceleration[0] + this.previousAcceleration[1] + this.previousAcceleration[2]) != 0.0f) {
		
			for (int i = 0; i < 3; i++) {
			
				if (Math.abs(this.previousAcceleration[i]- linear_acceleration[i]) > this.sensitivity) {
						
					long nbMillisElapsedSinceLastMotionDetected = this.lastMotionDetectedDate != null ? (new Date()).getTime() - this.lastMotionDetectedDate.getTime() : -1;
					
					if ( this.lastMotionDetectedDate == null || nbMillisElapsedSinceLastMotionDetected > this.stableDelayBeforeDetection ) {					
						Log.d("MotionDetector", "Motion detected");	
						this.setChanged();
						this.notifyObservers();
					}
					else {						
						Log.d("MotionDetector", (this.counter++) + " : Motion detected but discarded because only "+nbMillisElapsedSinceLastMotionDetected/1000+" seconds since last detection");
					}
					this.lastMotionDetectedDate = new Date();
					break;
				}
			}
		}		
		this.previousAcceleration[0] = linear_acceleration[0];
		this.previousAcceleration[1] = linear_acceleration[1];
		this.previousAcceleration[2] = linear_acceleration[2];
	}	
}
