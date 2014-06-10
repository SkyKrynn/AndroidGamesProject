package com.brsmith.android.games.framework.impl;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.interfaces.IGame;

public abstract class GLScreen extends Screen
{
	protected final GLGraphics glGraphics;
	protected final GLGame glGame;
	
	public GLScreen(IGame game)
	{
		super(game);
		glGame = (GLGame)game;
		glGraphics = ((GLGame)game).getGLGraphics();
	}
}
