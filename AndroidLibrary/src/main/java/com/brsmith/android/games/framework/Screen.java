package com.brsmith.android.games.framework;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;

import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.interfaces.IInput.TouchEvent;
import com.brsmith.android.games.framework.handlers.TouchControlHandler;

public abstract class Screen
{
	protected final IGame game;
	protected final TouchControlHandler controlHandler;
	
	public Screen(IGame game)
	{
		this.game = game;
		controlHandler = new TouchControlHandler();
	}
	
	public abstract void update(float deltaTime);
	public abstract void present(float deltaTime);
	public abstract void pause();
	public abstract void resume();
	public abstract void dispose();
	
	public void sendTouchEventsThruControlHandler(List<TouchEvent> touchEvents)
	{
        try
        {
	        Iterator<TouchEvent> i = touchEvents.iterator();
	        while(i.hasNext())
	        {
	        	TouchEvent event = i.next();
	        	if(event.type == TouchEvent.TOUCH_UP)
	        	{
	        		controlHandler.executeHit(event);
	        	}
	        }
        }
        catch (ConcurrentModificationException ex)
        {}

	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
	}
}
