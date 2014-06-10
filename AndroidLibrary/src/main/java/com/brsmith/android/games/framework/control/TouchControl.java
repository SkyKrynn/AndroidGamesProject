package com.brsmith.android.games.framework.control;

import com.brsmith.android.games.framework.enums.CoordinatePosition;
import com.brsmith.android.games.framework.interfaces.IPixmap;

public class TouchControl extends BaseControl
{
	public TouchControl(IPixmap pixmap, int x, int y)
	{
		super(pixmap, x, y);
	}

	public TouchControl(IPixmap pixmap, int x, int y, CoordinatePosition pos)
	{
		super(pixmap, x, y, pos);
	}
	
	public boolean isHit(float x, float y)
	{
		return isHit((int)x, (int)y);
	}
	
	public boolean isHit(int x, int y)
	{
		if (x >= rect.left && (x <= rect.right))
			if (y >= rect.top && (y <= rect.bottom))
				return true;
			
		return false;
	}

}
