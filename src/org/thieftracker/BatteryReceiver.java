package org.thieftracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BatteryReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Contacter contacter = ThiefTracker.getContacter();
		contacter.send("Battery low !");		
	}
}
