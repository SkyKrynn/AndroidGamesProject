package com.brsmith.android.games.slideme;

import android.graphics.Color;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.enums.CoordinatePosition;
import com.brsmith.android.games.framework.control.LabelControl;

public class UnknownScreen extends Screen
{
	LabelControl unknown;

	public UnknownScreen(IGame game)
	{
		super(game);
		
		unknown = new LabelControl("?", 160, 240, CoordinatePosition.CENTER, new Assets());
		unknown.setLabelSize(48);
		unknown.setLabelColor(Color.RED);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void present(float deltaTime)
	{
        IGraphics g = game.getGraphics();
		g.clear(Color.BLACK);
		
		unknown.draw(g);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
