package org.thieftracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver  extends BroadcastReceiver {

	private Context context;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		this.context = context;
		
		Bundle bundle = intent.getExtras();

		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];
		for (int n = 0; n < messages.length; n++) {
			smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
		}		
		String smsText = smsMessage[0].getMessageBody().toLowerCase();
		String[] tokens = smsText.split(" ");
		String commandName = tokens[0];
		String commandClassName = "Command_"+commandName;		
		try {
			Class<?> commandClass = Class.forName("org.thieftracker."+commandClassName);
			Command command;
			try {
				command = (Command)commandClass.newInstance();
				command.setContext(context);
				command.setOriginatingAddress(smsMessage[0].getOriginatingAddress());
				command.execute( tokens.length > 1 ? smsText.substring(commandName.length()+1) : "" );
			} 
			catch (IllegalAccessException e) {
			} 
			catch (InstantiationException e) {
			}			
		}
		catch(ClassNotFoundException e) {
			//SmsSender.getInstance(context).sendSms(smsMessage[0].getOriginatingAddress(), commandName+" unknown.");
		}
	}	
}
