package org.thieftracker;

public class Command_forreal extends Command {

	public void execute(String params) {
		
		if ( params.equals("1") || params.equals("on") ) {
			SmsSender.getInstance(this.getContext()).setForReal(true);
		}
		if ( params.equals("0") || params.equals("off") ) {
			SmsSender.getInstance(this.getContext()).setForReal(true);
		}
	}
	
}
