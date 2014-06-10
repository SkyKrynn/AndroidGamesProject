package com.brsmith.android.games.framework.control;

import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.interfaces.IPixmap;

public class Control
{
	IPixmap pixmap;
	int posX;
	int posY;
	
	public Control(IPixmap pixmap, int posX, int posY)
	{
		this.pixmap = pixmap;
		this.posX = posX;
		this.posY = posY;
	}
	
	public void draw(IGraphics g)
	{
		g.drawPixmap(pixmap, posX - pixmap.getWidth() /2, posY - pixmap.getHeight() / 2);
	}
	
	public void move(int posX, int posY)
	{
		this.posX = posX;
		this.posY = posY;
	}
	
	public boolean isHit(float x, float y)
	{
		return isHit((int)x, (int)y);
	}
	
	public boolean isHit(int x, int y)
	{
		if (x >= (posX - pixmap.getWidth() /2) && (x <= (posX + pixmap.getWidth() / 2)))
			if (y >= (posY - pixmap.getHeight() / 2) && (y <= (posY + pixmap.getHeight() / 2)))
				return true;
			
		return false;
	}
}
