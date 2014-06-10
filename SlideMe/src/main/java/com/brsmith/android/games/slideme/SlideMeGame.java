package com.brsmith.android.games.slideme;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.impl.AndroidGame;

public class SlideMeGame extends AndroidGame
{
	public Screen getStartScreen()
	{
		return new LoadingScreen(this);
	}
}
