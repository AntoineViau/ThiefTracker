package org.thieftracker;

import java.util.HashMap;

import org.thieftracker.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {

	public static final Integer SOUND_START = 1;
	public static final Integer SOUND_EMAIL = 2;
	public static final Integer SOUND_SMS = 3;
	public static final Integer SOUND_MOVE = 4;
	public static final Integer SOUND_ALARM = 5;

	private static volatile SoundManager instance;
	private Context context;
	private AudioManager audioManager;
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolResourcesMap;
	private HashMap<Integer, Integer> soundStreamMap;

	private SoundManager(Context context) {
		this.context = context;
		this.audioManager = (AudioManager)this.context.getSystemService(Context.AUDIO_SERVICE);
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundPoolResourcesMap = new HashMap<Integer, Integer>();
		soundPoolResourcesMap.put(SOUND_START,soundPool.load(this.context, R.raw.readytorock, 1));
		soundPoolResourcesMap.put(SOUND_EMAIL, soundPool.load(this.context, R.raw.email2, 1));
		soundPoolResourcesMap.put(SOUND_SMS, soundPool.load(this.context, R.raw.message1, 1));
		soundPoolResourcesMap.put(SOUND_MOVE, soundPool.load(this.context, R.raw.move, 1));
		soundPoolResourcesMap.put(SOUND_ALARM, soundPool.load(this.context, R.raw.alarm, 1));
		
		soundStreamMap = new HashMap<Integer, Integer>();
	}
	
	public static SoundManager getInstance(Context context) {
		if ( SoundManager.instance == null ) {
			synchronized(SoundManager.class) {
				if ( SoundManager.instance == null ) { 				
					SoundManager.instance = new SoundManager(context);
				}
			}
		}
		return( SoundManager.instance );
	}
	
	public boolean playSound(Integer soundId, boolean loop) {
		Integer streamId = this.soundPool.play(soundId, 1, 1, 1, loop ? -1 : 0, 1);
		soundStreamMap.put(soundId,  streamId);
		return( streamId != 0 ? true : false );
	}
	
	public void stopSound(Integer soundId) {
		Integer streamId = soundStreamMap.get(soundId);
		if ( streamId != 0 )
		{
			this.soundPool.stop(streamId);
		}
	}
	
	public void startAlarm() {
		this.audioManager.setStreamVolume (AudioManager.STREAM_MUSIC, this.audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
		this.playSound(SoundManager.SOUND_ALARM, true);		
	}

	public void stopAlarm() {
		this.stopSound(SoundManager.SOUND_ALARM);		
	}
}
