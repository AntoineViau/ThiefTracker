package org.thieftracker;

import java.util.ArrayList;
import java.util.TreeSet;

import android.telephony.SmsManager;
import android.util.Log;

public class Contacter {
	
	private SmsManager smsManager;
	private TreeSet<String> phonesNumbers;
	private TreeSet<String> emailsAddresses;
	private boolean enableSmsFlag;
	private boolean enableEmailsFlag;
	private boolean realMode;
	
	public Contacter() {
		this.smsManager = SmsManager.getDefault();
		this.phonesNumbers = new TreeSet<String>();
		this.emailsAddresses = new TreeSet<String>();
		this.enableSmsFlag = true;
		this.enableEmailsFlag = true;
	}
		
	public void setRealMode(boolean forReal) {
		Log.d("Contacter", "Set Real Mode to "+(forReal ? "on" : "off"));		
		this.realMode = forReal;
	}
	
	public boolean getRealMode() {
		return( this.realMode );
	}
	public boolean isForReal() {
		return( this.realMode );
	}
	
	public boolean addPhoneNumber(String phoneNumber) {
		if ( phoneNumber.matches("^\\+?\\s*[\\d\\s]+$") ) {
			this.phonesNumbers.add(phoneNumber);
			return(true);
		}
		return(false);
	}
	
	public void removePhonesNumbers(String phoneNumber) {
		this.phonesNumbers.remove(phoneNumber);
	}
	
	public void clearPhonesNumbers() {
		this.phonesNumbers.clear();
	}
	
	public boolean addEmailAddress(String emailAddress) {
		if ( emailAddress.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") ) {
			this.emailsAddresses.add(emailAddress);
			return(true);
		}
		return(false);
	}
	public void removeEmailAddress(String emailAddress) {
		this.emailsAddresses.remove(emailAddress);
	}
	
	public String[] getPhonesNumbers() {		
		return( (String[])this.phonesNumbers.toArray(new String[this.phonesNumbers.size()]) );
	}
	
	public String[] getEmailsAddresses() {
		return( (String[])this.emailsAddresses.toArray(new String[this.emailsAddresses.size()]) );
	}
	
	public void enableSms() { this.enableSmsFlag = true; }
	public void disableSms() { this.enableSmsFlag = false; }
	public void enableEmails() { this.enableEmailsFlag = true; }
	public void disableEmails() { this.enableEmailsFlag = false; }
	
	public void send(String message) {
		
		Log.d("Contacter", "send "+message);
		
		if ( this.enableSmsFlag && this.phonesNumbers.size() > 0 ) {		
			for(String phoneNumber : this.phonesNumbers) {
				this.sendSms(message, phoneNumber);
			}
		}
		if ( this.enableEmailsFlag && this.emailsAddresses.size() > 0 ) {
			for(String emailAddress : this.emailsAddresses ) {
				this.sendEmail(message, emailAddress);
			}
		}
	}
	
	public void sendSms(String message, String phoneNumber) {

		Log.d("Contacter", "sendSms to "+phoneNumber+" : "+message);

		if ( this.realMode == true ) {
			ArrayList<String> smsTexts = this.smsManager.divideMessage(message);
			this.smsManager.sendMultipartTextMessage(phoneNumber, null, smsTexts, null, null);
		}
	}
	
	public void sendEmail(String message, String emailAddress) {
	
		Log.d("Contacter", "sendEmail to "+emailAddress+" : "+message);
		
		PreferencesService preferencesService = ThiefTracker.getPreferencesService();
		String account = preferencesService.getPreference("mailAccount");
		String password = preferencesService.getPreference("mailPassword");
		if ( !account.equals("") && !password.equals("") )
		{
			Mail mail = new Mail();
			mail.setSubject("A message from ThiefTracker on device "+ThiefTracker.getDeviceId());
			mail.setBody(message);
			mail.addRecipient(emailAddress);
			try {
				mail.send();
			}
			catch(Exception e) {
			}
		}
	}	
}
