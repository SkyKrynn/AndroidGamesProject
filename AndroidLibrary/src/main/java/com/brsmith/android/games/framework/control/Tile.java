package com.brsmith.android.games.framework.control;

import com.brsmith.android.games.framework.enums.CoordinatePosition;
import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.interfaces.IPixmap;

public class Tile extends TouchControl
{
	public Tile(IPixmap pixmap, int x, int y)
	{
		super(pixmap, x, y, CoordinatePosition.CENTER);
		move(x, y);
	}
	
	public void move(int x, int y)
	{
		super.move(x, y);
	}
	
	public void update(float deltaTime)
	{
		super.update(deltaTime);
	}
	
	public void draw(IGraphics g)
	{
		super.draw(g);
	}
}
