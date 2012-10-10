package org.thieftracker;

import java.util.HashMap;

import org.thieftracker.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Alarm {

	public static final Integer SOUND_ALARM = 5;

	private Context context;
	private AudioManager audioManager;
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolResourcesMap;
	private HashMap<Integer, Integer> soundStreamMap;
	private boolean alarmPlaying;

	public Alarm(Context context) {
		this.context = context;
		this.audioManager = (AudioManager)this.context.getSystemService(Context.AUDIO_SERVICE);
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundPoolResourcesMap = new HashMap<Integer, Integer>();
		soundPoolResourcesMap.put(SOUND_ALARM, soundPool.load(this.context, R.raw.alarm, 1));
		soundStreamMap = new HashMap<Integer, Integer>();
	}
	
	private boolean playSound(Integer soundId, boolean loop) {
		Integer streamId = this.soundPool.play(soundId, 1, 1, 1, loop ? -1 : 0, 1);
		soundStreamMap.put(soundId,  streamId);
		return( streamId != 0 ? true : false );
	}
	
	private void stopSound(Integer soundId) {
		Integer streamId = soundStreamMap.get(soundId);
		if ( streamId != 0 )
		{
			this.soundPool.stop(streamId);
		}
	}
	
	public void start() {
		this.audioManager.setStreamVolume (AudioManager.STREAM_MUSIC, this.audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
		this.playSound(Alarm.SOUND_ALARM, true);
		this.alarmPlaying = true;
	}
	
	public boolean isPlaying() {
		return( this.alarmPlaying );
	}

	public void stop() {
		this.stopSound(Alarm.SOUND_ALARM);
		this.alarmPlaying = true;
	}
}
