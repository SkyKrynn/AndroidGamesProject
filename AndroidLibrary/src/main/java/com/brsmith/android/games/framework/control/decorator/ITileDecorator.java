package com.brsmith.android.games.framework.control.decorator;

import com.brsmith.android.games.framework.interfaces.IPixmap;

public interface ITileDecorator
{
	public IPixmap getTilePixmap(int rank);
	public int getTileWidth();
	public int getTileHeight();
}
