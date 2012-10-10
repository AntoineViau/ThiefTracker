package org.thieftracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
		HttpClient client = new DefaultHttpClient();		
		HttpPost request = new HttpPost("http://www.antoineviau.com/ThiefTracker/contacterEmail.php");		
		request.setHeader("User-Agent", "ThiefTracker");
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("message", message));
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			client.execute(request);
		}
		catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	}	
}
