package org.thieftracker;


/**
 * Command_config will send back the app config to the caller.
 * @author Antoine
 *
 */
public class Command_config extends Command {
	
	public void execute() {		
		Contacter contacter = ThiefTracker.getContacter();		
		contacter.sendSms((new ConfigRetriever()).getConfigString(), this.getOriginatingAddress());
	}

}
