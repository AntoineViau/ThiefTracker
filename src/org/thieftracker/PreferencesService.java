package org.thieftracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesService {

	Context context;
	
	public PreferencesService(Context context) {
		
		this.context  = context;
	}
	
	public void setPreference(String key, String value) {
		SharedPreferences preferences = this.context.getSharedPreferences("ThiefTrackerPrefsFile", 0);		
		Editor editor = preferences.edit();		
		editor.putString(key, value);
		editor.commit();
	}
	
	public String getPreference(String key) {
		
		SharedPreferences preferences = this.context.getSharedPreferences("ThiefTrackerPrefsFile", 0);
		
		if ( !preferences.contains(key) ) {
			key = key.toLowerCase();			
		}
		
		return( preferences.getString(key, "") );
	}
	
}
