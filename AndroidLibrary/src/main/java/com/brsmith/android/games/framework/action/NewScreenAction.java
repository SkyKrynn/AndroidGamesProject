package com.brsmith.android.games.framework.action;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.command.IActionCommand;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.control.BaseControl;

public class NewScreenAction<T extends Screen> implements IActionCommand
{
	IGame game;
	Class<T> clazz;
	
	public NewScreenAction(IGame game, Class<T> clazz)
	{
		this.game = game;
		this.clazz = clazz;
	}

	public void run(BaseControl control)
	{
		try {
			Constructor<T> ctor = clazz.getDeclaredConstructor(IGame.class);
			ctor.setAccessible(true);
			Screen newScreen = (Screen) ctor.newInstance(game);
			game.setScreen(newScreen);
		} catch (IllegalArgumentException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		} catch (NoSuchMethodException e) {
		}
}
	
}
