package org.thieftracker;

import android.content.Context;
import android.telephony.SmsManager;

public class SmsSender {
	private static volatile SmsSender instance;
	private Context context;
	private SmsManager smsManager;
	private boolean forReal;
	
	private SmsSender(Context context) {
		this.context = context;
		this.smsManager = SmsManager.getDefault();
		this.forReal = true;
	}
	
	public static SmsSender getInstance(Context context) {
		if ( SmsSender.instance == null ) {
			synchronized(SmsSender.class) {
				if ( SmsSender.instance == null ) { 				
					SmsSender.instance = new SmsSender(context);
				}
			}
		}
		return( SmsSender.instance );
	}
	
	public void setForReal(boolean forReal) {
		this.forReal = forReal;
	}
	
	public boolean getForReal() {
		return( this.forReal );
	}
	
	public void sendSms(String phoneNumber, String smsText) {
		if (!phoneNumber.equals("")) {
			if ( this.forReal ) {
				this.smsManager.sendTextMessage(phoneNumber, null, smsText, null, null);
			}
			Logger.getInstance().log("Send \n\""+smsText+"\" to "+phoneNumber);
		}
	}
	
}
