package com.brsmith.android.games.framework.command;

import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.control.BaseControl;

public class ExitCommand implements IActionCommand
{
	IGame game;
	
	public ExitCommand(IGame game)
	{
		this.game = game;
	}

	public void run(BaseControl control) 
	{
		game.exitGame();
	}

}
