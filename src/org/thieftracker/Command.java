package org.thieftracker;

import android.content.Context;

public abstract class Command implements ICommand {

	private Context context;
	private String originatingAddress;
	
	public Command() {
		this.context = null;
		this.originatingAddress = null;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public Context getContext() {
		return( this.context );
	}
	
	public void setOriginatingAddress(String address) {
		this.originatingAddress = address;
	}
	
	public String getOriginatingAddress() {
		return( this.originatingAddress );
	}
	
	public void answerBack(String answerText) {		
		if ( this.originatingAddress != null && !this.originatingAddress.equals("") ) {
			Contacter contacter = ThiefTracker.getContacter();
			contacter.sendSms(answerText, this.originatingAddress);
		}
	}
	
}
