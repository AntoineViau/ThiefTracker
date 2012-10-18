package org.thieftracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver  extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.d("SmsReceiver", "onReceive");
		
		Logger logger = ThiefTracker.getLogger();
		
		logger.log("Sms receveived :");
				
		Bundle bundle = intent.getExtras();

		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];
		for (int n = 0; n < messages.length; n++) {
			smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
		}		
		String smsText = smsMessage[0].getMessageBody();
		logger.log(smsText);
		String[] tokens = smsText.split(" ");
		String commandName = tokens[0].toLowerCase();
		String commandClassName = "Command_"+commandName;		
		try {
			Class<?> commandClass = Class.forName("org.thieftracker."+commandClassName);
			Command command;
			try {
				logger.log("Command found for this SMS. Execute !");
				command = (Command)commandClass.newInstance();
				command.setParametersString(tokens.length > 1 ? smsText.substring(commandName.length()+1) : "" );
				command.setContext(context);
				command.setOriginatingAddress(smsMessage[0].getOriginatingAddress());
				command.execute();
			} 
			catch (IllegalAccessException e) {
			} 
			catch (InstantiationException e) {
			}			
		}
		catch(ClassNotFoundException e) {
			logger.log("No command found for this SMS");
		}
	}	
}
