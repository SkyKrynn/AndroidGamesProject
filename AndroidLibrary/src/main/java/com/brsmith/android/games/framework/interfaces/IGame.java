package com.brsmith.android.games.framework.interfaces;

import com.brsmith.android.games.framework.Screen;

public interface IGame
{
	public IInput getInput();
	public IFileIO getFileIO();
	public IGraphics getGraphics();
	public IAudio getAudio();
	public void setScreen(Screen screen);
	public Screen getCurrentScreen();
	public Screen getReturnScreen();
	public Screen getStartScreen();
	public void exitGame();
}
