package org.thieftracker;

import android.content.Context;

public class Command_setting extends Command {
	
	public void execute(String params) {
		
		Context context = this.getContext();
		
		Contacter contacter = ThiefTracker.getContacter();
		
		String[] tokens = params.split(" ");
		String subCommand = tokens[0].toLowerCase();

		if ( subCommand.equals("phone1 ".toLowerCase()) ) {
			String newPhone1 = tokens[1];
			context
				.getSharedPreferences("ThiefTrackerPrefsFile", 0)
					.edit()
						.putString("phone1", newPhone1)
							.commit();
			contacter.send("New phone 1 set to "+newPhone1);			
		}
		
		if ( subCommand.equals("phone2 ".toLowerCase()) ) {
			String newPhone2 = tokens[1];
			context
				.getSharedPreferences("ThiefTrackerPrefsFile", 0)
					.edit()
						.putString("phone2", newPhone2)
							.commit();
			contacter.send("New phone 2 set to "+newPhone2);			
		}				
	}

}
