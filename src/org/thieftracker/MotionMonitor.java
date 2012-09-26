package org.thieftracker;

import android.content.Context;

public class MotionMonitor implements IMotionReactor {
	
	private static volatile MotionMonitor instance;
	private Context context;

	private MotionMonitor(Context context) {
		this.context = context;
	}
	
	public static MotionMonitor getInstance(Context context) {
		if ( MotionMonitor.instance == null ) {
			synchronized(MotionMonitor.class) {
				if ( MotionMonitor.instance == null ) { 				
					MotionMonitor.instance = new MotionMonitor(context);
				}
			}
		}
		return( MotionMonitor.instance );
	}
	
	public void onMotionDetected() {
		Logger.getInstance().log("Motion detected.");
		Contacter.getInstance(context).send("Motion detected !");
	}

}
