package com.brsmith.android.games.framework.control;

import android.graphics.Rect;

import com.brsmith.android.games.framework.enums.CoordinatePosition;
import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.interfaces.IPixmap;

public class BaseControl
{
	IPixmap pixmap;
	Rect rect;
	CoordinatePosition coordPos;
	int x;
	int y;

	public BaseControl(IPixmap pixmap, int x, int y)
	{
		init(pixmap, x, y, CoordinatePosition.UPPER_LEFT);
	}

	public BaseControl(IPixmap pixmap, int x, int y, CoordinatePosition pos)
	{
		init(pixmap, x, y, pos);
	}
	
	private void init(IPixmap pixmap, int x, int y, CoordinatePosition pos)
	{
		this.pixmap = pixmap;
		this.coordPos = pos;
		this.x = x;
		this.y = y;
		move(x, y, pos);
	}
	
	public void move(int x, int y)
	{
		move(x, y, coordPos);
	}
	
	public void move(int x, int y, CoordinatePosition pos)
	{
		if(pixmap == null)
			return;
		
		coordPos = pos;
		
		if(pos == CoordinatePosition.UPPER_RIGHT)
			rect = new Rect(x, y, x + pixmap.getWidth(), y + pixmap.getHeight());

		if(pos == CoordinatePosition.UPPER_MIDDLE)
			rect = new Rect(x - pixmap.getWidth() /2, y, x + pixmap.getWidth() /2, y + pixmap.getHeight());

		if(pos == CoordinatePosition.UPPER_LEFT)
			rect = new Rect(x - pixmap.getWidth(), y, x, y + pixmap.getHeight());

		
		if(pos == CoordinatePosition.CENTER_RIGHT)
			rect = new Rect(x, y - pixmap.getHeight() / 2, x + pixmap.getWidth(), y + pixmap.getHeight() / 2);

		if(pos == CoordinatePosition.CENTER)
			rect = new Rect(x - pixmap.getWidth() /2, y - pixmap.getHeight() / 2, x + pixmap.getWidth() /2, y + pixmap.getHeight() / 2);

		if(pos == CoordinatePosition.CENTER_LEFT)
			rect = new Rect(x - pixmap.getWidth(), y - pixmap.getHeight() / 2, x, y + pixmap.getHeight() / 2);

		
		if(pos == CoordinatePosition.LOWER_RIGHT)
			rect = new Rect(x, y - pixmap.getHeight(), x + pixmap.getWidth(), y);

		if(pos == CoordinatePosition.LOWER_MIDDLE)
			rect = new Rect(x - pixmap.getWidth() /2, y - pixmap.getHeight(), x + pixmap.getWidth() /2, y);

		if(pos == CoordinatePosition.LOWER_LEFT)
			rect = new Rect(x - pixmap.getWidth(), y - pixmap.getHeight(), x, y);

	}
	
	public void setPixmap(IPixmap pixmap)
	{
		this.pixmap = pixmap;
		move(this.x, this.y, this.coordPos);
	}
	
	public CoordinatePosition getCoordPos() { return coordPos; }
	
	public void update(float deltaTime)
	{
		
	}
	
	public void draw(IGraphics g)
	{
		if(pixmap != null)
			g.drawPixmap(pixmap, rect.left, rect.top);
	}
}
