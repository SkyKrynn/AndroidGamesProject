package com.brsmith.android.games.framework.action;

import com.brsmith.android.games.framework.command.IActionCommand;
import com.brsmith.android.games.framework.control.TouchControl;

public class TouchAction
{
	private TouchControl control;
	private boolean enabled;
	private boolean visable;
	private IActionCommand command;
	
	public TouchAction(TouchControl control, boolean enabled, IActionCommand command, boolean visable)
	{
		this.control = control;
		this.enabled = enabled;
		this.visable = visable;
		this.command = command;
		if(command == null)
			enabled = false;
		
	}
	
	public TouchAction(TouchControl control, boolean enabled, IActionCommand command)
	{
		this(control, enabled, command, true);
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public void enable() 
	{
		if(command != null)
			enabled = true;
	}
	
	public void disable() 
	{ 
		enabled = false; 
	}
	
	public boolean isVisable()
	{
		return visable;
	}
	
	public void setVisability(boolean visable)
	{
		this.visable = visable;
	}
	
	public void setCommand(IActionCommand command)
	{
		this.command = command;
		if(command == null)
			enabled = false;
	}
	
	public TouchControl getControl() 
	{ 
		return control;
	}

	public void run()
	{
		if(command != null)
			command.run(control);
	}
}
