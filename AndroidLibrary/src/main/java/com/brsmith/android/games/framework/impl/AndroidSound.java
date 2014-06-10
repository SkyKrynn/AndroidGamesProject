package com.brsmith.android.games.framework.impl;

import android.media.SoundPool;

import com.brsmith.android.games.framework.interfaces.ISound;

public class AndroidSound implements ISound
{
	int soundId;
	SoundPool soundPool;
	
	public AndroidSound(SoundPool soundPool, int soundId)
	{
		this.soundId = soundId;
		this.soundPool = soundPool;
	}

	public void play(float volume)
	{
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	public void displose()
	{
		soundPool.unload(soundId);
	}
}
