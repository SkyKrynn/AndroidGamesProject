package com.brsmith.android.games.framework.command;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.control.BaseControl;

public class ReturnScreenCommand<T extends Screen> extends NewScreenCommand<T> implements IActionCommand
{

	public ReturnScreenCommand(IGame game, Class<T> clazz)
	{
		super(game, clazz);
	}
	
	@Override
	public void run(BaseControl control)
	{
		if(game.getReturnScreen() != null)
			game.setScreen(game.getReturnScreen());
		else
			createNewScreen();
	}

}
