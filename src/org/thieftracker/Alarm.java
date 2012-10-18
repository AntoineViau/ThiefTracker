package org.thieftracker;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class Alarm {

	public static final Integer SOUND_ALARM = 1;

	private Context context;
	private AudioManager audioManager;
	private SoundPool soundPool;
	private Integer resourceId;
	private Integer streamId;

	public Alarm(Context context) {
		this.context = context;
		this.audioManager = (AudioManager)this.context.getSystemService(Context.AUDIO_SERVICE);
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
		this.resourceId = soundPool.load(this.context, R.raw.alarm, 1);
		this.streamId = 0;
	}

	public void start() {
		Log.d("Alarm","start");
		this.audioManager.setStreamVolume (AudioManager.STREAM_MUSIC, this.audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
		this.streamId = this.soundPool.play(this.resourceId, 1, 1, 1, -1, 1);
	}
	
	public void stop() {
		Log.d("Alarm","stop");
		this.soundPool.stop(this.streamId);
		this.streamId = 0;
	}

	public boolean isPlaying() {
		return( this.streamId != 0 );
	}


	
}
