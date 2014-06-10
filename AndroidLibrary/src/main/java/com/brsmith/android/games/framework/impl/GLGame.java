package com.brsmith.android.games.framework.impl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
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

import java.util.Stack;

public abstract class GLGame extends Activity implements IGame, Renderer
{
	enum GLGameState
	{
		Initialized,
		Running,
		Paused,
		Finished,
		Idle
	}
	
	GLSurfaceView glView;
	GLGraphics glGraphics;
	IAudio audio;
	IInput input;
	IFileIO fileIO;
	Screen screen;
	GLGameState state = GLGameState.Initialized;
	final Object stateChanged = new Object();
	long startTime = System.nanoTime();
	WakeLock wakeLock;

	Screen returnScreen;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		glView = new GLSurfaceView(this);
		glView.setRenderer(this);
		setContentView(glView);
		
		glGraphics = new GLGraphics(getAssets(), glView);
		fileIO = new AndroidFileIO(getAssets());
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, glView, 1, 1);
		PowerManager powerManager = (PowerManager)
				getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
	}
	
	public void onPause()
	{
		synchronized(stateChanged)
		{
			if(isFinishing())
				state = GLGameState.Finished;
			else
				state = GLGameState.Paused;
			
			while(true)
			{
				try
				{
					stateChanged.wait();
					break;
				}
				catch(InterruptedException e)
				{
					// do nothing
				}
			}
		}
		
		wakeLock.release();
		glView.onPause();
		super.onPause();
	}
	
	public void onResume()
	{
		super.onResume();
		glView.onResume();
		wakeLock.acquire();
	}

	public void onDrawFrame(GL10 gl) 
	{
		GLGameState state = null;
		
		synchronized(stateChanged)
		{
			state = this.state;
		}
		
		if(state == GLGameState.Running)
		{
			float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			
			screen.update(deltaTime);
			screen.present(deltaTime);
		}
		
		if(state == GLGameState.Paused)
		{
			screen.pause();
			synchronized(stateChanged)
			{
				this.state = GLGameState.Idle;
				stateChanged.notifyAll();
			}
		}
		
		if(state == GLGameState.Finished)
		{
			screen.pause();
			screen.dispose();
			synchronized(stateChanged)
			{
				this.state = GLGameState.Idle;
				stateChanged.notifyAll();
			}
		}
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		glGraphics.setGL(gl);
		
		synchronized(stateChanged)
		{
			if(state == GLGameState.Initialized)
				screen = getStartScreen();
			state = GLGameState.Running;
			screen.resume();
			startTime = System.nanoTime();
		}
	}
	
	public GLGraphics getGLGraphics()
	{
		return glGraphics;
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
		throw new IllegalStateException("We are using OpenGL!");
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
			throw new IllegalArgumentException("Screen cannot be null");
		
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

    public void gotoLastScreen()
    {
        Screen screen = this.returnScreen;
        if(screen == null)
            return;

        this.screen.dispose();
        this.screen = null;

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
        return this.returnScreen;
	}

	public void exitGame()
	{
		this.finish();
	}

}
