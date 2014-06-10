package com.brsmith.android.games.framework.control;

import com.brsmith.android.games.framework.enums.CoordinatePosition;
import com.brsmith.android.games.framework.interfaces.IPixmap;

public class VisualEnableDisableControl extends TouchControl
{
	IPixmap disabledPixmap;
	IPixmap enabledPixmap;
	
	public VisualEnableDisableControl(IPixmap enabledPixmap, IPixmap disabledPixmap, int x, int y)
	{
		super(enabledPixmap, x, y);
		init(enabledPixmap, disabledPixmap);
	}
	
	public VisualEnableDisableControl(IPixmap enabledPixmap, IPixmap disabledPixmap, int x, int y, CoordinatePosition pos)
	{
		super(enabledPixmap, x, y, pos);
		init(enabledPixmap, disabledPixmap);
	}

	private void init(IPixmap enabledPixmap, IPixmap disabledPixmap)
	{
		this.enabledPixmap = enabledPixmap;
		this.disabledPixmap = disabledPixmap;
	}
	
	public IPixmap getEnabledPixmap() { return enabledPixmap; }
	public IPixmap getDisabledPixmap() { return disabledPixmap; }
}
