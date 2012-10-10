package org.thieftracker;


public class Command_motiondetection extends Command {
	
	public void execute(String params) {

		MotionMonitor motionMonitor = ThiefTracker.getMotionMonitor();
				
		String[] tokens = params.split(" ");
		String subCommand = tokens[0].toLowerCase();
		
		if ( subCommand.equals("start".toLowerCase()) ) {
			motionMonitor.startMotionDetection();
			this.answerBack("Motion detection started.");
		}
		
		if ( subCommand.equals("Sensitivity".toLowerCase()) ) {
			float sensitivity = Float.parseFloat(subCommand.substring("motionDetectionSensitivity ".length()));
			if ( sensitivity > 0 ) {
				motionMonitor.setSensitivity(sensitivity);
				this.answerBack("Motion sensitivity set to "+sensitivity);
			}
			else {
				this.answerBack("Invalid sensitivity.");			
			}
		}
		
		if ( subCommand.equals("stableDelayBeforeMonitoring".toLowerCase()) ) {
			Integer delay = Integer.parseInt(subCommand.substring("motionDetectionDelayBeforeReset ".length()));
			if ( delay > 0 ) {
				motionMonitor.setStableDelayBeforeDetection(delay*1000);
				this.answerBack("Motion delay before reset set to "+delay);
			}
			else {
				this.answerBack("Invalid delay.");			
			}
		}
		
		if ( subCommand.equals("stop".toLowerCase()) ) {
			motionMonitor.stopMotionDetection();
			this.answerBack("Motion detection stopped.");
		}
		
	}

}
