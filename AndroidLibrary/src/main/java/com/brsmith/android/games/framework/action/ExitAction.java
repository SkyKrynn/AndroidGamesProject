package com.brsmith.android.games.framework.action;

import com.brsmith.android.games.framework.command.IActionCommand;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.control.BaseControl;

public class ExitAction implements IActionCommand
{
	IGame game;
	
	public ExitAction(IGame game)
	{
		this.game = game;
	}

	public void run(BaseControl control) 
	{
		game.exitGame();
	}

}
