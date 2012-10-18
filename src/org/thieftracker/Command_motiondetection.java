package org.thieftracker;


public class Command_motiondetection extends Command {
	
	public void execute() {

		MotionMonitor motionMonitor = ThiefTracker.getMotionMonitor();
				
		String subCommand = this.getParameter(0).toLowerCase();
		
		if ( subCommand.equals("start") ) {
			motionMonitor.startMotionDetection();
			this.answerBack("Motion detection started.");
		}
		
		if ( subCommand.equals("Sensitivity") ) {
			float sensitivity = Float.parseFloat(subCommand.substring("motionDetectionSensitivity ".length()));
			if ( sensitivity > 0 ) {
				motionMonitor.setSensitivity(sensitivity);
				this.answerBack("Motion sensitivity set to "+sensitivity);
			}
			else {
				this.answerBack("Invalid sensitivity.");			
			}
		}
		
		if ( subCommand.equals("stableDelayBeforeMonitoring") ) {
			Integer delay = Integer.parseInt(subCommand.substring("motionDetectionDelayBeforeReset ".length()));
			if ( delay > 0 ) {
				motionMonitor.setNbMillisWithoutDetectionToBeStable(delay*1000);
				this.answerBack("Motion delay before reset set to "+delay);
			}
			else {
				this.answerBack("Invalid delay.");			
			}
		}
		
		if ( subCommand.equals("stop") ) {
			motionMonitor.stopMotionDetection();
			this.answerBack("Motion detection stopped.");
		}
		
	}

}
