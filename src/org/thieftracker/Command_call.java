package org.thieftracker;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

public class Command_call extends Command {
	
	public void execute(String params) {
		try {
			String phoneNumber = params.equals("") ? this.getOriginatingAddress() : params;
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			callIntent.setData(Uri.parse("tel:"+phoneNumber));
			this.getContext().startActivity(callIntent);
			this.answerBack("Calling "+phoneNumber);
		} 
		catch (ActivityNotFoundException e) {
			this.answerBack("Call failed : "+e.getMessage());
		}	
	}

}
