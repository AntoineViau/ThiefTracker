
package org.thieftracker;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;

/**
 * This the application level class for motion detection.
 * Since we do not want MotionDetector to be polluted by
 * any application objects/methods, this class will do
 * the job.
 * @author Antoine
 *
 */
public class MotionMonitor extends MotionDetector implements Observer {
	
	public MotionMonitor(Context context) {
		super(context);
		this.addObserver(this); 
	}
			
	@Override
	public void update(Observable motionDetector, Object param) {
		
		Logger logger = ThiefTracker.getLogger();
		logger.log("Motion detected, send message.");

		Contacter contacter = ThiefTracker.getContacter();
		contacter.send("Motion detected on "+ThiefTracker.getDeviceId());
		
	}	
	
}
