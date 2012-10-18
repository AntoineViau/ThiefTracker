package org.thieftracker;

public class Command_realmode extends Command {

	public void execute() {
				
		Contacter contacter = ThiefTracker.getContacter();
		PreferencesService preferencesService = ThiefTracker.getPreferencesService();
		
		String parameter = this.getParameter(0).toLowerCase();
		
		if ( parameter.equals("1") || parameter.equals("on") ) {
			contacter.setRealMode(true);
			preferencesService.setPreference("realMode","on");
			this.answerBack("ForReal set to on");
		}
		if ( parameter.equals("0") || parameter.equals("off") ) {
			contacter.setRealMode(true);
			preferencesService.setPreference("realMode","off");
			this.answerBack("ForReal set to off");
		}
	}
	
}
