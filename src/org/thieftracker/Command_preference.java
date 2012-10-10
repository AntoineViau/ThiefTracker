package org.thieftracker;


public class Command_preference extends Command {
	
	public void execute(String params) {

		String[] tokens = params.split(" ");
		String key = tokens[0].toLowerCase();
		String value = tokens[1].toLowerCase();
		
		PreferencesService preferencesService = ThiefTracker.getPreferencesService();		
		
		if ( key.equals("phone1") ) {	
			Contacter contacter = ThiefTracker.getContacter();
			String currentPhoneNumber1 = preferencesService.getPreference("phone1");
			String newPhoneNumber1 = value;
			if ( !newPhoneNumber1.equals(currentPhoneNumber1) ) {
				if ( contacter.addPhoneNumber(value) ) {			
					contacter.removePhonesNumbers(currentPhoneNumber1);
					preferencesService.setPreference("phone1", newPhoneNumber1);
					this.answerBack("Phone1 set to "+newPhoneNumber1);
				}
				else {
					this.answerBack("Unable to set phone1 to "+newPhoneNumber1);
				}
			} 
			else {
				this.answerBack("Phone1 is already set to "+newPhoneNumber1);
			}
		}		
	}
}
