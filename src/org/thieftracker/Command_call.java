package org.thieftracker;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

public class Command_call extends Command {
	
	public void execute() {
		try {
			if ( this.getNbParameters() > 0 ) {
				String phoneNumber = this.getParameter(0);			
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				callIntent.setData(Uri.parse("tel:"+phoneNumber));
				this.getContext().startActivity(callIntent);
				this.answerBack("Calling "+phoneNumber);
			}
		} 
		catch (ActivityNotFoundException e) {
			this.answerBack("Call failed : "+e.getMessage());
		}	
	}

}
