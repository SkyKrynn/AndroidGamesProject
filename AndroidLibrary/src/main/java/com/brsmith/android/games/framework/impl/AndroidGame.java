package com.brsmith.android.games.framework.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.interfaces.IAudio;
import com.brsmith.android.games.framework.interfaces.IFileIO;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.interfaces.IInput;

public abstract class AndroidGame extends Activity implements IGame
{
	AndroidFastRenderView renderView;
	IGraphics graphics;
	IAudio audio;
	IInput input;
	IFileIO fileIO;
	Screen screen;
	WakeLock wakeLock;

	Screen returnScreen;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(screen != null)
			screen.onActivityResult(requestCode, resultCode, data);
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		boolean isLandscape = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
		int frameBufferWidth = isLandscape ? 480 : 320;
		int frameBufferHeight = isLandscape ? 320 : 480;
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);

		@SuppressWarnings("deprecation")
		float scaleX = (float)frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
		@SuppressWarnings("deprecation")
		float scaleY = (float)frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();
		
		renderView = new AndroidFastRenderView(this, frameBuffer);
		graphics = new AndroidGraphics(getAssets(), frameBuffer, getContentResolver());
		fileIO = new AndroidFileIO(getAssets());
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, renderView, scaleX, scaleY);
		screen = getStartScreen();
		setContentView(renderView);
		
		PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();
		
		if(isFinishing())
			screen.dispose();
	}
	
	public IInput getInput()
	{
		return input;
	}

	public IFileIO getFileIO()
	{
		return fileIO;
	}

	public IGraphics getGraphics()
	{
		return graphics;
	}

	public IAudio getAudio()
	{
		return audio;
	}

	public void setScreen(Screen screen)
	{
		setScreen(screen, false);
	}
	
	public void setScreen(Screen screen, boolean disposeCurrent)
	{
		if(screen == null)
			throw new IllegalArgumentException("Screen must not be null");
		
		this.screen.pause();
		
		if(disposeCurrent)
		{
			this.screen.dispose();
			this.screen = null;
		}
		
		this.returnScreen = this.screen;
		
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}

	public Screen getCurrentScreen()
	{
		return screen;
	}
	
	public Screen getReturnScreen()
	{
		return returnScreen;
	}
	
	public void exitGame()
	{
		this.finish();
	}
}
