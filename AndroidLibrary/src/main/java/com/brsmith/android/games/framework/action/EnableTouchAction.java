package com.brsmith.android.games.framework.action;

import com.brsmith.android.games.framework.command.IActionCommand;
import com.brsmith.android.games.framework.control.VisualEnableDisableControl;

public class EnableTouchAction extends TouchAction
{

	public EnableTouchAction(VisualEnableDisableControl control, boolean enabled, IActionCommand command)
	{
		super(control, enabled, command);
		if(enabled)
			enable();
		else
			disable();
	}
	
	@Override
	public void enable() 
	{
		super.enable();
		if(isEnabled())
			getControl().setPixmap(((VisualEnableDisableControl)getControl()).getEnabledPixmap());
	}
	
	@Override
	public void disable() 
	{ 
		super.disable();
		if(!isEnabled())
			getControl().setPixmap(((VisualEnableDisableControl)getControl()).getDisabledPixmap());
	}

}
