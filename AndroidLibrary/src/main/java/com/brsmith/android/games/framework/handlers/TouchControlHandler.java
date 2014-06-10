package com.brsmith.android.games.framework.handlers;

import java.util.HashMap;
import java.util.Map;

import com.brsmith.android.games.framework.action.TouchAction;
import com.brsmith.android.games.framework.control.TouchControl;
import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.interfaces.IInput.TouchEvent;

public class TouchControlHandler
{
	private final Map<String, TouchAction> controlMap = new HashMap<String, TouchAction>();
	
	public TouchControlHandler()
	{
	}

	public void actionEnabled(String key)
	{
		TouchAction action = controlMap.get(key);
		action.enable();
		controlMap.put(key, action);
	}
	
	public void actionDisabled(String key)
	{
		TouchAction action = controlMap.get(key);
		action.disable();
		controlMap.put(key, action);
	}

	public void actionVisable(String key)
	{
		TouchAction action = controlMap.get(key);
		action.setVisability(true);
		controlMap.put(key, action);
	}
	
	public void actionInvisable(String key)
	{
		TouchAction action = controlMap.get(key);
		action.setVisability(false);
		controlMap.put(key, action);
	}

	public void add(String key, TouchAction value)
	{
		controlMap.put(key, value);
	}

	public void remove(String key)
	{
		if(!controlMap.containsKey(key)) return;
		
		controlMap.remove(key);
	}
	
	public boolean isEnabled(String key)
	{
		if(!controlMap.containsKey(key))
			return false;
		
		return controlMap.get(key).isEnabled();
	}
	
	public TouchControl get(String key)
	{
		return controlMap.get(key).getControl();
	}
	
	public String controlHit(TouchEvent event)
	{
		for(Map.Entry<String, TouchAction> entry : controlMap.entrySet())
		{
			if(entry.getValue().isEnabled())
			{
				if(entry.getValue().getControl().isHit(event.x, event.y))
				{
					return entry.getKey();
				}
			}
		}
		
		return null;
	}

	public boolean executeHit(TouchEvent event)
	{
		for(Map.Entry<String, TouchAction> entry : controlMap.entrySet())
		{
			if(entry.getValue().isEnabled())
			{
				if(entry.getValue().getControl().isHit(event.x, event.y))
				{
					entry.getValue().run();
					return true;
				}
			}
		}
		
		return false;
	}

	public void draw(IGraphics g, String key)
	{
		if(!controlMap.containsKey(key)) return;
		
		controlMap.get(key).getControl().draw(g);
	}
	
	public void updateControls(float deltaTime)
	{
		for(Map.Entry<String, TouchAction> entry : controlMap.entrySet())
			if(entry.getValue().isVisable())
				entry.getValue().getControl().update(deltaTime);
	}
	
	public void drawControls(IGraphics g)
	{
		for(Map.Entry<String, TouchAction> entry : controlMap.entrySet())
			if(entry.getValue().isVisable())
				entry.getValue().getControl().draw(g);
	}
}
