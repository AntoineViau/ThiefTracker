package org.thieftracker;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MotionDetector implements SensorEventListener {

	private static volatile MotionDetector instance;
	private Context context;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ArrayList<IMotionReactor> reactors;
	private float previousAcceleration[];
	private Date motionDetectedDate;
	private boolean detectionActive;
	private float sensitivity;
	private Integer delayBeforeReset;

	private MotionDetector(Context context) {
		this.context = context;
		this.reactors = new ArrayList<IMotionReactor>();
		this.mSensorManager = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);		
		this.mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		this.motionDetectedDate = null;
		this.detectionActive = false;
		this.sensitivity = 3.0f; 
		this.delayBeforeReset = 1000*60;
	}
	
	public static MotionDetector getInstance(Context context) {
		if ( MotionDetector.instance == null ) {
			synchronized(MotionDetector.class) {
				if ( MotionDetector.instance == null ) { 				
					MotionDetector.instance = new MotionDetector(context);
				}
			}
		}
		return( MotionDetector.instance );
	}

	public void addReactor(IMotionReactor reactor)	{
		this.reactors.add(reactor);
	}
	
	public void removeReactor(IMotionReactor reactor) {
		this.reactors.remove(reactor);
	}
	
	public void startMotionDetection() {
		this.previousAcceleration = new float[3];
		this.previousAcceleration[0] = 0.0f;
		this.previousAcceleration[1] = 0.0f;
		this.previousAcceleration[2] = 0.0f;
		this.motionDetectedDate = null;
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		this.detectionActive = true;
		Logger.getInstance().log("Motion detection started.");
	}
	
	public void stopMotionDetection() {
		this.mSensorManager.unregisterListener(this);
		this.detectionActive = false;
		Logger.getInstance().log("Motion detection stopped.");
	}

	public void setSensitivity(float sensitivity) {
		this.sensitivity = sensitivity;
	}
	
	public float getSensitivity() {
		return( this.sensitivity );
	}
	
	public void setDelayBeforeReset(Integer delay) {
		this.delayBeforeReset = delay;
	}	
	public Integer getDelayBeforeReset() {
		return( this.delayBeforeReset );
	}
	
	public boolean isDetectionActive() {
		return( this.detectionActive );
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		return;
	}
	/*
	 * Donc...
	 * Un mouvement est détecté à partir d'une certaine sensibilité.
	 * Quand il y a détection on regarde de quand date la dernière détection.
	 * Si trop récente, on ignore, et on reset le compteur de temps.
	 * Si suffisamment ancienne, on appelle les reactors.
	 */
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
						
					Logger.getInstance().log("Motion detected.");

					Date now = new Date();
					
					if ( this.motionDetectedDate == null || (now.getTime() - this.motionDetectedDate.getTime()) > this.delayBeforeReset ) {
					
						Logger.getInstance().log("First detection, or enough time since last detection. Calling reactors.");						
						for(IMotionReactor reactor: this.reactors) {
							reactor.onMotionDetected();
						}											
						
					}
					else {						
						//Logger.getInstance().log("Discarded.");
					}
					this.motionDetectedDate = new Date();
					break;
				}
			}
		}
		
		this.previousAcceleration[0] = linear_acceleration[0];
		this.previousAcceleration[1] = linear_acceleration[1];
		this.previousAcceleration[2] = linear_acceleration[2];
	}	
}
