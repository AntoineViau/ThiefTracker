package org.thieftracker;

import android.content.Context;

public class Command_motiondetection extends Command {
	
	public void execute(String params) {

		Context context = this.getContext();
		
		String[] tokens = params.split(" ");
		String subCommand = tokens[0].toLowerCase();
		
		if ( subCommand.equals("start".toLowerCase()) ) {			
			MotionDetector.getInstance(context).addReactor(MotionMonitor.getInstance(context));
			MotionDetector.getInstance(context).startMotionDetection();
			this.answerBack("Motion detection started.");
		}
		
		if ( subCommand.equals("Sensitivity".toLowerCase()) ) {
			float sensitivity = Float.parseFloat(subCommand.substring("motionDetectionSensitivity ".length()));
			if ( sensitivity > 0 ) {
				MotionDetector.getInstance(context).setSensitivity(sensitivity);
				this.answerBack("Motion sensitivity set to "+sensitivity);
			}
			else {
				this.answerBack("Invalid sensitivity.");			
			}
		}
		
		if ( subCommand.equals("DelayBeforeReset".toLowerCase()) ) {
			Integer delay = Integer.parseInt(subCommand.substring("motionDetectionDelayBeforeReset ".length()));
			if ( delay > 0 ) {
				MotionDetector.getInstance(context).setDelayBeforeReset(delay*1000);
				this.answerBack("Motion delay before reset set to "+delay);
			}
			else {
				this.answerBack("Invalid delay.");			
			}
		}
		
		if ( subCommand.equals("stop".toLowerCase()) ) {
			MotionDetector.getInstance(context).stopMotionDetection();
			this.answerBack("Motion detection stopped.");
		}
		
	}

}
