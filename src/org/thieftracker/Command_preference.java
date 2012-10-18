package org.thieftracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Command_preference extends Command {
	
	public void execute() {

		String key = this.getParameter(0).toLowerCase();
		String value = this.getParameter(1).toLowerCase();
		
		PreferencesService preferencesService = ThiefTracker.getPreferencesService();
		
		Pattern p = Pattern.compile("phone(1|2)");
		Matcher m = p.matcher(key);		
		if ( m.matches() ) {	
			String phoneIndex = m.group(1);		
			Contacter contacter = ThiefTracker.getContacter();
			String currentPhoneNumber = preferencesService.getPreference("phone"+phoneIndex);
			String newPhoneNumber = value;
			if ( !newPhoneNumber.equals(currentPhoneNumber) ) {
				if ( contacter.addPhoneNumber(value) ) {			
					contacter.removePhonesNumbers(currentPhoneNumber);
					preferencesService.setPreference("phone"+phoneIndex, newPhoneNumber);
					this.answerBack("Phone"+phoneIndex+" set to "+newPhoneNumber);
				}
				else {
					this.answerBack("Unable to set phone"+phoneIndex+" to "+newPhoneNumber);
				}
			} 
			else {
				this.answerBack("Phone"+phoneIndex+" is already set to "+newPhoneNumber);
			}
			return;
		}
	

		p = Pattern.compile("email(1|2)");
		m = p.matcher(key);		
		if ( m.matches() ) {	
			String emailIndex = m.group(1);		
			Contacter contacter = ThiefTracker.getContacter();
			String currentEmailAddress = preferencesService.getPreference("email"+emailIndex);
			String newEmailAddress = value;
			if ( !newEmailAddress.equals(currentEmailAddress) ) {
				if ( contacter.addEmailAddress(value) ) {			
					contacter.removeEmailAddress(currentEmailAddress);
					preferencesService.setPreference("email"+emailIndex, newEmailAddress);
					this.answerBack("email"+emailIndex+" set to "+newEmailAddress);
				}
				else {
					this.answerBack("Unable to set email"+emailIndex+" to "+newEmailAddress);
				}
			} 
			else {
				this.answerBack("email"+emailIndex+" is already set to "+newEmailAddress);
			}
			return;
		}
		
		if ( key.equals("trackingurl") ) {
			preferencesService.setPreference("trackingUrl", value);
			return;		
		}
		
	}
}
