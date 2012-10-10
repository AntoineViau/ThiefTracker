package org.thieftracker;

public class Command_realmode extends Command {

	public void execute(String params) {
		
		Contacter contacter = ThiefTracker.getContacter();
		
		if ( params.equals("1") || params.equals("on") ) {
			contacter.setRealMode(true);
			this.answerBack("ForReal set to on");
		}
		if ( params.equals("0") || params.equals("off") ) {
			contacter.setRealMode(true);
			this.answerBack("ForReal set to off");
		}
	}
	
}
