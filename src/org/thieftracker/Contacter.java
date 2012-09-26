package org.thieftracker;

import java.util.TreeSet;

import android.content.Context;
import android.content.SharedPreferences;

public class Contacter {
	
	private static volatile Contacter instance;
	private Context context;
	private TreeSet<String> phonesNumbers;
	private TreeSet<String> emailsAddresses;
	private boolean enableSmsFlag;
	private boolean enableEmailsFlag;
	
	private Contacter(Context context) {
		this.context = context;
		this.phonesNumbers = new TreeSet<String>();
		this.emailsAddresses = new TreeSet<String>();
		this.enableSmsFlag = true;
		this.enableEmailsFlag = true;
	}
		
	public static Contacter getInstance(Context context) {
		if ( Contacter.instance == null ) {
			synchronized(Contacter.class) {
				if ( Contacter.instance == null ) { 				
					Contacter.instance = new Contacter(context);
				}
			}
		}
		return( Contacter.instance );
	}	
	
	public void addPhoneNumber(String phoneNumber) {
		this.phonesNumbers.add(phoneNumber);
	}
	
	public void addEmailAddress(String emailAddress) {
		this.emailsAddresses.add(emailAddress);
	}
	
	public void getFromPreferences() {

		SharedPreferences settings = this.context.getSharedPreferences("ThiefTrackerPrefsFile", 0);
		
		String phone1 = settings.getString("phone1", "");
		if ( !phone1.equals("") ) {
			this.addPhoneNumber(phone1);
		}
		
		String phone2 = settings.getString("phone2", "");
		if ( !phone2.equals("") ) {
			this.addPhoneNumber(phone2);
		}				
	}
	
	public void enableSms() { this.enableSmsFlag = true; }
	public void disableSms() { this.enableSmsFlag = false; }
	public void enableEmails() { this.enableEmailsFlag = true; }
	public void disableEmails() { this.enableEmailsFlag = false; }
	
	public void send(String message) {
	
		getFromPreferences();
		
		for(String phoneNumber : this.phonesNumbers) {
			SmsSender.getInstance(context).sendSms(phoneNumber, message);
		}
	}
	
	public void send(String message, String phoneNumber) {		
		SmsSender.getInstance(context).sendSms(phoneNumber, message);
	}
}
