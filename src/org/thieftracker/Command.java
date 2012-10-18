package org.thieftracker;

import android.content.Context;

public abstract class Command {

	private Context context;
	private String originatingAddress;
	private String parametersString;
	private String[] parameters;
	
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

	public void setParametersString(String parametersString) {
		this.parametersString = parametersString;
		this.parameters = this.parametersString.split(" ");			
	}
	
	public Integer getNbParameters() {
		return( this.parameters.length );
	}
	
	public String getParameter(Integer index) {
		return( parameters[index] );
	}
	
	public void answerBack(String answerText) {		
		if ( this.originatingAddress != null && !this.originatingAddress.equals("") ) {
			Contacter contacter = ThiefTracker.getContacter();
			contacter.sendSms(answerText, this.originatingAddress);
		}
	}
	
	public abstract void execute();
}	
